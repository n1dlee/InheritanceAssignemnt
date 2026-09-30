public class Foundation extends CardStack{
	
	public Foundation(){
		
	}
	public boolean canPlayFrom() { //Return whether or not a card may be removed from this stack
		return false;
	}
	public boolean playTo(Card card){ //Play a card if the move is legal
		if (stack.isEmpty() && card.getRank() == 0) {
			addCard(card);
			return true;
			
		}
		else {
			if (getTopCard() != null) {
				if (card.getSuit() == getTopCard().getSuit()) {
					if (card.getRank() == getTopCard().getRank() + 1) {
						addCard(card);
						return true;
					}
				}
			}
		}
		return false;
	}
}
