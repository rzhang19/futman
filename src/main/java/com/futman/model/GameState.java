package com.futman.model;

import java.util.List;
import java.util.ArrayList;

public class GameState {
    private static int ID_COUNTER = 1;
    
    private int m_id;
    private Team m_managedTeam;
    private int m_currentDay;
    private Season m_currentSeason;
    
    public GameState(Team managedTeam, Season currentSeason) {
        m_id = ID_COUNTER;
        ID_COUNTER++;
        
        m_managedTeam = managedTeam;
        m_currentSeason = currentSeason;
        m_currentDay = 1;
    }
    
    public int getID() {
        return m_id;
    }
    
    public Team getManagedTeam() {
        return m_managedTeam;
    }
    
    public int getCurrentDay() {
        return m_currentDay;
    }
    
    public Season getCurrentSeason() {
        return m_currentSeason;
    }
    
    public boolean advanceDay() {
        m_currentDay++;
        return true;
    }
    
    public Match getTodayMatch() {
        if (m_currentSeason == null) {
            return null;
        }
        return m_currentSeason.getMatches()[m_currentDay];
    }
    
    public boolean hasMatchToday() {
        return getTodayMatch() != null;
    }
    
    public boolean isSeasonComplete() {
        if (m_currentSeason == null) {
            return false;
        }
        return m_currentSeason.getSeasonCompleted();
    }
    
    public boolean isValid() {
        if (m_managedTeam == null) {
            System.err.println("model.GameState Error: managed team cannot be null");
            return false;
        }
        if (m_currentSeason == null) {
            System.err.println("model.GameState Error: current season cannot be null");
            return false;
        }
        if (m_currentDay < 1) {
            System.err.println("model.GameState Error: current day must be at1");
            return false;
        }
        return true;
    }
}
