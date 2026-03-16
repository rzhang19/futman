package com.futman.db;

import com.futman.model.Player;
import com.futman.model.Player.Position;
import com.futman.model.Team;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO implements DAO<Player> {
    private Connection connection;
    private TeamDAO teamDAO;
    
    public PlayerDAO(Connection connection) {
        this.connection = connection;
        this.teamDAO = new TeamDAO(connection);
    }
    
    @Override
    public boolean save(Player player) {
        if (player == null || !player.isValid()) {
            return false;
        }
        
        String sql = "INSERT INTO players (first_name, last_name, display_name, dob, position, overall, " +
                     "speed, shooting, tackling, passing, reactions, blocking, team_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, player.getFirstName());
            pstmt.setString(2, player.getLastName());
            pstmt.setString(3, player.getNickName());
            pstmt.setString(4, player.getDOB().toString());
            pstmt.setString(5, player.getPosition());
            pstmt.setInt(6, player.getOverall());
            pstmt.setInt(7, player.getSpeed());
            pstmt.setInt(8, player.getShooting());
            pstmt.setInt(9, player.getTackling());
            pstmt.setInt(10, player.getPassing());
            pstmt.setInt(11, player.getReactions());
            pstmt.setInt(12, player.getBlocking());
            pstmt.setInt(13, player.getTeam().getID());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    player.setID(rs.getInt(1));
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("db.PlayerDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Player findById(int id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Player player = mapResultSetToPlayer(rs);
                rs.close();
                return player;
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.PlayerDAO Error: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                players.add(mapResultSetToPlayer(rs));
            }
        } catch (SQLException e) {
            System.err.println("db.PlayerDAO Error: " + e.getMessage());
        }
        
        return players;
    }
    
    @Override
    public boolean update(Player player) {
        if (player == null || !player.isValid()) {
            return false;
        }
        
        String sql = "UPDATE players SET first_name = ?, last_name = ?, display_name = ?, dob = ?, " +
                     "position = ?, overall = ?, speed = ?, shooting = ?, tackling = ?, passing = ?, " +
                     "reactions = ?, blocking = ?, team_id = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, player.getFirstName());
            pstmt.setString(2, player.getLastName());
            pstmt.setString(3, player.getNickName());
            pstmt.setString(4, player.getDOB().toString());
            pstmt.setString(5, player.getPosition());
            pstmt.setInt(6, player.getOverall());
            pstmt.setInt(7, player.getSpeed());
            pstmt.setInt(8, player.getShooting());
            pstmt.setInt(9, player.getTackling());
            pstmt.setInt(10, player.getPassing());
            pstmt.setInt(11, player.getReactions());
            pstmt.setInt(12, player.getBlocking());
            pstmt.setInt(13, player.getTeam().getID());
            pstmt.setInt(14, player.getID());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.PlayerDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM players WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.PlayerDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    public List<Player> findByTeamId(int teamId) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players WHERE team_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, teamId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                players.add(mapResultSetToPlayer(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.PlayerDAO Error: " + e.getMessage());
        }
        
        return players;
    }
    
    private Player mapResultSetToPlayer(ResultSet rs) throws SQLException {
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String displayName = rs.getString("display_name");
        LocalDate dob = LocalDate.parse(rs.getString("dob"));
        Position position = Position.valueOf(rs.getString("position"));
        int speed = rs.getInt("speed");
        int shooting = rs.getInt("shooting");
        int tackling = rs.getInt("tackling");
        int passing = rs.getInt("passing");
        int reactions = rs.getInt("reactions");
        int blocking = rs.getInt("blocking");
        int teamId = rs.getInt("team_id");
        
        Team team = teamDAO.findById(teamId);
        
        Player player = new Player(firstName, lastName, displayName, dob, position, 
                                   speed, shooting, tackling, passing, reactions, blocking);
        player.setID(rs.getInt("id"));
        if (team != null) {
            player.addToTeam(team);
        }
        
        return player;
    }
}
