package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Account;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DBUnitConfigParametrResolver.class)
class AccountDaoTest extends DBUnitConfig {

    public AccountDaoTest(String name) throws Exception {
        super(name);
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        String file = getClass().getClassLoader().getResource("preparedDataset.xml").getFile();
        beforeData = new FlatXmlDataSetBuilder().build(new File(file));
        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    @Test
    void givenAccountId3_whenDelete_thenDeletedThirdAccount() throws Exception {
        accountDao.delete(3);

        String file = getClass().getClassLoader().getResource("AccountDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("accounts");

        ITable actualTable = tester.getConnection().createDataSet().getTable("accounts");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllAccounts() {
        List<Account> expectedAccounts = new ArrayList<>();
        expectedAccounts.add(new Account(1, "username", "password"));
        expectedAccounts.add(new Account(2, "Qwerty", "123456"));
        expectedAccounts.add(new Account(3, "Andrey", "1111"));

        List<Account> actualCourses = accountDao.getAll();

        assertEquals(expectedAccounts, actualCourses);
    }

    @Test
    void givenAccount_whenSave_thenAddedGivenAccount() throws Exception {
        accountDao.save(new Account("NEW", "USER"));

        String file = getClass().getClassLoader().getResource("AccountDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("accounts");

        ITable actualTable = tester.getConnection().createDataSet().getTable("accounts");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenAccount_whenUpdate_thenUpdatedAccountWithEqualId() throws Exception {
        accountDao.update(new Account(3, "UPDATED", "USER"));

        String file = getClass().getClassLoader().getResource("AccountDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("accounts");

        ITable actualTable = tester.getConnection().createDataSet().getTable("accounts");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenAccountId_whenGetById_thenReturnedAccountWithGivenId() {
        Account expectedAccount = new Account(1, "username", "password");

        Optional<Account> actualAccount = accountDao.getById(1);

        assertEquals(expectedAccount, actualAccount.get());
    }

    @Test
    void givenUsernameOfFirstAccount_whenGetByUsername_thenReturnedAccountWithGivenUsername() {
        Account expectedAccount = new Account(1, "username", "password");

        Optional<Account> actualAccount = accountDao.getByUsername("username");

        assertEquals(expectedAccount, actualAccount.get());
    }
}