package com.parking.management.system.dao;

import com.parking.management.system.domain.Floor;
import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class FloorDao implements Dao<Floor>{

    private final ConnectionProvider connectionProvider;

    private static final String GET_FLOOR_BY_ID = "SELECT * FROM floors WHERE id = ?";
    private static final String GET_ALL_FLOORS = "SELECT * FROM floors ORDER BY id";
    private static final String CREATE_FLOOR_SQL = "INSERT INTO floors VALUES (DEFAULT, ?, ?, ?)";
    private static final String UPDATE_FLOOR_SQL = "UPDATE floors SET floor_number = ?, spot_number = ?, parking_id = ? WHERE id = ?";
    private static final String DELETE_FLOOR_SQL = "DELETE FROM floors WHERE id = ?";

    public FloorDao(ConnectionProvider connectionProvider) { this.connectionProvider = connectionProvider; }

    @Override
    public Floor getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FLOOR_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToFloor(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Floor> getAll() {
        List<Floor> floors = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_FLOORS)) {
            while (resultSet.next()) {
                floors.add(mapToFloor(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return floors;
    }

    @Override
    public void save(Floor floor) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_FLOOR_SQL, RETURN_GENERATED_KEYS)) {
            statement.setInt(1, floor.getFloorNumber());
            statement.setInt(2, floor.getSpotNumber());
            statement.setInt(3, floor.getParkingID());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    floor.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Floor floor) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FLOOR_SQL)) {
            statement.setInt(1, floor.getFloorNumber());
            statement.setInt(2, floor.getSpotNumber());
            statement.setInt(3, floor.getParkingID());
            statement.setInt(4, floor.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_FLOOR_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Floor mapToFloor(ResultSet resultSet) throws SQLException{
        return new Floor(resultSet.getInt("id"), resultSet.getInt("floor_number"),
                resultSet.getInt("spot_number"), resultSet.getInt("parking_id"));
    }
}
