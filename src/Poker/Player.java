package Poker;

import java.util.Vector;
import java.lang.String;

public class Player
{
    private String name = "";
    private int cash;
    public Vector<Card> cards = new Vector<Card>();
    private String status ="";
    private boolean responded;

    public Player()
    {
        cash = 40;
        status = "IN";
        responded = false;
    }
    public Player(int bank)
    {
        status = "IN";
        cash = bank;
    }

    public boolean fold()
    {
        status = "FOLD";
        return true;
    }
    public boolean allIn()
    {
        status = "ALLIN";
        cash = 0;
        return true;
    }
    public void setStatusIN()
    {
        status = "IN";
    }
    public String getStatus()
    {
        if(cash == 0)
            status ="ALLIN";
        return status;
    }
    public boolean gamble(int amount)
    {
        if( amount > cash)
            return false;
        else
        {
            cash -= amount;
            if(cash == 0)
                status = "ALLIN";
            //System.out.println("In gamble returning true");
            return true;
        }
    }
    public boolean checkBet(int amount)
    {
        if( amount >= cash)
        {
            return false;
        }
        else
            return true;
    }
    public int getCash()
    {
        return cash;
    }
    public void printHand()
    {
        //System.out.println("in print hand");
        System.out.println(cards.elementAt(0).toString());
        System.out.println(cards.elementAt(1).toString());
    }

    public void addCards(Card x)
    {
        cards.add(x);
    }

    public boolean isResponded()
    {
        return responded;
    }
    public void setRespondedTrue()
    {
        responded = true;
    }
    public void setRespondedFalse()
    {
        responded = false;
    }
    public void addWinnings(int amount)
    {
        cash += amount;
    }
    public void removeCards()
    {
        cards.clear();
    }

    public String getName()
    {
        return name;
    }
    public void setName(String x)
    {
        name = x;
    }
}
