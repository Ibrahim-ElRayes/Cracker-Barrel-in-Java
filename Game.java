// Ibrahim El-Rayes
// CSCE 4430 : Programming Languages

import java.util.ArrayList;

public class Game 
{
    public static void main(String[] args) 
	{
        
        Board b = new Board();

        ArrayList<Board.Space> initial = new ArrayList<Board.Space>();

        initial.add(new Board.Space(0,0));
        initial.add(new Board.Space(1,0));
        initial.add(new Board.Space(1,1));
        initial.add(new Board.Space(2,0));
        initial.add(new Board.Space(2,1));

        for(int i = 0; i < initial.size(); i++) 
		{

            Board.Space start = initial.get(i);

            System.out.println("\n--- " + start + " ---");

            b.setup(5, start);

            ArrayList<Board.Move> bs = b.solutionPath();

            System.out.println();
            b.printLocation(b.b);

            for(int j = 0; j < bs.size(); j++){
                System.out.println("\n" + bs.get(j) + "\n");

                b.move(bs.get(j));

                b.printLocation(b.b);
            }
        }
    }
}
