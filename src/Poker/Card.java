package Poker;

import java.lang.String;

public class Card
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

        String temp = "img/";
        if(rank == 14)
            temp = temp.concat("A");
        else if(rank == 2)
            temp = temp.concat("2");
        else if(rank == 3)
            temp = temp.concat("3");
        else if(rank == 4)
            temp = temp.concat("4");
        else if(rank == 5)
            temp = temp.concat("5");
        else if(rank == 6)
            temp = temp.concat("6");
        else if(rank == 7)
            temp = temp.concat("7");
        else if(rank == 8)
            temp = temp.concat("8");
        else if(rank == 9)
            temp = temp.concat("9");
        else if(rank == 10)
            temp = temp.concat("10");
        else if(rank == 11)
            temp = temp.concat("J");
        else if(rank == 12)
            temp = temp.concat("Q");
        else if(rank == 13)
            temp = temp.concat("K");
        else
            System.out.println("Error, rank is " +rank);
        if(suit == 1)
            temp = temp.concat("S");
        if(suit == 2)
            temp = temp.concat("H");
        if(suit == 3)
            temp = temp.concat("D");
        if(suit == 4)
            temp = temp.concat("C");
        temp = temp.concat(".jpg");
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
    
    //Setters
    public void setSuit(int s)
    {
        suit = s;
    }
    
    public void setRank(int r)
    {
        rank = r;
    }
}
