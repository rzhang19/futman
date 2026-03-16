package com.futman.db;

import com.futman.model.Country;
import com.futman.model.Formation;
import com.futman.model.League;
import com.futman.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements DAO<Team> {
    private Connection connection;
    private CountryDAO countryDAO;
    private LeagueDAO leagueDAO;
    
    public TeamDAO(Connection connection) {
        this.connection = connection;
        this.countryDAO = new CountryDAO(connection);
        this.leagueDAO = new LeagueDAO(connection);
    }
    
    @Override
    public boolean save(Team team) {
        if (team == null || !team.isValid()) {
            return false;
        }
        
        String sql = "INSERT INTO teams (long_name, short_name, country_id, league_id, formation, " +
                     "season_wins, season_draws, season_losses, season_goals, goals_conceded) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, team.getName());
            pstmt.setString(2, team.getShortName());
            pstmt.setInt(3, team.getCountry().getID());
            
            if (team.getLeague() != null) {
                pstmt.setInt(4, team.getLeague().getID());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.setString(5, team.getFormation().name());
            pstmt.setInt(6, team.getSeasonWins());
            pstmt.setInt(7, team.getSeasonDraws());
            pstmt.setInt(8, team.getSeasonLosses());
            pstmt.setInt(9, team.getSeasonGoals());
            pstmt.setInt(10, team.getGoalsConceded());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    team.setID(rs.getInt(1));
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("db.TeamDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Team findById(int id) {
        String sql = "SELECT * FROM teams WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Team team = mapResultSetToTeam(rs);
                rs.close();
                return team;
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.TeamDAO Error: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                teams.add(mapResultSetToTeam(rs));
            }
        } catch (SQLException e) {
            System.err.println("db.TeamDAO Error: " + e.getMessage());
        }
        
        return teams;
    }
    
    @Override
    public boolean update(Team team) {
        if (team == null || !team.isValid()) {
            return false;
        }
        
        String sql = "UPDATE teams SET long_name = ?, short_name = ?, country_id = ?, league_id = ?, " +
                     "formation = ?, season_wins = ?, season_draws = ?, season_losses = ?, " +
                     "season_goals = ?, goals_conceded = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, team.getName());
            pstmt.setString(2, team.getShortName());
            pstmt.setInt(3, team.getCountry().getID());
            
            if (team.getLeague() != null) {
                pstmt.setInt(4, team.getLeague().getID());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.setString(5, team.getFormation().name());
            pstmt.setInt(6, team.getSeasonWins());
            pstmt.setInt(7, team.getSeasonDraws());
            pstmt.setInt(8, team.getSeasonLosses());
            pstmt.setInt(9, team.getSeasonGoals());
            pstmt.setInt(10, team.getGoalsConceded());
            pstmt.setInt(11, team.getID());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.TeamDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM teams WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.TeamDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    public List<Team> findByCountryId(int countryId) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams WHERE country_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, countryId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                teams.add(mapResultSetToTeam(rs));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.TeamDAO Error: " + e.getMessage());
        }
        
        return teams;
    }
    
    private Team mapResultSetToTeam(ResultSet rs) throws SQLException {
        int countryId = rs.getInt("country_id");
        Country country = countryDAO.findById(countryId);
        
        Team team = new Team(rs.getString("long_name"), rs.getString("short_name"), country);
        team.setID(rs.getInt("id"));
        
        int leagueId = rs.getInt("league_id");
        if (!rs.wasNull()) {
            League league = leagueDAO.findById(leagueId);
            if (league != null) {
                team.addLeague(league);
            }
        }
        
        team.setFormation(Formation.valueOf(rs.getString("formation")));
        
        return team;
    }
}
