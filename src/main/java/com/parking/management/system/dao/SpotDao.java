package com.parking.management.system.dao;

import com.parking.management.system.domain.Spot;
import com.parking.management.system.utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SpotDao implements Dao<Spot> {

    private final ConnectionProvider connectionProvider;

    private static final String GET_SPOT_BY_ID = "SELECT * FROM spots WHERE id = ?";
    private static final String GET_ALL_SPOT = "SELECT * FROM spots ORDER BY id";
    private static final String CREATE_SPOT_SQL = "INSERT INTO spots VALUES (DEFAULT, ?, ?, ?)";
    private static final String UPDATE_SPOT_SQL = "UPDATE spots SET is_free = ?, type = ? , floor_id = ? WHERE id = ?";
    private static final String DELETE_SPOT_SQL = "DELETE FROM spots WHERE id = ?";

    public SpotDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }


    @Override
    public Optional<Spot> getById(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SPOT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToSpot(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Spot> getAll() {
        List<Spot> spots = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SPOT)) {
            while (resultSet.next()) {
                spots.add(mapToSpot(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spots;
    }

    @Override
    public void save(Spot spot) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_SPOT_SQL, RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, spot.isFree());
            statement.setString(2, spot.getType());
            statement.setInt(3, spot.getFloorId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    spot.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Spot spot) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SPOT_SQL)) {
            statement.setBoolean(1, spot.isFree());
            statement.setString(2, spot.getType());
            statement.setInt(3, spot.getFloorId());
            statement.setInt(4, spot.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SPOT_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Spot mapToSpot(ResultSet resultSet) throws SQLException {
        return new Spot(resultSet.getInt("id"), resultSet.getBoolean("is_free"), resultSet.getString("type"),
                resultSet.getInt("floor_id"));
    }
}
