package com.futman.db;

import com.futman.model.Country;
import com.futman.model.League;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeagueDAO implements DAO<League> {
    private Connection connection;
    private CountryDAO countryDAO;
    
    public LeagueDAO(Connection connection) {
        this.connection = connection;
        this.countryDAO = new CountryDAO(connection);
    }
    
    @Override
    public boolean save(League league) {
        if (league == null || !league.isValid()) {
            return false;
        }
        
        String sql = "INSERT INTO leagues (name, country_id, tier, max_teams, face_times, year) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, league.getName());
            pstmt.setInt(2, league.getCountry().getID());
            pstmt.setInt(3, league.getTier());
            pstmt.setInt(4, league.getMaxSize());
            pstmt.setInt(5, league.getFaceTimesPublic());
            pstmt.setInt(6, league.getYear());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    league.setID(rs.getInt(1));
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("db.LeagueDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public League findById(int id) {
        String sql = "SELECT * FROM leagues WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                League league = mapResultSetToLeague(rs);
                rs.close();
                return league;
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.LeagueDAO Error: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<League> findAll() {
        List<League> leagues = new ArrayList<>();
        String sql = "SELECT * FROM leagues";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                leagues.add(mapResultSetToLeague(rs));
            }
        } catch (SQLException e) {
            System.err.println("db.LeagueDAO Error: " + e.getMessage());
        }
        
        return leagues;
    }
    
    @Override
    public boolean update(League league) {
        if (league == null || !league.isValid()) {
            return false;
        }
        
        String sql = "UPDATE leagues SET name = ?, country_id = ?, tier = ?, max_teams = ?, face_times = ?, year = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, league.getName());
            pstmt.setInt(2, league.getCountry().getID());
            pstmt.setInt(3, league.getTier());
            pstmt.setInt(4, league.getMaxSize());
            pstmt.setInt(5, league.getFaceTimesPublic());
            pstmt.setInt(6, league.getYear());
            pstmt.setInt(7, league.getID());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.LeagueDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM leagues WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.LeagueDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    public List<League> findByCountryId(int countryId) {
        List<League> leagues = new ArrayList<>();
        String sql = "SELECT * FROM leagues WHERE country_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, countryId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                leagues.add(mapResultSetToLeague(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.LeagueDAO Error: " + e.getMessage());
        }
        
        return leagues;
    }
    
    private League mapResultSetToLeague(ResultSet rs) throws SQLException {
        int countryId = rs.getInt("country_id");
        Country country = countryDAO.findById(countryId);
        
        League league = new League(
            rs.getString("name"),
            country,
            rs.getInt("tier"),
            rs.getInt("max_teams"),
            rs.getInt("face_times")
        );
        league.setID(rs.getInt("id"));
        
        return league;
    }
}
