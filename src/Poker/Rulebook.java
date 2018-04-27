package Poker;

public class Rulebook
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
