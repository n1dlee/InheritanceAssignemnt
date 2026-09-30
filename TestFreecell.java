import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TestFreecell {
	static int pass = 0, fail = 0;
	static void check(String name, boolean ok) {
		if (ok) pass++; else fail++;
		System.out.println((ok ? "PASS  " : "FAIL  ") + name);
	}
	static Card c(String rank, String suit) {
		int r = Arrays.asList(Card.rankNames).indexOf(rank);
		int s = Arrays.asList(Card.suitNames).indexOf(suit);
		return new Card(r, s);
	}

	public static void main(String[] args) throws Exception {
		String dir = args[0];

		System.out.println("== CardStack ==");
		CardStack cs = new CardStack();
		check("new stack is empty, size 0", cs.isEmpty() && cs.size() == 0);
		check("getTopCard on empty is null", cs.getTopCard() == null);
		check("removeTopCard on empty is null", cs.removeTopCard() == null);
		check("toString on empty is empty string", cs.toString().equals(""));
		cs.addCard(c("A", "C")); cs.addCard(c("2", "C")); cs.addCard(c("3", "d"));
		check("toString uses comma+space: " + cs, cs.toString().equals("AC, 2C, 3d"));
		check("top card is 3d", cs.getTopCard().toString().equals("3d"));
		check("toArray bottom to top", cs.toArray()[0].toString().equals("AC") && cs.toArray().length == 3);
		check("addCard(null) ignored", sizeAfterNull());
		CardStack empty = new CardStack();
		check("playTo(emptyStack) returns false", !new CardStack().playTo(empty));

		System.out.println("== Cell ==");
		Cell cell = new Cell();
		check("cell accepts card when empty", cell.playTo(c("5", "S")) && cell.size() == 1);
		check("cell rejects 2nd card", !cell.playTo(c("6", "d")) && cell.size() == 1);

		System.out.println("== Foundation ==");
		Foundation f = new Foundation();
		check("foundation rejects non-ace on empty", !f.playTo(c("2", "C")) && f.size() == 0);
		check("foundation accepts ace", f.playTo(c("A", "C")) && f.size() == 1);
		check("foundation rejects wrong suit", !f.playTo(c("2", "d")));
		check("foundation rejects skipped rank", !f.playTo(c("3", "C")));
		check("foundation accepts next rank same suit", f.playTo(c("2", "C")) && f.size() == 2);
		check("cannot play from foundation", !f.canPlayFrom());
		Cell dst = new Cell();
		check("playTo(CardStack) from foundation refused", !dst.playTo(f) && f.size() == 2);

		System.out.println("== Cascade ==");
		Cascade a = new Cascade();
		check("cascade accepts any card when empty (and stores it)", a.playTo(c("7", "S")) && a.size() == 1);
		a = new Cascade(); a.addCard(c("6", "d"));
		check("black 5 on red 6 accepted", a.playTo(c("5", "S")));
		check("  ...and card was actually added (size 2)", a.size() == 2);
		a = new Cascade(); a.addCard(c("6", "S"));
		check("red 5 on black 6 accepted", a.playTo(c("5", "h")));
		check("  ...and card was actually added (size 2)", a.size() == 2);
		a = new Cascade(); a.addCard(c("6", "d"));
		check("red 5 on red 6 rejected", !a.playTo(c("5", "h")) && a.size() == 1);
		check("black 7 on red 6 rejected (wrong rank)", !a.playTo(c("7", "S")) && a.size() == 1);
		check("black 6 on red 6 rejected (same rank)", !a.playTo(c("6", "C")) && a.size() == 1);

		System.out.println("== Moves through stacks ==");
		Cascade src = new Cascade(); src.addCard(c("K", "S")); src.addCard(c("5", "S"));
		Cascade dest = new Cascade(); dest.addCard(c("6", "h"));
		check("cascade->cascade legal move works", dest.playTo(src));
		check("  source lost card, dest gained it", src.size() == 1 && dest.size() == 2 && dest.getTopCard().toString().equals("5S"));
		Cascade bad = new Cascade(); bad.addCard(c("9", "C"));
		check("illegal move leaves both stacks unchanged", !bad.playTo(src) && src.size() == 1 && bad.size() == 1);

		System.out.println("== FreecellGame ==");
		FreecellGame g = new FreecellGame(0);
		int total = 0;
		for (int i = 0; i <= 16; i++) total += g.getStack(i).length;
		check("all 52 cards present after deal", total == 52);
		check("deck (0) is empty after deal", g.getStack(0).length == 0);
		int[] want = {7,7,7,7,6,6,6,6};
		boolean sizes = true;
		for (int i = 0; i < 8; i++) if (g.getStack(9 + i).length != want[i]) sizes = false;
		check("cascade sizes 7,7,7,7,6,6,6,6", sizes);
		check("getStack out of range returns null", g.getStack(-1) == null && g.getStack(17) == null);
		check("not game over at start", !g.isGameOver());
		check("play 0 -> 1 rejected", !g.play(0, 1));
		check("play 1 -> 17 rejected", !g.play(1, 17));
		check("play x -> x rejected", !g.play(9, 9));
		check("play from empty cell rejected", !g.play(1, 2));
		check("play from foundation rejected", !g.play(5, 1));
		check("cascade -> cell legal", g.play(10, 1) && g.getStack(1).length == 1);
		check("toString contains headers", g.toString().contains("Cells:\n") && g.toString().contains("Foundations:\n") && g.toString().contains("Cascades:\n"));
		check("toString formats ' 1: ' and ' 9: ' and '10: '", g.toString().contains("\n 1: ") && g.toString().contains("\n 9: ") && g.toString().contains("\n10: "));

		System.out.println("== File test: java-board-0.board vs deal(seed 0) ==");
		List<String> lines = Files.readAllLines(Paths.get(dir, "java-board-0.board"));
		boolean same = true; int row = 0;
		for (String line : lines) {
			if (line.trim().isEmpty()) continue;
			String[] toks = line.trim().split("\\s+");
			FreecellGame fresh = new FreecellGame(0);
			Card[] got = fresh.getStack(9 + row);
			if (got.length != toks.length) { same = false; }
			else for (int i = 0; i < toks.length; i++) {
				String t = toks[i];
				String suit = t.substring(t.length() - 1).replace("D","d").replace("H","h");
				if (!got[i].toString().equals(t.substring(0, t.length() - 1) + suit)) same = false;
			}
			row++;
		}
		check("deal matches board file (8 cascades read: " + row + ")", same && row == 8);

		System.out.println("== File test: input.txt -> transcript.txt ==");
		String[] moves = Files.readAllLines(Paths.get(dir, "java-board-0.input.txt")).toArray(new String[0]);
		FreecellGame play = new FreecellGame(0);
		int illegal = 0, n = 0;
		for (String m : moves) {
			if (m.trim().isEmpty()) continue;
			String[] p = m.trim().split("\\s+");
			n++;
			if (!play.play(Integer.parseInt(p[0]), Integer.parseInt(p[1]))) illegal++;
		}
		check("all " + n + " moves in input.txt are legal (illegal: " + illegal + ")", illegal == 0);
		check("game is over after input.txt", play.isGameOver());

		System.out.println();
		System.out.println("RESULT: " + pass + " passed, " + fail + " failed");
	}

	static boolean sizeAfterNull() {
		CardStack x = new CardStack(); x.addCard(null);
		return x.size() == 0;
	}
}
