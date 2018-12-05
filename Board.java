// Ibrahim El-Rayes
// CSCE 4430 : Programming Languages

import java.util.ArrayList;

public class Board 
{
    boolean[][] b;
    int pegsLeft;
    int size;

    public static int boolToInt(Boolean b) 
	{
		if (b)
		{
			return 1;
		}
		else
		{
			return 0;
		}
    }

    public void printLocation(boolean[][] board) 
	{
        for(int i = 0; i < size; i++) 
		{
            System.out.print("  ");
            for(int k = 0; k < (size - i - 1); k++) 
			{
                System.out.print(" ");
            }
            for(int j = 0; j <= i; j++) 
			{
                System.out.print(Board.boolToInt(board[i][j]));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static class Space 
	{
        int row;
        int column;

        public String toString() 
		{
            return "[" + row + "," + column + "]";
        }
        public Space(int row, int column) 
		{
            this.row = row;
            this.column = column;
        }

        public boolean isValid(int board_size) 
		{
            return (row >= 0) && (row < board_size) && (column >= 0) && (column <= row);
        }
        public void set(int row, int column) 
		{
            this.row = row;
            this.column = column;
        }
    }

    public class Move 
	{
        Board.Space from;
        Board.Space to;

        public String toString() 
		{
            return "from " + from.toString() + " to " + to.toString();
        }

        public Move(Board.Space from, Board.Space to) 
		{
            this.from = from;
            this.to = to;
        }

        public boolean isValid(Board board) 
		{

            if (!from.isValid(board.size) || !to.isValid(board.size)) 
			{
                return false;
            }
            if (!board.b[from.row][from.column]) 
			{
                return false;
            }
            if (board.b[to.row][to.column]) 
			{
                return false;
            }

            int rowJump = Math.abs(from.row - to.row);
            int colJump = Math.abs(from.column - to.column);

            if (rowJump == 0) 
			{
                if (colJump != 2) 
				{
                    return false;
                }
            }
            else if (rowJump == 2) 
			{
                if (colJump != 0 && colJump != 2) 
				{
                    return false;
                }
            }
            else 
			{
                return false;
            }

            return board.b[(from.row + to.row) / 2][(from.column + to.column) / 2];
        }
    }

    public void setup(int n, Board.Space hole) 
	{
        size = n;
        b = new boolean[n][n];
        pegsLeft = -1;

        for (int i = 0; i < n; i++) 
		{
            for (int j = 0; j <= i; j++) 
			{
                b[i][j] = true;
                pegsLeft++;
            }
        }

        b[hole.row][hole.column] = false;
    }

    public boolean move(Move move) 
	{
        if (!move.isValid(this)) 
		{
            System.out.println("Invalid move.");
            return false;
        }

        b[move.from.row][move.from.column] = false;

        b[move.to.row][move.to.column] = true;

        b[(move.from.row + move.to.row) / 2][(move.from.column + move.to.column) / 2] = false;

        pegsLeft--;

        return true;
    }

    public void undoMove(Move move) 
	{

        b[move.from.row][move.from.column] = true;

        b[move.to.row][move.to.column] = false;

        b[(move.from.row + move.to.row)/2][(move.from.column + move.to.column)/2] = true;

        pegsLeft++;
    }

    public ArrayList<Move> validMovesfromsLocation(Space from) 
	{
        ArrayList<Move> moves = new ArrayList<Move>();

        if (!from.isValid(size)) 
		{
            return moves;
        }

        if (!b[from.row][from.column]) 
		{
            return moves;
        }

        Move move = new Move(from, new Space(from.row - 2, from.column));

        if (move.isValid(this)) 
		{
            moves.add(move);
        }

        move = new Move(from, new Space(from.row - 2, from.column - 2));

        if (move.isValid(this)) 
		{
            moves.add(move);
        }

        move = new Move(from, new Space(from.row, from.column - 2));

        if (move.isValid(this)) 
		{
            moves.add(move);
        }

        move = new Move(from, new Space(from.row, from.column + 2));

        if (move.isValid(this)) 
		{
            moves.add(move);
        }

        move = new Move(from, new Space(from.row + 2, from.column));

        if (move.isValid(this)) 
		{
            moves.add(move);
        }

        move = new Move(from, new Space(from.row + 2, from.column + 2));

        if (move.isValid(this)) 
		{
            moves.add(move);
        }

        return moves;
    }

    public ArrayList<Move> validMoves() 
	{
        Space space;
        ArrayList<Move> spaceMoves;
        ArrayList<Move> moves = new ArrayList<Move>();

        for(int i = 0; i < size; i++) 
		{
            for (int j = 0; j <= i; j++) 
			{
                space = new Space(i,j);
                spaceMoves = this.validMovesfromsLocation(space);

                moves.addAll(spaceMoves);
            }
        }

        return moves;
    }

    public ArrayList<Move> solutionPath() {
        ArrayList<Move> path = new ArrayList<Move>();
        ArrayList<Move> moves = this.validMoves();

        if (moves.isEmpty()) {
            return path;
        }

        for (int i = 0; i < moves.size(); i++) {
            this.move(moves.get(i));

            // Win condition
            if (pegsLeft == 1) {
                path.add(moves.get(i));
                this.undoMove(moves.get(i));

                return path;
            }

            // Recurse
            ArrayList<Move> movePath = this.solutionPath();

            if (movePath.size() + 1 > path.size()) {
                path.clear();
                path.add(moves.get(i));
                path.addAll(movePath);
            }

            this.undoMove(moves.get(i));
        }

        return path;
    }
}
