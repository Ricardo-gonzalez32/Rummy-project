package proj2;

//import java.util.*;

public class Pile extends MyStack<Card>{

    //java.util.LinkedList deck;
    MyStack <Card> Pile;
    //private int index;


  /**
   * Creates an empty Pile of cards.
   */
   public Pile() {
      //deck = new LinkedList();

      Pile = new MyStack<>();

   }
	public Card peek()
	{
		if(Pile.length() == 0)
			return null;
		else
			return (Card)Pile.top();
	}

   public void addCard( Card card ) {
      Pile.push( card );
   }


   public int getSizeOfPile() {
      return Pile.length();
   }

//    public Card dealCard() {
   
// 	 if ( deck.size() == 0 )
//          return null;
//       else
       
// 		return (Card) deck.removeFirst();
//    }

   public Card removeCard() {
 
	if (Pile.length() == 0)
         return null;
      else{
		
         return (Card) Pile.pop( );
	}
   }


//   /**
//    * Shuffles the cards present in the deck.
//    */
//    public void shuffle() {
//       Collections.shuffle( deck );
//    }


  /**
   * Looks for an empty deck.
   * @return <code>true</code> if there are no cards left to be dealt from the deck.
   */
   public boolean isEmpty() {
		return Pile.isEmpty();
   }



  /**
   * Restores the deck to "full deck" status.
   */
  public void restorePile() {
	//not sure if kosher
      Pile.clear();
   }
    
}
