package blackjack;
import java.util.*;

public class Blackjack implements BlackjackEngine {

	/**
	 * Constructor you must provide.  Initializes the player's account 
	 * to 200 and the initial bet to 5.  Feel free to initialize any other
	 * fields. Keep in mind that the constructor does not define the 
	 * deck(s) of cards.
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	private int accountAmount = 200;
	private int betAmount = 5;
	private int gameStatus;
	private Random newRanGen = new Random();
	private int numOfDecks;
	private int playerCardsValue;
	private int dealerCardsValue;
	private ArrayList <Card> mainDeck = new ArrayList<Card>();
	private ArrayList <Card> dealerCards = new ArrayList<Card>();
	private ArrayList <Card> playerCards = new ArrayList<Card>();

	public Blackjack(Random randomGenerator, int numberOfDecks) {
		newRanGen = randomGenerator;
		numOfDecks = numberOfDecks;
	}

	public int getNumberOfDecks() {
		return numOfDecks;
	}

	public void createAndShuffleGameDeck() {   
		ArrayList <Card> Deck = new ArrayList<Card>();
		for(int x = 0 ; x < CardSuit.values().length; x++) {
			for (int y = 0 ; y < CardValue.values().length ; y++) {
				Deck.add(new Card(CardValue.values()[y], CardSuit.values()[x]));
			}
		}
		for (int x = 0 ; x < numOfDecks ; x++) {
			mainDeck.addAll(Deck);
		}
		Collections.shuffle(mainDeck, newRanGen);
	}

	public Card[] getGameDeck() {
		Card[] newDeck = new Card[mainDeck.size()];
		for (int x = 0 ; x < newDeck.length ; x++) {
			newDeck[x] = mainDeck.get(x);
		}
		return newDeck;
	}

	public void deal() {	 
		mainDeck.clear();
		playerCards.clear();
		dealerCards.clear();

		createAndShuffleGameDeck();

		playerCards.add(mainDeck.get(0));
		mainDeck.remove(0);
		dealerCards.add(mainDeck.get(0));
		dealerCards.get(0).setFaceDown();
		mainDeck.remove(0);
		playerCards.add(mainDeck.get(0));
		mainDeck.remove(0);
		dealerCards.add(mainDeck.get(0));
		mainDeck.remove(0);


		gameStatus = GAME_IN_PROGRESS;
		accountAmount -= betAmount;

		playerCardsValue = playerCards.get(0).getValue().getIntValue() 
				+ playerCards.get(1).getValue().getIntValue();
		dealerCardsValue = dealerCards.get(0).getValue().getIntValue() 
				+ dealerCards.get(1).getValue().getIntValue();
		if(dealerCards.get(0).getValue().getIntValue() == 1
				&& dealerCardsValue + 10 <= 21){
			dealerCardsValue += 10;
		}
		else if(dealerCards.get(1).getValue().getIntValue() == 1
				&& dealerCardsValue + 10 <= 21){
			dealerCardsValue += 10;
		}
		if(playerCards.get(0).getValue().getIntValue() == 1
				&& playerCardsValue + 10 <= 21){
			playerCardsValue += 10;
		}
		else if(playerCards.get(1).getValue().getIntValue() == 1
				&& playerCardsValue + 10 <= 21){
			playerCardsValue += 10;
		}
	}

	public Card[] getDealerCards() {
		Card[] newDealerCards = new Card[dealerCards.size()];
		for(int x = 0 ; x < dealerCards.size() ; x++) {
			newDealerCards[x] = dealerCards.get(x);
		}
		return newDealerCards;
	}

	public int[] getDealerCardsTotal() {
		int smallestDealerValue = 0;
		for(int x=0; x<dealerCards.size(); x++ ) {
			smallestDealerValue += dealerCards.get(x).getValue().getIntValue();
		}
		int[]dealerValue1 = new int[1];
		int[]dealerValue2 = new int[2];
		if (smallestDealerValue > 21) {
			return null;
		}
		if (smallestDealerValue == dealerCardsValue) {
			dealerValue1[0] = dealerCardsValue;
			return dealerValue1;
		}
		else if (dealerCardsValue <= 21){
			dealerValue2[0] = smallestDealerValue;
			dealerValue2[1] = dealerCardsValue;
			return dealerValue2;
		}
		return null;
	}

	public int getDealerCardsEvaluation() {
		if (dealerCardsValue < 21) {
			return LESS_THAN_21;
		}
		else if (dealerCardsValue > 21) {
			return BUST;
		}
		else if (dealerCards.get(0).getValue().getIntValue() == 1 && 
				dealerCards.get(1).getValue().getIntValue()==10) {
			return BLACKJACK;
		}
		else if (dealerCards.get(1).getValue().getIntValue() == 1 && 
				dealerCards.get(0).getValue().getIntValue()==10) {
			return BLACKJACK;
		}
		else if (dealerCardsValue == 21) {
			return HAS_21;
		}
		return 0;
	}

	public Card[] getPlayerCards() {
		Card[] newPlayerCards = new Card[playerCards.size()];
		for(int x = 0 ; x < playerCards.size() ; x++) {
			newPlayerCards[x] = playerCards.get(x);
		}
		return newPlayerCards;
	}

	public int[] getPlayerCardsTotal() {
		int smallestPlayerValue = 0;
		for(int x=0; x<playerCards.size(); x++ ) {
			smallestPlayerValue += playerCards.get(x).getValue().getIntValue();
		}
		int[]playerValue1 = new int[1];
		int[]playerValue2 = new int[2];
		if (smallestPlayerValue > 21) {
			return null;
		}
		if (smallestPlayerValue == playerCardsValue) {
			playerValue1[0] = playerCardsValue;
			return playerValue1;
		}
		else if (playerCardsValue <= 21) {
			playerValue2[0] = smallestPlayerValue;
			playerValue2[1] = playerCardsValue;
			return playerValue2;
		}
		return null;
	}

	public int getPlayerCardsEvaluation() {
		if (playerCardsValue < 21) {
			return LESS_THAN_21;
		}
		else if (playerCardsValue > 21) {
			return BUST;
		}
		else if (playerCards.get(0).getValue().getIntValue() == 1 && 
				playerCards.get(1).getValue().getIntValue()==10) {
			return BLACKJACK;
		}
		else if (playerCards.get(1).getValue().getIntValue() == 1 && 
				playerCards.get(0).getValue().getIntValue()==10) {
			return BLACKJACK;
		}
		else if (playerCardsValue == 21) {
			return HAS_21;
		}
		return 0;
	}

	public void playerHit() {
		int count = 0;
		playerCards.add(mainDeck.get(0));
		for (int x = 0; x < playerCards.size(); x++) {
			if(playerCards.get(x).getValue().getIntValue() ==1) {
				count++;
			}
		}
		int smallestPlayerValue = 0;
		for(int x=0; x<playerCards.size(); x++ ) {
			smallestPlayerValue += playerCards.get(x).getValue().getIntValue();
		}
		if(count >= 1 && smallestPlayerValue < 12) {
			playerCardsValue = smallestPlayerValue + 10;
		}
		else {
			playerCardsValue = smallestPlayerValue;
		}
		mainDeck.remove(0);

		if (playerCardsValue > 21) {
			gameStatus = DEALER_WON;
		}
		else if (playerCardsValue < 21) {
			gameStatus = GAME_IN_PROGRESS;
		}

	}

	public void playerStand() {
		dealerCards.get(0).setFaceUp();
		while (dealerCardsValue < 16 && dealerCardsValue < 21) {
			dealerCards.add(mainDeck.get(0));
			int count = 0;
			for (int x = 0; x < dealerCards.size(); x++) {
				if(dealerCards.get(x).getValue().getIntValue() ==1) {
					count++;
				}
			}
			int smallestDealerValue = 0;
			for(int x=0; x<dealerCards.size(); x++ ) {
				smallestDealerValue += dealerCards.get(x).getValue().getIntValue();
			}
			if(count >= 1 && smallestDealerValue < 12) {
				dealerCardsValue = smallestDealerValue + 10;
			}
			else {
				dealerCardsValue = smallestDealerValue;
			}
			mainDeck.remove(0);
		}

		if (dealerCardsValue >= 16 && dealerCardsValue <= 21) {
			if (dealerCardsValue > playerCardsValue) {
				gameStatus = DEALER_WON;
			}
			if (dealerCardsValue < playerCardsValue) {
				gameStatus = PLAYER_WON;
				int amount = 2 * betAmount;
				accountAmount += amount;
			}
			if (dealerCardsValue == playerCardsValue) {
				gameStatus = DRAW;
				accountAmount += betAmount;
			}
		}
		else {
			gameStatus = PLAYER_WON;
			int amount = 2 * betAmount;
			accountAmount += amount;
		}
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setBetAmount(int amount) {
		betAmount = amount;
	}

	public int getBetAmount() {
		return betAmount;
	}

	public void setAccountAmount(int amount) {	
		accountAmount = amount;
	}

	public int getAccountAmount() {
		return accountAmount;
	}

	/* Feel Free to add any private methods you might need */
}