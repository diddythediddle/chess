import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

/**
 * Aditya Patil
 * PD:7
 * Square class represents a square on the chessboard. 
 * Each square can hold a piece and be drawn with a specific color. 
 * The class handles the display of pieces and the square's interaction with them.
 */
@SuppressWarnings("serial")
public class Square extends JComponent {

    private Board b; // The Board to which this square belongs
    
    private final boolean color; // Determines whether the square is white or black
    
    private Piece occupyingPiece; // The piece currently occupying this square
    
    private boolean dispPiece; // Flag to determine whether the piece should be displayed
    
    private int row; // Row position of the square on the board
    private int col; // Column position of the square on the board

    /**
     * Constructor initializes a square on the board with the given color and position.
     * 
     * Preconditions: The 'b' Board object and 'isWhite' color flag must be valid. 
     *               'row' and 'col' must be non-negative integers (0-7).
     * Postconditions: A square object is created with the specified board, color, position,
     *                 and the display flag for pieces is set to true.
     */
    public Square(Board b, boolean isWhite, int row, int col) {
        this.b = b;
        this.color = isWhite;
        this.dispPiece = true;
        this.row = row;
        this.col = col;

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Returns the color of the square (true for white, false for black).
     * 
     * Postconditions: Returns the color of the square, either true or false.
     * 
     */
    public boolean getColor() {
        return this.color;
    }

    /**
     * Returns the piece occupying the square.
     * 
     * Postconditions: Returns the occupying piece if present, or null if the square is empty.
     */
    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    /**
     * Checks if the square is occupied by a piece.
     * 
     * Postconditions: Returns true if the square is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }

    /**
     * Returns the row of the square.
     * 
     * Postconditions: Returns the row index of the square.
     *
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the column of the square.
     * 
     * Postconditions: Returns the column index of the square.
     * 
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Sets whether the piece on the square should be displayed.
     * 
     * Preconditions: 'v' must be a valid boolean value (true or false).
     * Postconditions: Updates the dispPiece flag to either show or hide the piece.
     * 
     */
    public void setDisplay(boolean v) {
        this.dispPiece = v;
    }

    /**
     * Places a piece on the square.
     * 
     * Preconditions: 'p' must be a valid Piece object.
     * Postconditions: The piece 'p' is placed on this square, and the square is marked as occupied.
     */
    public void put(Piece p) {
        this.occupyingPiece = p;
    }

    /**
     * Removes the piece from the square and returns it.
     * 
     * Postconditions: The piece occupying the square is removed, and null is set in its place. 
     *                 Returns the piece that was removed.
     */
    public Piece removePiece() {
        Piece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }

    /**
     * Paints the square, including its color and any occupying piece.
    *
     * Preconditions: The Graphics object 'g' must be valid for painting.
     * Postconditions: The square is painted with its appropriate color, and if a piece is present, it is drawn.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the color based on the square's color (white or black)
        if (this.color) {
            g.setColor(new Color(221, 192, 127)); // Light square color
        } else {
            g.setColor(new Color(101, 67, 33)); // Dark square color
        }

        // Draw the square
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        // If a piece is occupying this square and should be displayed, draw it
        if (occupyingPiece != null && dispPiece) {
            occupyingPiece.draw(g, this);
        }
    }
}
