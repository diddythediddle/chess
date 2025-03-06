import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

/**
 * Aditya Patil
 * PD:7
 * Board class represents the chessboard, handles the layout of squares, 
 * and the logic for moving pieces. It listens for mouse events to handle user interactions.
 * 
 * Pre-condition: The GameWindow instance is passed to the constructor to manage the game state.
 * Post-condition: The board is initialized with squares and pieces placed on them.
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    
    private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
    
    private final Square[][] board; 
    private final GameWindow g;      
    private boolean whiteTurn;      
    
    private Piece currPiece;       
    private Square fromMoveSquare;  
    
    private int currX;             
    private int currY;              

    /**
     * Constructor to initialize the board and pieces.
     * Pre-condition: The GameWindow object is passed for managing game state.
     * Post-condition: The board is initialized with empty squares and pieces.
     */
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0)); // Grid layout for 8x8 chessboard
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isWhite = (row + col) % 2 == 0; 
                board[row][col] = new Square(this, isWhite, row, col);
                this.add(board[row][col]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    /**
     * Initializes pieces at their starting positions.
     * Pre-condition: The squares are initialized and empty.
     * Post-condition: Pieces are placed on the board in their initial positions.
     */
    private void initializePieces() {
        // Place knights on the board
        board[0][2].put(new Piece(true, RESOURCES_WKNIGHT_PNG)); 
        board[0][5].put(new Piece(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Piece(false, RESOURCES_BKNIGHT_PNG)); 
        board[7][5].put(new Piece(false, RESOURCES_BKNIGHT_PNG));
    }

    /**
     * Gets the 2D array of squares representing the board.
     * Pre-condition: The board is initialized.
     * Post-condition: The board is returned as a 2D array of squares.
     */
    public Square[][] getSquareArray() {
        return this.board;
    }

    /**
     * Gets the current player's turn status.
     * Pre-condition: The current turn (white or black) is set.
     * Post-condition: The current turn status is returned.
     */
    public boolean getTurn() {
        return whiteTurn;
    }

    /**
     * Sets the currently selected piece.
     * Pre-condition: A piece is selected.
     * Post-condition: The selected piece is set to the given piece.
     */
    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    /**
     * Gets the currently selected piece.
     * Pre-condition: A piece is selected.
     * Post-condition: The selected piece is returned.
     */
    public Piece getCurrPiece() {
        return this.currPiece;
    }

    /**
     * Paints the board and the pieces on the board.
     * Pre-condition: The board and pieces are initialized.
     * Post-condition: The board and pieces are painted to the screen.
     */
    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue)); // Highlight the source square
                sq.paintComponent(g);
            }
        }

        if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn) || (!currPiece.getColor() && !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    /**
     * Handles the event when a mouse is pressed.
     * Pre-condition: A mouse button is pressed.
     * Post-condition: The selected piece is captured, and the move square is highlighted.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            if (!currPiece.getColor() && whiteTurn)
                return;
            if (currPiece.getColor() && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    /**
     * Handles the event when the mouse is released.
     * Pre-condition: The piece is moved to a new square.
     * Post-condition: The piece is placed on the new square, or it is returned to its original position if the move is illegal.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
    
        if (currPiece == null || fromMoveSquare == null) {
            return;
        }
    
        if ((whiteTurn && !currPiece.getColor()) || (!whiteTurn && currPiece.getColor())) {
            return;
        }
    
        ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);
    
        boolean isLegalMove = false;
        for (int i = 0; i < legalMoves.size(); i++) {
            if (legalMoves.get(i) == endSquare) {
                isLegalMove = true;
                break;
            }
        }
    
        if (isLegalMove && (endSquare == null || !endSquare.isOccupied() || 
            endSquare.getOccupyingPiece().getColor() != currPiece.getColor())) {
            fromMoveSquare.removePiece();
            endSquare.put(currPiece);
            whiteTurn = !whiteTurn;  // Change turn after a successful move
        } else {
            fromMoveSquare.put(currPiece);
        }
    
        // Remove border highlights from all squares
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setBorder(null);
            }
        }
    
        fromMoveSquare.setDisplay(true); // Make sure the square is displayed again
        currPiece = null;  // Clear the selected piece
        fromMoveSquare = null;  // Clear the move square
        repaint();
    }

    /**
     * Handles dragging the piece around the board.
     * Pre-condition: A piece is being dragged.
     * Post-condition: The piece is updated at the mouse location, and legal move squares are highlighted.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
        if (currPiece != null) {
            for (Square s : currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.setBorder(BorderFactory.createLineBorder(Color.green));
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
