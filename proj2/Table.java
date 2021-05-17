
package proj2;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	@author Patti Ordonez
*/
public class Table extends JFrame implements ActionListener
{

	final static int numDealtCards = 9;
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	JList p1HandPile;			//GUI representation of the hand
	JList p2HandPile;
	Deck cardDeck;
	
	Pile stackDeck;			    //New stack implementation using MyStack and Pile classes

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	

	//hand stuff
	Hand P1Hand;
	Hand P2Hand;


	//Jframe ending pop up
	JFrame GameEnd;
	JLabel Winner;

	//variables to implement the turn system
	//variables to keep track of if the stack is used by a player
	//player 1 will be true, player 2 will be false
	Boolean PlayerTurns = true;				
	//boolean value to limit the amount of draws from the deck to one
	// true = has not drawn 
	// false = has drawn a card
	Boolean PlayerDraw = true;		   

	private void deal(Card [] cards)
	{
		for(int i = 0; i < cards.length; i++)
			cards[i] = (Card)cardDeck.dealCard();
	}

	public Table()
	{
		super("D Card Game -John K. Estell");

		setLayout(new BorderLayout());
		setSize(1200,700);
		
		//cat icon for the game because i like cats
		ImageIcon CatImage = new ImageIcon("black-cat.png");
		this.setIconImage(CatImage.getImage());

		cardDeck = new Deck();

		for(int i = 0; i < Card.suit.length; i++){
			for(int j = 0; j < Card.rank.length; j++){
				Card card = new Card(Card.suit[i],Card.rank[j]);
				cardDeck.addCard(card);
			}
		}


		cardDeck.shuffle();
		stackDeck = new Pile();

		
		//add a card to the stack
		Card Topofdeck = cardDeck.dealCard();
		
		stackDeck.addCard(Topofdeck);

		

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));


		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);




		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();


		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();




		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);


		JPanel middle = new JPanel(new GridLayout(1,3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck ");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay   ");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);


		Card [] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);
		//p1Hand = new DefaultListModel();
		P1Hand = new Hand();	
		for(int i = 0; i < cardsPlayer1.length; i++){
			//p1Hand.addElement(cardsPlayer1[i]);
			P1Hand.addCard(cardsPlayer1[i]);
		}

		//display the initial hand of the player 1
		System.out.println("Initial Player 1: " + P1Hand.toString());
		
		
		p1HandPile = new JList(P1Hand.gethand());
		


		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack));

		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);


		stack = new JLabel("Stack ");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + Topofdeck.toString() + ".gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck ");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay  ");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);

		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);
		//p2Hand = new DefaultListModel();
		P2Hand = new Hand();

		for(int i = 0; i < cardsPlayer2.length; i++){
			//p2Hand.addElement(cardsPlayer2[i]);
			P2Hand.addCard(cardsPlayer2[i]);
		}

		//display the initial hand of the player 2
		System.out.println("Initial Player 2: " + P2Hand.toString());


		p2HandPile = new JList(P2Hand.gethand());

		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack));

		add(middle, BorderLayout.CENTER);

		JPanel leftBorder = new JPanel(new GridLayout(2,1));


		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);


		
		

	}



	//function to display a pop up of the game ending and the winner
	public void EndGame(){
		//check witch player won
		int result =  P1Hand.compareTo(P2Hand);
		
		GameEnd = new JFrame("Game over");

		if(result < 0 )
		{
			Winner = new JLabel("Player 1 wins!");
			System.out.println("Player 1 wins!");
		}
		if(result == 0 )
		{
			Winner = new JLabel("It's a tie!");
			System.out.println("It's a tie!");			
		}
		if(result > 0)
		{
			Winner = new JLabel("Player 2 wins!");
			System.out.println("Player 2 wins!");			
		}

		//System.out.println("this is P1 hand " + P1Hand.toString());
		//System.out.println("this is P2 hand " + P2Hand.toString());

		JPanel NP = new JPanel();
		NP.add(Winner);
		GameEnd.add(NP);
		GameEnd.setSize(250,150);
		GameEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameEnd.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		
		//call the function to end the game is the Deck is empty
		if(cardDeck.isEmpty() || P1Hand.isEmpty() || P2Hand.isEmpty()) { 
			EndGame();
		} 
		else
		{
			//player 1 actions
			if(PlayerTurns == true)
			{

				if(p1Deck == src && PlayerDraw == true){

					Card card = cardDeck.dealCard();

					if (card != null){	
						//p1Hand.addElement(card);
						P1Hand.addCard(card);

						//display the player
						System.out.println("Player 1");
						//display if a player drew a card in the terminal
						System.out.println("	Added:" + card);
						PlayerDraw = false;	
					}
					if(cardDeck.getSizeOfDeck() == 0)
						deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));

				}

				if(p1Stack == src && PlayerDraw == true){

					Card card = stackDeck.removeCard();

					if(card != null){
						Card topCard = stackDeck.peek();

						if (topCard != null)
							topOfStack.setIcon(topCard.getCardImage());
						else
							topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));

						if(p1Stack == src){

							//p1Hand.addElement(card);
							P1Hand.addCard(card);

							//display the player
							System.out.println("Player 1");
							//display if a player drew a card in the terminal
							System.out.println("	Added:" + card);

							PlayerDraw = false;
						}


					}

				}

				if(p1Lay == src && PlayerDraw == false ){
					//Object [] cards = p1HandPile.getSelectedValues();
					Card [] cards = P1Hand.findSet();

					if (cards != null)


						for(int i = 0; i < cards.length; i++)
						{
							Card card = (Card)cards[i];
							layCard(card);
							
							P1Hand.removeCard(card);
	
						}
				}



				if(p1LayOnStack == src && PlayerDraw == false){
					int [] num  = p1HandPile.getSelectedIndices();
					if (num.length == 1)
					{
						Object obj = p1HandPile.getSelectedValue();

						if (obj != null)
						{
							//p1Hand.removeElement(obj);
							Card card = (Card)obj;
							P1Hand.removeCard(card);
						
							stackDeck.addCard(card);
							topOfStack.setIcon(card.getCardImage());
							//end the turn after a card is put in the stack
							PlayerTurns = false;	 //change to player 2
							PlayerDraw = true;		 //reset the playerdraw variable 
						

							//display the output 
							System.out.println("	Discarded:" + card);

							//display the current hand
							System.out.print("Hand now: " + P1Hand.toString());
							System.out.println("");

						}
					}
				}

				
			}
			


			//player 2 actions

			if(PlayerTurns == false)
			{
				

				if(p2Deck == src && PlayerDraw == true){

					Card card = cardDeck.dealCard();

					if (card != null){
						//p2Hand.addElement(card);
						P2Hand.addCard(card);

						//display the current player 2
						System.out.println("Player 2");
						
						//display if a player drew a card in the terminal
						System.out.println("	Added:" + card);

						PlayerDraw = false;
					}
					if(cardDeck.getSizeOfDeck() == 0)
						deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));

				}


				if(p2Stack == src && PlayerDraw == true){

					Card card = stackDeck.removeCard();

					if(card != null){
						Card topCard = stackDeck.peek();
						if (topCard != null)
							topOfStack.setIcon(topCard.getCardImage());
						else
							topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));

						if(p2Stack == src){
							//p2Hand.addElement(card);
							P2Hand.addCard(card);
							PlayerDraw = false;


							//display the current player 2
							System.out.println("Player 2");
						
							//display if a player drew a card in the terminal
							System.out.println("	Added:" + card);
						}		

					}

				}


				if(p2Lay == src && PlayerDraw == false){
					//Object [] cards = p2HandPile.getSelectedValues();
					Card [] cards = P2Hand.findSet();

					if (cards != null)
						for(int i = 0; i < cards.length; i++)
						{
							Card card = cards[i];
							layCard(card);
							
							P2Hand.removeCard(card);	
						}
				}


				if(p2LayOnStack == src && PlayerDraw == false){
					int [] num  = p2HandPile.getSelectedIndices();
					if (num.length == 1)
					{
						Object obj = p2HandPile.getSelectedValue();
						if (obj != null)
						{

							//p2Hand.removeElement(obj);
							Card card = (Card)obj;
							P2Hand.removeCard(card);	//copy

							stackDeck.addCard(card);
							topOfStack.setIcon(card.getCardImage());
							//end the turn after a card is put in the stack
							PlayerTurns = true;		//change to player 1
							PlayerDraw = true;		//reset the player Draw variable

							////display the output 
							System.out.println("	Discarded:" + card);

							//display the current hand
							System.out.print("Hand now: " + P2Hand.toString());
							System.out.println("");
						}
					}
				}


				
			}
		}

	}

	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		setPanels[rankIndex].array[suitIndex].setText(card.toString());
		System.out.println("	laying: " + card);
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}

}

class HandPanel extends JPanel
{

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack)
	{
		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

}
class SetPanel extends JPanel
{
	private Set data;
	JButton [] array = new JButton[4];

	public SetPanel(int index)
	{
		super();
		data = new Set(Card.rank[index]);

		for(int i = 0; i < array.length; i++){
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}

}




