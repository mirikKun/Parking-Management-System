package com.parking.management.system.service;

import com.parking.management.system.dao.AccountDao;
import com.parking.management.system.domain.Account;
import com.parking.management.system.exception.EntityNotFoundException;
import com.parking.management.system.exception.UsernameNotUniqueException;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Optional<Account> getById(int id) {
        return accountDao.getById(id);
    }

    public List<Account> getAll() {
        return accountDao.getAll();
    }

    public void save(Account account) {
        verifyAccountUnique(account);
        accountDao.save(account);
    }

    public void update(Account account) {
        verifyAccountUnique(account);
        verifyAccountPresent(account.getId());
        accountDao.update(account);
    }

    public void delete(int id) {
        accountDao.delete(id);
    }

    private void verifyAccountPresent(int id) {
        accountDao.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Audience with id %d is not present", id)));
    }

    private void verifyAccountUnique(Account account) {
        accountDao.getByUsername(account.getUsername()).ifPresent(accountWithSameUsername -> {
            if (account.getId() != accountWithSameUsername.getId()) {
                throw new UsernameNotUniqueException(String.format("Account with username %s already exist", account.getUsername()));
            }
        });
    }
}
