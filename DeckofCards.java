import java.util.Vector;
import java.util.Random;

class DeckofCards
{

    private Vector<Integer> cards = new Vector<>();

    public Deck()
    {
        Random rand = new Random();
        for( int i = 0; i < 52; i++)
        {
            int next = rand.NextInt(52 - 1 + 1) + 1;
            while(cards.contains(next))
            {
                next = rand.NextInt(52 - 1 + 1) + 1;
            }
            cards.add(next);
        }
    }
    public int Deal()
    {
        int top = cards.firstElement();
        cards.remove(cards.firstElement());
        return top;
    }

}