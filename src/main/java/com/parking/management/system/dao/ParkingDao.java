package com.parking.management.system.dao;

import com.parking.management.system.domain.Parking;
import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ParkingDao implements Dao<Parking>{

    private final ConnectionProvider connectionProvider;

    private static final String GET_PARKING_BY_ID = "SELECT * FROM parkings WHERE id = ?";
    private static final String GET_ALL_PARKINGS = "SELECT * FROM parkings ORDER BY id";
    private static final String CREATE_PARKING_SQL = "INSERT INTO parkings VALUES (DEFAULT, ?)";
    private static final String UPDATE_PARKING_SQL = "UPDATE parkings SET address = ? WHERE id = ?";
    private static final String DELETE_PARKING_SQL = "DELETE FROM parkings WHERE id = ?";

    public ParkingDao(ConnectionProvider connectionProvider) { this.connectionProvider = connectionProvider; }

    @Override
    public Parking getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PARKING_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToParking(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Parking> getAll() {
        List<Parking> parkings = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PARKINGS)) {
            while (resultSet.next()) {
                parkings.add(mapToParking(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkings;
    }

    @Override
    public void save(Parking parking) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_PARKING_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, parking.getAddress());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    parking.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Parking parking) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PARKING_SQL)) {
            statement.setString(1, parking.getAddress());
            statement.setInt(2, parking.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PARKING_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Parking mapToParking(ResultSet resultSet) throws SQLException{
        return new Parking(resultSet.getInt("id"), resultSet.getString("address"));
    }
}
