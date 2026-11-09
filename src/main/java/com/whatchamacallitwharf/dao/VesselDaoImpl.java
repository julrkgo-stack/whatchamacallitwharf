package com.whatchamacallitwharf.dao;

import com.whatchamacallitwharf.model.Vessel;
import com.whatchamacallitwharf.resources.config.Database;
import com.whatchamacallitwharf.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class VesselDaoImpl implements VesselDao {

    @Override 
    public void addVessel(Vessel vessel) {
        Objects.requireNonNull(vessel, "vessel cannot be null");  // second guard, DAO layer still protects itself with a null guard as a safety net.
        String insertSQL = "INSERT INTO vessel (vessel_name, vessel_type, color, pattern, material, capacity_oz, dish_washer_safe) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(insertSQL);) {
             ps.setString(1, vessel.getName());  
             ps.setString(2, vessel.getType());
             ps.setString(3, vessel.getColor());
             ps.setString(4, vessel.getPattern());
             ps.setString(5, vessel.getMaterial());
             ps.setInt(6, vessel.getCapacity());
             ps.setBoolean(7, vessel.getDishwasherSafe());
             ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("addVessel failed for " + vessel.getName(), e);
        }
    }

    @Override 
    public List<Vessel> getAllVessels() {
        List<Vessel> vessels = new ArrayList<>();
        try(Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, vessel_name, vessel_type, color, pattern, material, capacity_oz, dish_washer_safe FROM vessel");) {
            while (rs.next()) {
                Vessel vessel = new Vessel( rs.getInt("id"),
                                            rs.getString("vessel_name"),
                                            rs.getString("vessel_type"),
                                            rs.getString("color"),
                                            rs.getString("pattern"),
                                            rs.getString("material"),
                                            rs.getInt("capacity_oz"),
                                            rs.getBoolean("dish_washer_safe"));
                vessels.add(vessel);
            }
        } catch (SQLException e) {
            throw new DataAccessException("getAllVessels failed", e);
        }
        return vessels;
    }

    @Override
    public Optional<Vessel> getVesselById(int id) {

        String selectSQL = "SELECT id, vessel_name, vessel_type, color, pattern, material, capacity_oz, dish_washer_safe FROM vessel WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(selectSQL);) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Vessel vessel = new Vessel();
                        vessel.setId(rs.getInt("id"));
                        vessel.setName(rs.getString("vessel_name"));
                        vessel.setType(rs.getString("vessel_type"));
                        vessel.setColor(rs.getString("color"));
                        vessel.setPattern(rs.getString("pattern"));
                        vessel.setMaterial(rs.getString("material"));
                        vessel.setCapacity(rs.getInt("capacity_oz"));
                        vessel.setDishwasherSafe(rs.getBoolean("dish_washer_safe"));
                        return Optional.of(vessel);
                    }
                }
        } catch (SQLException e) {
            throw new DataAccessException("getVesselById failed for " + id, e);
        }
        return Optional.empty();
    }

}
// 3. Data Access Objects
// UserDao.java        # Interface defining database operations
// UserDaoImpl.java    # SQL implementation details

