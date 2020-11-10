package com.parking.management.system.dao;

import com.parking.management.system.domain.Account;
import com.parking.management.system.domain.Admin;
import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class AdminDao implements Dao<Admin> {

    private final ConnectionProvider connectionProvider;

    private static final String GET_ADMIN_BY_ID = "SELECT * FROM admins WHERE id = ?";
    private static final String GET_ALL_ADMINS = "SELECT * FROM admins ORDER BY id";
    private static final String CREATE_ADMIN_SQL = "INSERT INTO admins VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ADMIN_SQL = "UPDATE admins SET name = ?, address = ?, email = ?, phone = ?, account_id = ? WHERE id = ?";
    private static final String DELETE_ADMIN_SQL = "DELETE FROM admins WHERE id = ?";

    public AdminDao(ConnectionProvider connectionProvider) { this.connectionProvider = connectionProvider; }

    @Override
    public Optional<Admin> getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ADMIN_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToAdmin(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<Admin> getAll() {
        List<Admin> admins = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_ADMINS)) {
            while (resultSet.next()) {
                admins.add(mapToAdmin(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public void save(Admin admin) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getAddress());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPhone());
            statement.setInt(5, admin.getAccount_id());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    admin.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Admin admin) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_SQL)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getAddress());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPhone());
            statement.setInt(5, admin.getAccount_id());
            statement.setInt(6, admin.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Admin mapToAdmin(ResultSet resultSet) throws SQLException {
        return new Admin(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("address"), resultSet.getString("email"),
                resultSet.getString("phone"), resultSet.getInt("account_id"));
    }
}
