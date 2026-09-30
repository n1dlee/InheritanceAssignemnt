public class Cell extends CardStack {
	public Cell() {
		
	}
	
	public boolean playTo(Card card) { //Add a card if the stack is empty
		if (stack.isEmpty()) {
			addCard(card);
			return true;
		}
		else
			return false;
	}
}
