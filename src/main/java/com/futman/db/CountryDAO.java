package com.futman.db;

import com.futman.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO implements DAO<Country> {
    private Connection connection;
    
    public CountryDAO(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public boolean save(Country country) {
        if (country == null || !country.isValid()) {
            return false;
        }
        
        String sql = "INSERT INTO countries (name, code) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, country.getName());
            pstmt.setString(2, country.getCode());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    country.setID(rs.getInt(1));
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("db.CountryDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Country findById(int id) {
        String sql = "SELECT * FROM countries WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Country country = new Country(rs.getString("name"), rs.getString("code"));
                country.setID(rs.getInt("id"));
                rs.close();
                return country;
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.CountryDAO Error: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT * FROM countries";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Country country = new Country(rs.getString("name"), rs.getString("code"));
                country.setID(rs.getInt("id"));
                countries.add(country);
            }
        } catch (SQLException e) {
            System.err.println("db.CountryDAO Error: " + e.getMessage());
        }
        
        return countries;
    }
    
    @Override
    public boolean update(Country country) {
        if (country == null || !country.isValid()) {
            return false;
        }
        
        String sql = "UPDATE countries SET name = ?, code = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, country.getName());
            pstmt.setString(2, country.getCode());
            pstmt.setInt(3, country.getID());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.CountryDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM countries WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("db.CountryDAO Error: " + e.getMessage());
        }
        
        return false;
    }
    
    public Country findByCode(String code) {
        String sql = "SELECT * FROM countries WHERE code = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Country country = new Country(rs.getString("name"), rs.getString("code"));
                country.setID(rs.getInt("id"));
                rs.close();
                return country;
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("db.CountryDAO Error: " + e.getMessage());
        }
        
        return null;
    }
}
