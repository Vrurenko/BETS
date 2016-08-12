package dao.interfaces;

import beans.Race;

import java.util.ArrayList;

public interface IRaceDAO {
    ArrayList<Race> getAllRaces();
    boolean setMultiplier(int id, double multiplier);
    boolean setWinner(int id, int winner);
    double getMultiplierByRaceID(int id);
    boolean addRace(String bookmaker, String admin);
    String getRaceAdmin(int id);
    String getRaceBookmaker(int id);
    boolean hasWinner(int id);
    boolean doesExist(int id);
    boolean hasMultiplier(int id);
}
