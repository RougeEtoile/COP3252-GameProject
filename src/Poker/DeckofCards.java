package Poker;

import java.util.Vector;
import java.util.Random;

public class DeckofCards
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
            seen.add(next);
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

    public Vector<Card> getCards()
    {
        return cards;
    }

}
