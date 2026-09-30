public class FreecellGame {
	
	/*
  Model of a Freecell game. Card stacks are numbered as follows: 0: deck (not used by player), 1-4: cells, 5-8: foundations, 9-16: cascades The toString method is implemented as shown in text game transcript. For example:
  Cells:
   1: 
   2: 
   3: 
   4: Kh
  Foundations:
   5: AC, 2C, 3C, 4C
   6: Ah, 2h, 3h, 4h, 5h, 6h, 7h
   7: AS
   8: Ad
  Cascades:
   9: 6S, 7S, 5C, 2S, 8d, 6d, Jh, 10C, 9h, 8C, 7d, 6C
  10: 7C
  11: 4S, 3d
  12: 
  13: QC, 8h, 9C, KC, Qd, JS, 10d, 9S
  14: Kd, QS, Jd, 10S, 9d
  15: 10h, 5d, 2d, 8S, JC, 5S, 4d, 3S
  16: KS, Qh
	 */

	private CardStack[] stacks = new CardStack[17];
	
	public FreecellGame(long seed) {
		// Create a Freecell game with a random deal generated with a given seed.
		stacks[0] = new Deck(seed);

		for(int i = 1; i <= 4; i++){
			stacks[i] = new Cell();
		}

		for(int i = 5; i <= 8; i++){
			stacks[i] = new Foundation();
		}
	
		for(int i = 9; i <= 16; i++){
			stacks[i] = new Cascade();
		}

		int cascade = 9;
		while(!stacks[0].isEmpty()){
			Card card = stacks[0].removeTopCard();
			stacks[cascade].addCard(card);
			cascade++;

			if(cascade > 16){
				cascade = 9;
			}
		}
	}
	
	public boolean play(int srcStackNum, int destStackNum) {
		// Attempt to move a card from stack srcStackNum to stack destStackNum. 
		// If the play is legal, the play is made and true is returned. 
		// If the play is not legal, no change is made and false is returned.
		
		if(srcStackNum < 1 || srcStackNum > 16){
			return false;
		}

		if(destStackNum < 1 || destStackNum > 16){
			return false;
		}
		
		if(srcStackNum == destStackNum){
			return false;
		}
		
		return stacks[destStackNum].playTo(stacks[srcStackNum]);
	}
	
	public Card[] getStack(int stackNum) {
		// Return an array with the Card in the given stack number listed from bottom to top.
		
		if(stackNum < 0 || stackNum > 16){
			return null;
		}
		
		return stacks[stackNum].toArray();
	}
	
	public boolean isGameOver() {
		// Return whether or not the game has been won (that is, all cards are on foundations).
		
		int count = 0;
		for(int i = 5; i <= 8; i++){
			count = count + stacks[i].size();
		}

		// game is over when all 52 cards are on the foundations
		return count == 52;
	
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Cells:\n");
		for(int i = 1; i <= 4; i++){
			sb.append(" " + i + ": " + stacks[i] + "\n");
		}
		sb.append("Foundations:\n");
		for(int i = 5; i <= 8; i++){
			sb.append(" " + i + ": " + stacks[i] + "\n");
		}
		sb.append("Cascades:\n");
		for(int i = 9; i <= 16; i++){
			if(i < 10){
				sb.append(" ");
			}
			sb.append(i + ": " + stacks[i] + "\n");
		}
		
		return sb.toString();
	}
}
