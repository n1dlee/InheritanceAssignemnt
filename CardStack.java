import java.util.Stack;

public class CardStack {
	
	protected Stack<Card> stack;
	
	public CardStack() {
        this.stack = new Stack<Card>();
    }
	
	public void addCard(Card card) {
		// Add the given card to the top of the stack.
		
		if (card != null) {
            stack.push(card);
        }
	}
	
	public boolean canPlayFrom() {
		// Return whether or not a card may be removed from this stack.

		return true;
	}
	
	public Card getTopCard() {
		// Return the top card (or null if empty).
		
		if(stack.isEmpty()) {
			return null;
		}
		
		return stack.peek();
		
	}
	
	public boolean isEmpty() {
		// Return whether or not the card stack is empty.

		return stack.isEmpty();
	}
	
	public boolean playTo(Card card) {
		// Given a card to be played to the top of this card stack, 
		// either make the legal play and return true, or ignore the illegal play and return false.
		
        if (card == null) {
            return false;
        }
        addCard(card);
        return true;
    }
	
	public boolean playTo(CardStack otherStack) {
		// Given a stack from which the top card is to be played to the top of this card stack, 
		// either make the legal play and return true, or ignore the illegal play and return false.

		if(otherStack == null || !otherStack.canPlayFrom() || otherStack.isEmpty()) {
			return false;
		}
		
		Card topCard = otherStack.getTopCard();
		if(this.playTo(topCard)) {
			
			otherStack.removeTopCard();
			return true;
		}
		
		return false; // whether or not the card was played onto this card stack

	}
	
	public Card removeTopCard() {
		// Remove the top card and return it (or null if empty).
		
		if(stack.isEmpty()) {
			return null;
		}
		
		return stack.pop();
	}
	
	public int size() {
		return stack.size();
	}
	
	public Card[] toArray() {
		// Return an array of Card objects in this stack, ordered bottom to top.

		return stack.toArray(new Card[0]);
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < stack.size(); i++) {
			if(i > 0) {
				sb.append(", ");
			}
			sb.append(stack.get(i).toString());
		}
		
		return sb.toString(); 
	}
	
	
	
}
