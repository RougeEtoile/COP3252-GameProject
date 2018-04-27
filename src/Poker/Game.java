package Poker;

import java.util.Vector;
import java.lang.String;
import java.util.Scanner;

public class Game
{
    public DeckofCards deck = new DeckofCards();
    public Vector<Card> board = new Vector<Card>();
    public Player [] players = new Player[2];
    public Rulebook rules = new Rulebook();
    public int pot = 0;
    public int button = 0;     // which player has the button
    public int currentBet = rules.getSmallBet();
    private boolean preFlop = false;
    private boolean flop = false;
    private boolean turn = false;
    private boolean river = false;

    public Game()
    {
        players[0] = new Player();
        players[1] = new Player();
    }
    public boolean isDone()
    {
        if(players[button%2].getCash() < 2)
            return true;
        if(players[(button+1)%2].getCash() < 3)
            return true;

        return false;
    }
    public void reset()
    {
        setAllRespondedFalse();
        for(Player p: players)
        {
            p.setStatusIN();
            p.removeCards();
        }
        board.clear();
        button++;
        preFlop = false;
        flop = false;
        turn = false;
        river = false;
    }
    public void setAllRespondedFalse()
    {
        for( int i = 0; i< players.length; i++)
        {
            players[i].setRespondedFalse();
        }
    }
    public boolean hasAllResponded()
    {
        for(int i = 0; i< players.length; i++)
        {
            if(players[i].isResponded() == false)
                return false;
        }
        return true;
    }
    public boolean bettingEnabled()
    {
        for(Player p: players)
        {
            if(p.getStatus().equals("FOLD") || p.getStatus().equals("ALLIN"))
                return false;
        }
        return true;
    }
    public void initPreFlop()
    {
        if(!preFlop)
        {
            System.out.println("button is " + button);
            System.out.println("button%2 is " + button%2);
            System.out.println("button+1%2 is " + ((button+1)%2));
            setAllRespondedFalse();
            currentBet = rules.getSmallBet();
            players[button%2].gamble(rules.getSmallBlind());
            players[(button+1)%2].gamble(rules.getBigBlind());
            pot += rules.getSmallBlind() + rules.getBigBlind();
            System.out.println("dealing " + button);
            deck.deal();//burn a card
            players[(button+1)%2].addCards(deck.deal());
            players[button%2].addCards(deck.deal());
            players[(button+1)%2].addCards(deck.deal());
            players[button%2].addCards(deck.deal());
            preFlop= true;
        }
    }
    public void initFlop()
    {
        if(!flop)
        {
            setAllRespondedFalse();
            deck.deal();
            currentBet = rules.getSmallBet();
            board.add(deck.deal());
            board.add(deck.deal());
            board.add(deck.deal());
            flop = true;
        }
    }
    public void initTurn()
    {
        if(!turn)
        {
            deck.deal();
            currentBet = rules.getSmallBet();
            board.add(deck.deal());
            setAllRespondedFalse();
            turn = true;
        }
    }
    public void initRiver()
    {
        if(!river)
        {
            deck.deal();
            currentBet = rules.getSmallBet();
            board.add(deck.deal());
            setAllRespondedFalse();
            river = true;
        }
    }
    public String getPlayer1Name()
    {
        return players[0].getName();
    }
    public String getPlayer2Name()
    {
        return players[1].getName();
    }
    public void setPlayer1Name(String x)
    {
        players[0].setName(x);
    }
    public void setPlayer2Name(String x)
    {
        players[1].setName(x);
    }

    public boolean isPreFlop()
    {
        return preFlop;
    }

    public boolean isFlop()
    {
        return flop;
    }

    public boolean isTurn()
    {
        return turn;
    }

    public boolean isRiver()
    {
        return river;
    }

    public DeckofCards getDeck()
    {
        return deck;
    }

    public Vector<Card> getBoard()
    {
        return board;
    }

    public Player[] getPlayers()
    {
        return players;
    }

    public Rulebook getRules()
    {
        return rules;
    }

    public int getPot()
    {
        return pot;
    }

    public int getButton()
    {
        return button;
    }
    public int getCurrentBet()
    {
        return currentBet;
    }

    public void setDeck(DeckofCards d)
    {
        deck = d;
    }

    public void setBoard(Vector<Card> b)
    {
        board = b;
    }

    public void setRules(Rulebook r)
    {
        rules = r;
    }

    public void setPot(int p)
    {
        pot = p;
    }

    public void setButton(int b)
    {
        button = b;
    }

    public void setCurrentBet(int c)
    {
        currentBet = c;
    }

}
