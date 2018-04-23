import java.util.Vector;
import java.util.Random;
import java.lang.String;

public class Poker
{
    public static void main()
    {

    }
}
class Game
{
    private DeckofCards Deck = new DeckofCards();
    private Player [] players;
    private Rulebook rules = new Rulebook();
    private int pot = 0;
    private int button = 0;     // which player has the button

    public Game()
    {
        players = new Player[2];
    }
    public Game(int members)
    {
        players = new Player[members];

    }
    public void play()
    {

    }
    private void preFlop()
    {
        players[button].bet(rules.getSmallBlind());
        players[button +1].bet(rules.getBigBlind());
        int i = button +1;
        while( i < players.length)
        {
            //players[i];
        }


    }


}
class Card
{
    private int suit;
    private int rank;
    public Card( int s, int r)
    {
        suit = s;
        rank = r;
    }
    public String toString()
    {
        String temp = "";
        if(rank == 1)
            temp.concat("Ace of ");
        if(rank == 2)
            temp.concat("Two of ");
        if(rank == 3)
            temp.concat("Three of ");
        if(rank == 4)
            temp.concat("Four of ");
        if(rank == 5)
            temp.concat("Five of ");
        if(rank == 6)
            temp.concat("Six of ");
        if(rank == 7)
            temp.concat("Seven of ");
        if(rank == 8)
            temp.concat("Eight of ");
        if(rank == 9)
            temp.concat("Nine of ");
        if(rank == 10)
            temp.concat("Ten of ");
        if(rank == 11)
            temp.concat("Jack of ");
        if(rank == 12)
            temp.concat("Queen of ");
        if(rank == 13)
            temp.concat("King of ");

        if(suit == 1)
            temp.concat("Spades");
        if(suit == 2)
            temp.concat("Hearts");
        if(suit == 3)
            temp.concat("Diamonds");
        if(suit == 4)
            temp.concat("Clubs");

        return temp;

    }
}
class DeckofCards
{

    private Vector<Card> cards = new Vector<Card>();

    public DeckofCards()
    {
        shuffle();
    }
    public Card deal()
    {
        Card top = cards.firstElement();
        cards.remove(cards.firstElement());
        return top;
    }
    public void shuffle()
    {
        Vector<Integer> seen = new Vector<>();
        cards.clear();
        Random rand = new Random();
        for( int i = 0; i < 52; i++)
        {
            int next = rand.nextInt(52 - 1 + 1) + 1;
            while(seen.contains(next))
            {
                next = rand.nextInt(52 - 1 + 1) + 1;
            }
            int s = 1;
            if(next > 13)
                s = 2;
            if(next > 26)
                s = 3;
            if(next > 39)
                s = 4;

            cards.add(new Card(s, next%13));
        }

    }


}
class Rulebook
{
    private int smallBlind;
    private int bigBlind;
    private int smallBet;
    private int bigBet;

    public Rulebook()
    {
        smallBlind = 5;
        bigBlind = smallBlind *2;
        smallBet = bigBlind;
        bigBet = bigBlind*2;
    }
    public Rulebook(int small)
    {
        smallBlind = small;
        bigBlind = small *2;
        smallBet = bigBlind;
        bigBet = bigBlind*2;
    }
    public int getSmallBlind()
    {
        return smallBlind;
    }
    public int getBigBlind()
    {
        return bigBlind;
    }
    public int getSmallBet()
    {
        return smallBet;
    }
    public int getBigBet()
    {
        return bigBet;
    }
    private static int score()
    {
        return 5;
    }

}
class Player
{
    private int cash;
    private Vector<Integer> cards = new Vector<>();
    private String status ="";

    public Player()
    {
        cash = 100;
        status = "IN";
    }
    public Player(int bank)
    {
        status = "IN";
        cash = bank;
    }

    public void fold()
    {
        status = "FOLD";
    }
    public String getStatus()
    {
        if(cash == 0)
            status ="ALLIN";
        return status;
    }
    public boolean bet(int amount)
    {
        if( amount > cash)
            return false;
        else
        {
            cash -= amount;
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

}
