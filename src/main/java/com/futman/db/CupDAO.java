package com.futman.db;

import com.futman.model.Country;
import com.futman.model.Cup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CupDAO implements DAO<Cup> {
    private Connection connection;
    private CountryDAO countryDAO;
    
    public CupDAO(Connection connection) {
        this.connection = connection;
        this.countryDAO = new CountryDAO(connection);
    }
    
    @Override
    public boolean save(Cup cup) {
        if (cup == null || !cup.isValid()) {
            return false;
        }
        
        String sql = "INSERT INTO cups (name, country_id, max_teams, max_matches, year) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cup.getName());
            pstmt.setInt(2, cup.getCountry().getID());
            pstmt.setInt(3, cup.getMaxSize());
            pstmt.setInt(4, cup.getMaxMatchesPerSeason());
            pstmt.setInt(5, cup.getYear());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    cup.setID(rs.getInt(1));
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("db.CupDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Cup findById(int id) {
        String sql = "SELECT * FROM cups WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cup cup = mapResultSetToCup(rs);
                rs.close();
                return cup;
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.CupDAO Error: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Cup> findAll() {
        List<Cup> cups = new ArrayList<>();
        String sql = "SELECT * FROM cups";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cups.add(mapResultSetToCup(rs));
            }
        } catch (SQLException e) {
            System.err.println("db.CupDAO Error: " + e.getMessage());
        }
        
        return cups;
    }
    
    @Override
    public boolean update(Cup cup) {
        if (cup == null || !cup.isValid()) {
            return false;
        }
        
        String sql = "UPDATE cups SET name = ?, country_id = ?, max_teams = ?, max_matches = ?, year = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cup.getName());
            pstmt.setInt(2, cup.getCountry().getID());
            pstmt.setInt(3, cup.getMaxSize());
            pstmt.setInt(4, cup.getMaxMatchesPerSeason());
            pstmt.setInt(5, cup.getYear());
            pstmt.setInt(6, cup.getID());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.CupDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM cups WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.CupDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    public List<Cup> findByCountryId(int countryId) {
        List<Cup> cups = new ArrayList<>();
        String sql = "SELECT * FROM cups WHERE country_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, countryId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                cups.add(mapResultSetToCup(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.CupDAO Error: " + e.getMessage());
        }
        
        return cups;
    }
    
    private Cup mapResultSetToCup(ResultSet rs) throws SQLException {
        int countryId = rs.getInt("country_id");
        Country country = countryDAO.findById(countryId);
        
        Cup cup = new Cup(
            rs.getString("name"),
            country,
            rs.getInt("max_teams")
        );
        cup.setID(rs.getInt("id"));
        
        return cup;
    }
}
