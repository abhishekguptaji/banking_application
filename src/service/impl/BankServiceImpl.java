package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
//          String customerId = accountType;

        Customer c = new Customer(email, name, customerId);
        customerRepository.save(c);
//        String accountNumber = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();

        Account account = new Account(accountNumber, accountType, (double) 0, customerId);
        accountRepository.save(account);

        return accountNumber;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        return String.format("AC%06d", size);
    }

    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException(
                        "Account not found: " + accountNumber
                ));

        // Update balance
        account.setBalance(account.getBalance() + amount);

        // Create transaction
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(), // id
                Type.DEPOSIT,                 // type
                account.getAccountNumber(),   // accountNumber
                amount,                       // amount
                LocalDateTime.now(),          // timestamp
                note                          // note
        );

        // Save transaction
        transactionRepository.add(transaction);

        // Save updated account
        accountRepository.save(account);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException(
                        "Account not found: " + accountNumber
                ));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }
        // Update balance
        account.setBalance(account.getBalance() - amount);

        // Create transaction
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(), // id
                Type.DEPOSIT,                 // type
                account.getAccountNumber(),   // accountNumber
                amount,                       // amount
                LocalDateTime.now(),          // timestamp
                note                        // note
        );

        // Save transaction
        transactionRepository.add(transaction);

        // Save updated account
        accountRepository.save(account);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {
        if (fromAcc.equals(toAcc)) {
            throw new RuntimeException("Cannot transfer to the account");
        }
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new RuntimeException(
                        "Account not found: " + fromAcc
                ));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new RuntimeException(
                        "Account not found: " + toAcc
                ));
        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("insufficient Balance");
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        Transaction fromTransaction = new Transaction(
                UUID.randomUUID().toString(), // id
                Type.TRANSFER_OUT,                 // type
                from.getAccountNumber(),   // accountNumber
                amount,                       // amount
                LocalDateTime.now(),          // timestamp
                note                        // note
        );
        transactionRepository.add(fromTransaction);
        Transaction toTransaction = new Transaction(
                UUID.randomUUID().toString(), // id
                Type.TRANSFER_IN,                 // type
                to.getAccountNumber(),   // accountNumber
                amount,                       // amount
                LocalDateTime.now(),          // timestamp
                note                        // note
        );
        transactionRepository.add(fromTransaction);
    }

    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
        List<Account> result = new ArrayList<>();
        for (Customer c : customerRepository.findAll()) {
            if (c.getName().toLowerCase().contains(query)) {
                result.addAll(
                        accountRepository.findByCustomer(c.getId())
                );
            }
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }

}

