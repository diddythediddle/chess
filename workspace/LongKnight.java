import java.util.ArrayList;

/*
 * Aditya Patil
 * PD:7
 * The LongKnight class represents a chess piece that moves in an extended knight pattern, 
 * jumping either 3 squares in one direction and 1 perpendicular or 1 square in one direction 
 * and 3 squares perpendicular.
 */
public class LongKnight extends Piece {

    /**
     * Constructor for LongKnight.
     * Pre-condition: The superclass constructor requires a valid image file path.
     * Post-condition: A LongKnight piece is created with the specified color and image.
     */
    public LongKnight(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    /**
     * Determines the squares controlled by the piece.
     * Pre-condition: The board must be an 8x8 array of Square objects, and start must be a valid square.
     * Post-condition: Returns an ArrayList of squares that this piece controls.
     */
    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        int[][] knightMoves = {
            {-3, -1}, {-3, 1}, {3, -1}, {3, 1},
            {-1, -3}, {-1, 3}, {1, -3}, {1, 3},
        };

        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                controlledSquares.add(board[newRow][newCol]);
            }
        }
        return controlledSquares;
    }

    /**
     * Determines the legal moves for the piece.
     * Pre-condition: The board must be an 8x8 array of Square objects, and start must be a valid square.
     * Post-condition: Returns an ArrayList of squares that the piece can legally move to.
     */
    @Override
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        Square[][] board = b.getSquareArray();

        int[][] knightMoves = {
            {-3, -1}, {-3, 1}, {3, -1}, {3, 1},
            {-1, -3}, {-1, 3}, {1, -3}, {1, 3}
        };

        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Square sq = board[newRow][newCol];
                if (!sq.isOccupied() || sq.getOccupyingPiece().getColor() != this.getColor()) {
                    moves.add(sq);
                }
            }
        }
        return moves;
    }

    /**
     * Returns a string representation of the piece.
     * Pre-condition: None.
     * Post-condition: Returns a string in the format "A <color> LongKnight".
     */
    @Override
    public String toString() {
        return "A " + (getColor() ? "white" : "black") + " LongKnight";
    }
}
