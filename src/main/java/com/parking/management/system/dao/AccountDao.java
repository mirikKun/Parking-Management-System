package com.parking.management.system.dao;

import com.parking.management.system.domain.Account;
import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class AccountDao implements Dao<Account> {

    private final ConnectionProvider connectionProvider;

    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    private static final String GET_ALL_ACCOUNTS = "SELECT * FROM accounts ORDER BY id";
    private static final String CREATE_ACCOUNT_SQL = "INSERT INTO accounts VALUES (DEFAULT, ?, ?)";
    private static final String UPDATE_ACCOUNT_SQL = "UPDATE accounts SET username = ?, password = ? WHERE id = ?";
    private static final String DELETE_ACCOUNT_SQL = "DELETE FROM accounts WHERE id = ?";
    private static final String GET_BY_USERNAME_SQL = "SELECT * FROM accounts WHERE username = ?";

    public AccountDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<Account> getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToAccount(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_ACCOUNTS)) {
            while (resultSet.next()) {
                accounts.add(mapToAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void save(Account account) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account mapToAccount(ResultSet resultSet) throws SQLException {
        return new Account(resultSet.getInt("id"), resultSet.getString("username"),
                resultSet.getString("password"));
    }

    public Optional<Account> getByUsername(String username) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_USERNAME_SQL)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToAccount(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
