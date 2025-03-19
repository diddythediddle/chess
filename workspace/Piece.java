import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/*
 * Aditya Patil
 * PD:7
 * The Piece class represents a chess piece with a color and an image. 
 * It has methods to get its color and image, draw it on the board, find which squares it controls, 
 * and calculate its legal moves using a special knight-like movement.
 */
public class Piece {
    private final boolean color; // true for white, false for black
    private BufferedImage img; // Image representation of the piece

    /**
     * Constructor for Piece.
     * Pre-condition: img_file must be a valid path to an image file.
     * Post-condition: A piece is created with the specified color and image.
     */
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Gets the color of the piece.
     * Pre-condition: None.
     * Post-condition: Returns true if the piece is white, false if black.
     */
    public boolean getColor() {
        return color;
    }

    /**
     * Gets the image of the piece.
     * Pre-condition: The image file must be successfully loaded.
     * Post-condition: Returns the BufferedImage of the piece.
     */
    public Image getImage() {
        return img;
    }

    /**
     * Draws the piece on the board.
     * Pre-condition: Graphics object must be valid, and the square must have valid coordinates.
     * Post-condition: The piece's image is drawn at the specified square's location.
     */
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    /**
     * Determines the squares controlled by the piece.
     * Pre-condition: The board must be an 8x8 array of Square objects, and start must be a valid square.
     * Post-condition: Returns an ArrayList of squares that this piece controls.
     */
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        // Custom knight-like movement pattern
        int[][] knightMoves = {
            {-3, -1}, {-3, 1}, {3, -1}, {3, 1},
            {-1, -3}, {-1, 3}, {1, -3}, {1, 3},
        };

        // Checking each potential move
        for (int i = 0; i < knightMoves.length; i++) {
            int newRow = row + knightMoves[i][0];
            int newCol = col + knightMoves[i][1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                controlledSquares.add(board[newRow][newCol]);
            }
        }
        return controlledSquares;
    }

    /**
    * Determines the legal moves for the piece.
   * This piece moves in an extended knight pattern, jumping either 3 squares in one direction 
    * and 1 square perpendicular or 1 square in one direction and 3 squares perpendicular.
    * Pre-condition: The board must be an 8x8 array of Square objects, and start must be a valid square.
     * Post-condition: Returns an ArrayList of squares that the piece can legally move to.
    */
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        Square[][] board = b.getSquareArray();

        // Custom knight-like movement pattern
        int[][] knightMoves = {
            {-3, -1}, {-3, 1}, {3, -1}, {3, 1},
            {-1, -3}, {-1, 3}, {1, -3}, {1, 3}
        };

        // Checking each potential move
        for (int i = 0; i < knightMoves.length; i++) {
            int newRow = row + knightMoves[i][0];
            int newCol = col + knightMoves[i][1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Square sq = board[newRow][newCol];
                if (!sq.isOccupied() || sq.getOccupyingPiece().getColor() != this.color) {
                    moves.add(sq);
                }
            }
        }
        return moves;
    }
}