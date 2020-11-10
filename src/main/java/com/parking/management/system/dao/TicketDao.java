package com.parking.management.system.dao;


import com.parking.management.system.domain.Ticket;
import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
public class TicketDao implements Dao<Ticket> {

    private final ConnectionProvider connectionProvider;

    private static final String GET_TICKET_BY_ID = "SELECT * FROM tickets WHERE id = ?";
    private static final String GET_ALL_TICKET = "SELECT * FROM tickets ORDER BY id";
    private static final String CREATE_TICKET_SQL = "INSERT INTO tickets VALUES (DEFAULT, ?, ?)";
    private static final String UPDATE_TICKET_SQL = "UPDATE tickets SET creation_date = ?, payment_id = ? WHERE id = ?";
    private static final String DELETE_TICKET_SQL = "DELETE FROM tickets WHERE id = ?";
    private static final String GET_BY_PAYMENT_ID_SQL = "SELECT * FROM tickets WHERE payment_id = ?";

    public TicketDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<Ticket> getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TICKET_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToTicket(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_TICKET)) {
            while (resultSet.next()) {
                tickets.add(mapToTicket(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void save(Ticket ticket) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TICKET_SQL, RETURN_GENERATED_KEYS)) {
            statement.setObject(1, ticket.getCreationDate());
            statement.setInt(2, ticket.getPaymentId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Ticket ticket) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET_SQL)) {
            statement.setObject(1, ticket.getCreationDate());
            statement.setInt(2, ticket.getPaymentId());
            statement.setInt(3, ticket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TICKET_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Ticket mapToTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(resultSet.getInt("id"), resultSet.getObject("creation_date", LocalDate.class), resultSet.getInt("payment_id") );
    }

    public Optional<Ticket> getByPaymentId(int payment_id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_PAYMENT_ID_SQL)) {
            statement.setInt(1, payment_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToTicket(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
