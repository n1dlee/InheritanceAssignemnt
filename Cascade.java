public class Cascade extends CardStack {
	
	public Cascade() {
	
	}
	public boolean playTo(Card card) { //Return true if playing the card is a legal move
		if (stack.isEmpty()){
			addCard(card);
			return true;
		}
		else if (getTopCard().getRank() == card.getRank() + 1) {
			if ((card.suitIsRed[card.getSuit()] == true) && (getTopCard().suitIsRed[getTopCard().getSuit()] == false)){ //Check if card colors differ
				addCard(card);
				return true;
			} 

			else if ((card.suitIsRed[card.getSuit()] == false) && (getTopCard().suitIsRed[getTopCard().getSuit()] == true)) {//Check if card colors differ
				addCard(card);
				return true;
			}
		}
		
		
		return false;
			
	}

}
