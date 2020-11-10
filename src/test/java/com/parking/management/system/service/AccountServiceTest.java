package com.parking.management.system.service;

import com.parking.management.system.dao.AccountDao;
import com.parking.management.system.domain.Account;
import com.parking.management.system.exception.UsernameNotUniqueException;
import com.parking.management.system.utils.ConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountService accountService;

    @Test
    void givenIdOfTheFirstAccount_whenGetById_thenReturnedAccountWithGivenId() {
        Optional<Account> expectedAccount = Optional.of(new Account(1, "username", "password"));
        when(accountDao.getById(1)).thenReturn(expectedAccount);

        Optional<Account> actualAccount = accountService.getById(1);

        verify(accountDao, times(1)).getById(1);
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void getAll() {
        List<Account> expectedAccounts = singletonList(new Account(1, "username", "password"));
        when(accountDao.getAll()).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountService.getAll();

        verify(accountDao, times(1)).getAll();
        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    void save() {
        Account account = new Account(1, "username", "password");
        when(accountDao.getByUsername("username")).thenReturn(Optional.empty());

        accountService.save(account);

        verify(accountDao, times(1)).save(account);
    }

    @Test
    void update() {
        Account account = new Account(1, "username", "password");
        when(accountDao.getById(1)).thenReturn(Optional.of(account));
        when(accountDao.getByUsername("username")).thenReturn(Optional.empty());

        accountService.update(account);

        verify(accountDao, times(1)).update(account);
    }
}