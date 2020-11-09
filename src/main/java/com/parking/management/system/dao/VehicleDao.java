package com.parking.management.system.dao;

import com.parking.management.system.domain.Ticket;
import com.parking.management.system.domain.Vehicle;

import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
public class VehicleDao implements Dao<Vehicle> {
    private final ConnectionProvider connectionProvider;

    private static final String GET_VEHICLE_BY_ID = "SELECT * FROM vehicles WHERE id = ?";
    private static final String GET_ALL_VEHICLE = "SELECT * FROM vehicles ORDER BY id";
    private static final String CREATE_VEHICLE_SQL = "INSERT INTO vehicles VALUES (DEFAULT, ?)";
    private static final String UPDATE_VEHICLE_SQL = "UPDATE vehicles SET type = ? WHERE id = ?";
    private static final String DELETE_VEHICLE_SQL = "DELETE FROM vehicles WHERE id = ?";

    public VehicleDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Vehicle getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_VEHICLE_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToVehicle(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vehicle> getAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_VEHICLE)) {
            while (resultSet.next()) {
                vehicles.add(mapToVehicle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public void save(Vehicle vehicle) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_VEHICLE_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, vehicle.getType());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vehicle.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Vehicle vehicle) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_VEHICLE_SQL)) {
            statement.setObject(1, vehicle.getType());
            statement.setInt(2, vehicle.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_VEHICLE_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vehicle mapToVehicle(ResultSet resultSet) throws SQLException {
        return new Vehicle(resultSet.getInt("id"), resultSet.getString("type") );
    }
}
