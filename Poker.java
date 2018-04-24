import java.util.Vector;
import java.util.Random;
import java.lang.String;
import java.util.Scanner;

public class Poker
{
    public static void main(String [] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("No Limit Texas Holdem Poker");
        System.out.print("Enter the name of player one: ");
        String one = in.next();
        System.out.print("Enter the name of player two: ");
        String two = in.next();

        Game texas = new Game(one, two);
        char input = 's';
        while( input != 'x')
        {
            texas.play();
            System.out.println("Enter x to quit or any other key for a new game");
            input = in.next().charAt(0);
            if(input == 'x')
                break;
            System.out.print("Enter the name of player one: ");
            one = in.next();
            System.out.print("Enter the name of player two: ");
            two = in.next();

            texas = new Game(one, two);
        }
        System.exit(1);
    }
}
class Game
{
    private DeckofCards deck = new DeckofCards();
    private Vector<Card> board = new Vector<Card>();
    private Player [] players = new Player[2];
    private Rulebook rules = new Rulebook();
    private int pot = 0;
    private int currentPot = 0;
    private int button = 0;     // which player has the button
    Scanner in = new Scanner(System.in);

    public Game(String one, String two)
    {
        players[0] = new Player(one);
        players[1] = new Player(two);
    }
    public Game(int members)
    {
        players = new Player[members];

    }
    public void play()
    {
        while( !isDone())
        {
            preFlop();
            setAllRespondedFalse();
            flop();
            setAllRespondedFalse();
            turn();
            setAllRespondedFalse();
            river();
            score();
            reset();
            System.out.println("Enter x to quit or any other key to play another hand");
            char input = in.next().charAt(0);
            if(input == 'x')
                break;

        }
    }
    private void preFlop()
    {
        System.out.println("\nThis is the preflop\n");
        players[button].gamble(rules.getSmallBlind());
        players[button+1].gamble(rules.getBigBlind());
        for( int i =0; i< players.length; i++)
        {
            players[i].addCards(deck.deal());
            players[i].addCards(deck.deal());
        }
        //System.out.println("Player 1");
        if(bettingEnabled()) {
            int current = action(players[button], rules.getSmallBet());
            //System.out.println("hasAllresponded is " + hasAllResponded());
            //System.out.println("Player 2");
            while (hasAllResponded() == false) {
                if(!bettingEnabled())
                    break;
                for(Player p: players)
                {
                    if(!bettingEnabled())
                        break;
                    if(!p.isResponded())
                        current= action(p, current);
                }
                //System.out.println("hasAllresponded is " + hasAllResponded());
            }
        }
    }
    private void flop()
    {
        System.out.println("\nThis is the flop\n");
        setAllRespondedFalse();
        board.add(deck.deal());
        board.add(deck.deal());
        board.add(deck.deal());
        printBoard();
        betting();

    }
    private void turn()
    {
        System.out.println("\nThis is the turn\n");
        setAllRespondedFalse();
        board.add(deck.deal());
        printBoard();
        betting();
    }
    private void river()
    {
        System.out.println("\nThis is the river\n");
        setAllRespondedFalse();
        board.add(deck.deal());
        printBoard();
        betting();
    }
    private void score()
    {
        System.out.println("\nThis is scoring");
        Card [] p1hand = {players[0].cards.elementAt(0), players[0].cards.elementAt(1), board.elementAt(0), board.elementAt(1), board.elementAt(2)};
        Card [] p2hand = {players[1].cards.elementAt(0), players[1].cards.elementAt(1), board.elementAt(0), board.elementAt(1), board.elementAt(2)};
        //System.out.println("Entering scoring functions");
        int p1score = rules.score(p1hand);
        int p2score = rules.score(p2hand);
        if(p1score > p2score || players[1].getStatus().equals("FOLD"))
        {
            System.out.println(players[0].getName() +" has won the hand");
            players[0].addWinnings(pot);

        }
        else if(p2score > p1score || players[0].getStatus().equals("FOLD"))
        {
            System.out.println(players[1].getName() +" has won the hand");
            players[1].addWinnings(pot);
        }
        else
        {
            System.out.println("The hand was a tie");
            players[0].addWinnings(pot/2);
            players[1].addWinnings(pot/2);

        }

    }
    private boolean isDone()
    {
        //System.out.println("In isDone()");
        for( int i =0; i < players.length; i++)
        {
            if(players[i].getCash() == 0)
                return true;
        }
        return false;
    }
    private void reset()
    {
        setAllRespondedFalse();
        for(Player p: players)
        {
            p.setStatusIN();
            p.removeCards();
        }
        board.clear();
        button++;
        button = players.length %2;
    }
    private int getRaise(int bet)
    {
        System.out.print("Enter the amount you like to raise by (total bet): ");
        int raise = in.nextInt();
        while( raise < bet)
        {
            System.out.println("Raise must be equal to or larger than the current bet");
            raise = in.nextInt();
        }
        return raise;
    }
    private int action(Player currentPlayer, int bet)
    {

        if( !currentPlayer.getStatus().equals("FOLD") || !currentPlayer.getStatus().equals("ALLIN"))
        {
            System.out.println("\n" +currentPlayer.getName() + " choose your action");
            actionMenu(bet);
            System.out.println("You have $"+ currentPlayer.getCash());
            char x = in.next().charAt(0);
            boolean valid = false;
            while(!valid)
            {
                switch (x)
                {
                    case 'c':   //check
                        valid = currentPlayer.gamble(bet);
                        if(valid == false)
                            System.out.println("You can not bet by that amount");
                        pot += bet;
                        break;
                    case 'f':
                        valid = currentPlayer.fold();
                        break;
                    case 's':       //show hand
                        currentPlayer.printHand();
                        valid = false;
                        break;
                    case 'r':       //raise
                        int raise = getRaise(bet);
                        valid = currentPlayer.gamble(raise);
                        if(valid = false)
                            System.out.println("You can not raise by that amount");
                        if(valid = true)
                        {
                            bet = raise;
                            pot += bet;
                            setAllRespondedFalse();
                        }
                        break;
                    case 'a':   //all in
                        bet = currentPlayer.getCash();
                        currentPlayer.gamble(bet);
                        pot += bet;
                        valid = currentPlayer.allIn();
                        setAllRespondedFalse();
                        break;


                }
                if(valid == true)
                    break;
                x = in.next().charAt(0);
            }
        }
        currentPlayer.setRespondedTrue();
        return bet;
    }
    private void actionMenu(int bet)
    {
        System.out.println("Hold s to show your hand");
        System.out.println("c to check");
        System.out.println("f to fold");
        System.out.println("r to raise");
        System.out.println("a to all in");
        System.out.println("Current bet is $" + bet);
    }
    private void setAllRespondedFalse()
    {
        for( int i = 0; i< players.length; i++)
        {
            players[i].setRespondedFalse();
        }
    }
    private boolean hasAllResponded()
    {
        for(int i = 0; i< players.length; i++)
        {
            if(players[i].isResponded() == false)
                return false;
        }
        return true;
    }
    private boolean bettingEnabled()
    {
        for(Player p: players)
        {
            if(p.getStatus().equals("FOLD") || p.getStatus().equals("ALLIN"))
                return false;
        }
        return true;
    }
    private void betting()
    {
        if(bettingEnabled()) {
            int current = action(players[button + 1], rules.getSmallBet());
            //System.out.println("hasAllresponded is " + hasAllResponded());
            while (hasAllResponded() == false) {
                if(!bettingEnabled())
                    break;
                for(Player p: players)
                {
                    if(!bettingEnabled())
                        break;
                    if(!p.isResponded())
                        current= action(p, current);
                }
                //System.out.println("hasAllresponded is " + hasAllResponded());
            }
        }
    }
    private void printBoard()
    {
        for (Card x: board)
        {
            System.out.println(x.toString());
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
        //System.out.println("In toString");
        //System.out.println("Rank is " + rank);
        //System.out.println("Suit is " + suit);

        String temp = "";
        if(rank == 14)
            temp = temp.concat("Ace of ");
        else if(rank == 2)
            temp = temp.concat("Two of ");
        else if(rank == 3)
            temp = temp.concat("Three of ");
        else if(rank == 4)
            temp = temp.concat("Four of ");
        else if(rank == 5)
            temp = temp.concat("Five of ");
        else if(rank == 6)
            temp = temp.concat("Six of ");
        else if(rank == 7)
            temp = temp.concat("Seven of ");
        else if(rank == 8)
            temp = temp.concat("Eight of ");
        else if(rank == 9)
            temp = temp.concat("Nine of ");
        else if(rank == 10)
            temp = temp.concat("Ten of ");
        else if(rank == 11)
            temp = temp.concat("Jack of ");
        else if(rank == 12)
            temp = temp.concat("Queen of ");
        else if(rank == 13)
            temp = temp.concat("King of ");
        else
            System.out.println("Error, rank is " +rank);
        if(suit == 1)
            temp = temp.concat("Spades");
        if(suit == 2)
            temp = temp.concat("Hearts");
        if(suit == 3)
            temp = temp.concat("Diamonds");
        if(suit == 4)
            temp = temp.concat("Clubs");
        //System.out.println("Temp is " + temp);
        return temp;
    }
    public int getSuit()
    {
        return suit;
    }
    public int getRank()
    {
        return rank;
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
            next = next %13;
            if(next  == 1)          //ACE is 14
                next =14;
            if(next == 0)
                next = 13;
            cards.add(new Card(s, next ));
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
        smallBlind = 1;
        bigBlind = smallBlind *2;
        smallBet = bigBlind;
        bigBet = bigBlind*2;        //not currently in use
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
    public static int score(Card [] hand)
    {
        //System.out.println("In rules.score()");
        if ( isFlush(hand) && isStraight(hand) )
            return valueStraightFlush(hand);
        else if ( is4s(hand) )
            return valueFourKind(hand);
        else if ( isFullHouse(hand) )
            return valueFullHouse(hand);
        else if ( isFlush(hand) )
            return valueFlush(hand);
        else if ( isStraight(hand) )
            return valueStraight(hand);
        else if ( is3s(hand) )
            return value3Kind(hand);
        else if ( is22s(hand) )
            return valueTwoPairs(hand);
        else if ( is2s(hand) )
            return valueOnePair(hand);
        else
            return valueHighCard(hand);
    }
    private static int valueStraightFlush( Card[] hand )
    {
        return 8000000 + valueHighCard(hand);
    }
    private static int valueFourKind(Card[] hand )
    {
        sortRank(hand);
        return 7000000 + hand[2].getRank();
    }
    private static int valueFullHouse( Card[] hand )
    {
        sortRank(hand);

        return 6000000 + hand[2].getRank();
    }
    private static int valueFlush( Card[] hand )
    {
        return 5000000 + valueHighCard(hand);
    }
    private static int valueStraight( Card[] hand )
    {
        return 4000000 + valueHighCard(hand);
    }
    private static int value3Kind(Card[] hand )
    {
        sortRank(hand);

        return 3000000 + hand[2].getRank();
    }
    private static int valueTwoPairs( Card[] hand )
    {
        int val = 0;

        sortRank(hand);

        if ( hand[0].getRank() == hand[1].getRank() && hand[2].getRank() == hand[3].getRank() )
            val = 14*14*hand[2].getRank() + 14*hand[0].getRank() + hand[4].getRank();
        else if ( hand[0].getRank() == hand[1].getRank() && hand[3].getRank() == hand[4].getRank() )
            val = 14*14*hand[3].getRank() + 14*hand[0].getRank() + hand[2].getRank();
        else
            val = 14*14*hand[3].getRank() + 14*hand[1].getRank() + hand[0].getRank();

        return 2000000 + val;
    }
    private static int valueOnePair( Card[] hand )
    {
        int val = 0;

        sortRank(hand);

        if ( hand[0].getRank() == hand[1].getRank() )
            val = 14*14*14*hand[0].getRank() + hand[2].getRank() + 14*hand[3].getRank() + 14*14*hand[4].getRank();
        else if ( hand[1].getRank() == hand[2].getRank() )
            val = 14*14*14*hand[1].getRank() + hand[0].getRank() + 14*hand[3].getRank() + 14*14*hand[4].getRank();
        else if ( hand[2].getRank() == hand[3].getRank() )
            val = 14*14*14*hand[2].getRank() + hand[0].getRank() + 14*hand[1].getRank() + 14*14*hand[4].getRank();
        else
            val = 14*14*14*hand[3].getRank() + hand[0].getRank() + 14*hand[1].getRank() + 14*14*hand[2].getRank();

        return 1000000 + val;
    }
    private static boolean isFlush( Card [] hand)
    {
        sortSuit(hand);
        return(hand[0].getSuit() == hand[4].getSuit());
    }
    private static boolean isStraight( Card [] hand)
    {
        sortRank(hand);
        if(hand[4].getRank() == 14)         //contains ACE
        {
            if(hand[0].getRank() == 2 && hand[1].getRank() == 3 && hand[2].getRank() == 4 && hand[3].getRank() == 5)
                return true;
            if(hand[0].getRank() == 10 && hand[1].getRank() == 11 && hand[2].getRank() == 12 && hand[3].getRank() == 13)
                return true;
            return false;
        }
        else
        {
            int testRank = hand[0].getRank() + 1;
            for ( int i = 1; i < hand.length; i++ )
            {
                if ( hand[i].getRank() != testRank )
                    return false;
                testRank++;
            }
            return true;
        }
    }
    private static boolean is4s( Card [] hand)
    {
        sortRank(hand);
        if( hand[0].getRank() == hand[1].getRank() && hand[1].getRank() == hand[2].getRank() && hand[2].getRank() == hand[3].getRank())
            return true;
        if(hand[1].getRank() == hand[2].getRank() && hand[2].getRank() == hand[3].getRank() && hand[3].getRank() == hand[4].getRank())
            return true;
        return false;
    }
    private static boolean isFullHouse( Card [] hand)
    {
        return( is3s(hand) && is2s(hand));
    }
    private static boolean is3s( Card[] hand )
    {
        sortRank(hand);
        if(hand[0].getRank() == hand[1].getRank() && hand[1].getRank() == hand[2].getRank())
            return true;
        if( hand[1].getRank() == hand[2].getRank() && hand[2].getRank() == hand[3].getRank() )
            return true;
        if( hand[2].getRank() == hand[3].getRank() && hand[3].getRank() == hand[4].getRank() )
            return true;
        return false;
    }
    private static boolean is22s( Card[] hand )
    {
        sortRank(hand);
        if(hand[0].getRank() == hand[1].getRank() && hand[2].getRank() == hand[3].getRank() )
            return true;
        if( hand[0].getRank() == hand[1].getRank() && hand[3].getRank() == hand[4].getRank() )
            return true;
        if(hand[1].getRank() == hand[2].getRank() && hand[3].getRank() == hand[4].getRank() )
            return true;
        return false;
    }
    private static boolean is2s( Card[] hand )
    {
        sortRank(hand);

        if( hand[0].getRank() == hand[1].getRank() )
            return true;
        if( hand[1].getRank() == hand[2].getRank() )
            return true;
        if( hand[2].getRank() == hand[3].getRank() )
            return true;
        if( hand[3].getRank() == hand[4].getRank() )
            return true;

        return false;
    }

    private static void sortRank( Card [] hand)
    {
        //System.out.println("In rules.sortRank()");
        int i, j;
        int minJ = 0;

        for(i =0; i<hand.length; i++)
        {
            minJ = i;
            for ( j = i+1 ; j < hand.length ; j++ )
            {
                if ( hand[j].getRank() < hand[minJ].getRank() )
                {
                    minJ = j;
                }
            }
            Card help = hand[i];
            hand[i] = hand[minJ];
            hand[minJ] = help;
        }

    }
    private static void sortSuit( Card [] hand)
    {
        //System.out.println("In rules.sortSuit()");
        int i, j, minJ;
        for(i =0; i<hand.length; i++)
        {
            minJ = i;
            for ( j = i+1 ; j < hand.length ; j++ )
            {
                if ( hand[j].getSuit() < hand[minJ].getSuit() )
                {
                    minJ = j;
                }
            }
            Card help = hand[i];
            hand[i] = hand[minJ];
            hand[minJ] = help;
        }

    }
    public static int valueHighCard( Card[] hand )
    {
        int val;
        sortRank(hand);
        val = hand[0].getRank() + 14* hand[1].getRank() + 14*14* hand[2].getRank() + 14*14*14* hand[3].getRank() + 14*14*14*14* hand[4].getRank();
        return val;
    }

}
class Player
{
    private String name;
    private int cash;
    public Vector<Card> cards = new Vector<Card>();
    private String status ="";
    private int handTotal = 0;
    private boolean responded;

    public Player( String x)
    {
        cash = 40;
        status = "IN";
        responded = false;
        name = x;
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
            handTotal += amount;
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
    public int getHandTotal()
    {
        return handTotal;
    }
    public void removeCards()
    {
        cards.clear();
    }

    public String getName()
    {
        return name;
    }
}
