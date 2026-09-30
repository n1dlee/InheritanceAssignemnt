import java.util.Random;
import java.util.Scanner;

public class Freecell {
	
	public Freecell() {
		
	}
	
	public void play() {
		// Play a Freecell game with a random shuffle.
		Random rand = new Random();
		play(rand.nextLong());
		
	}
	
	public void play(long seed) {
		// Play a Freecell game with a random shuffle performed according to a given random seed.
		FreecellGame game = new FreecellGame(seed);
		Scanner in = new Scanner(System.in);

		while(true){
			System.out.println(game);

			if(game.isGameOver()){
				System.out.println("You win!");
				return;
			}
			System.out.print("Please enter source and destination card stacks, or \"-1\" to quit: ");
			if(!in.hasNextInt()){
				return;
			}
			int source = in.nextInt();
			if(source == -1){
				return;
			}

			if(!in.hasNextInt()){
				return;
			}
			int destination = in.nextInt();

			if (source < 1 || source > 16 || destination < 1 || destination > 16) {
				System.out.println("Illegal stack move.");
			} 
			else if(!game.play(source, destination)){
					System.out.println("Illegal play.");
			}

			
		}

	}
	
	public static void main(String[] args) {
		// Start a Freecell game with seed 0.
		
		Freecell game = new Freecell();
		game.play(0);
		
	}
}
