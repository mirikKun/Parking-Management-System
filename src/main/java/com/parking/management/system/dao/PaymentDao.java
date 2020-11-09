package com.parking.management.system.dao;

import com.parking.management.system.domain.Payment;
import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PaymentDao implements Dao<Payment>{

    private final ConnectionProvider connectionProvider;

    private static final String GET_PAYMENT_BY_ID = "SELECT * FROM payments WHERE id = ?";
    private static final String GET_ALL_PAYMENTS = "SELECT * FROM payments ORDER BY id";
    private static final String CREATE_PAYMENT_SQL = "INSERT INTO payments VALUES (DEFAULT, ?, ?, ?, ?)";
    private static final String UPDATE_PAYMENT_SQL = "UPDATE payments SET creation_date = ?, amount = ?, status = ?, type = ? WHERE id = ?";
    private static final String DELETE_PAYMENT_SQL = "DELETE FROM payments WHERE id = ?";


    public PaymentDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }


    @Override
    public Payment getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PAYMENT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPayment(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PAYMENTS)) {
            while (resultSet.next()) {
                payments.add(mapToPayment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public void save(Payment payment) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_PAYMENT_SQL, RETURN_GENERATED_KEYS)) {
            statement.setObject(1, payment.getCreationDate());
            statement.setInt(2, payment.getAmount());
            statement.setString(3, payment.getStatus());
            statement.setString(4, payment.getType());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Payment payment) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PAYMENT_SQL)) {
            statement.setObject(1, payment.getCreationDate());
            statement.setInt(2, payment.getAmount());
            statement.setString(3, payment.getStatus());
            statement.setString(4, payment.getType());
            statement.setInt(5, payment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PAYMENT_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Payment mapToPayment(ResultSet resultSet) throws SQLException{
        return new Payment(resultSet.getInt("id"), resultSet.getObject("creation_date", LocalDate.class),
                resultSet.getInt("amount"), resultSet.getString("status"),
                resultSet.getString("type"));
    }
}
