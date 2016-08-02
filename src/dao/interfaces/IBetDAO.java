package dao.interfaces;

import beans.Bet;

import java.util.ArrayList;

public interface IBetDAO {
    ArrayList<Bet> getBetsByUser(String user);
    boolean addBet(String client, int race, int rider, double amount);
    boolean setBetResultByID(int id, boolean result);
}
