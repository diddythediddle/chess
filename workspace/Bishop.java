

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
//you will need to implement two functions in this file.
public class Bishop extends Piece{
//private final boolean color;
//private BufferedImage img;
public Bishop(boolean isWhite, String img_file){
super(isWhite, img_file);
}
public String toString(){
if(getColor() == true){
return "A white Bishop";
} else {
return "A black Bishop";
}
}
//public boolean isInCheck(Boolean color){
//for(//loop through board// ){
//find all pieces != color
//for(loop of all controlled squares){
//squares look for instances of king
//}
//}
//}
/*public boolean getColor() {
return color;
}
public Image getImage() {
return img;
}
public void draw(Graphics g, Square currentSquare) {
int x = currentSquare.getX();
int y = currentSquare.getY();
g.drawImage(this.img, x, y, null);
}
// TO BE IMPLEMENTED!
//return a list of every square that is "controlled" by this piece. A square is
controlled
//if the piece capture into it legally.
*/
//pre-condition: input cannot be empty and the piece must be moved by the

//post-condition: returns a list of every square that is controlled.
public ArrayList<Square> getControlledSquares(Square[][] b0ard, Square start) {
ArrayList<Square> moves = new ArrayList<Square>();
//up and right
for(int j = start.getCol()+1, i = start.getRow()-1; i >= 0 && j <
b0ard[i].length; j++, i--){
moves.add(b0ard[i][j]);
}
for(int k = start.getRow()-1, l = start.getCol()-1; k >= 0 && l >= 0; l--,
k--){
moves.add(b0ard[k][l]);
}
return moves;
}
//TO BE IMPLEMENTED!
//implement the move function here
//it's up to you how the piece moves, but at the very least the rules should be

//returns an arraylist of squares which are legal to move to
//please note that your piece must have some sort of logic. Just being able to

//going to score any points.//I am making the bishop
//pre-condition: input cannot be empty and the piece must be moved by the

//post-condition: returns an arraylist of squares which are legal to move to
public ArrayList<Square> getLegalMoves(Board b, Square start){
ArrayList<Square> moves = new ArrayList<Square>();
Square[][] b0ard = b.getSquareArray();
//moves up left
for(int j = start.getCol()+1, i = start.getRow()-1; i >= 0 && j <
b0ard[i].length; j++, i--){
if(b0ard[i][j].isOccupied()){
if(b0ard[i][j].getOccupyingPiece().getColor() != getColor()){
moves.add(b0ard[i][j]);
}
break;
}
moves.add(b0ard[i][j]);
}
//moves up right
for(int k = start.getRow()-1, l = start.getCol()-1; k >= 0 && l >= 0; l--,
k--){
if(b0ard[k][l].isOccupied()){
if(b0ard[k][l].getOccupyingPiece().getColor() != getColor()){
moves.add(b0ard[k][l]);
}
break;
}
moves.add(b0ard[k][l]);
}
//moves down left
for(int j = start.getCol()-1, i = start.getRow()+1; i < 8 && j >= 0; j--, i++){
if(b0ard[i][j].isOccupied()){
if(b0ard[i][j].getOccupyingPiece().getColor() != getColor()){
moves.add(b0ard[i][j]);
}
break;
}
moves.add(b0ard[i][j]);
}
//moves down right
for(int k = start.getRow()+1, l = start.getCol()+1; k < 8 && l < 8; l++, k++)
{
if(b0ard[k][l].isOccupied()){
if(b0ard[k][l].getOccupyingPiece().getColor() != getColor()){
moves.add(b0ard[k][l]);
}
break;
}
moves.add(b0ard[k][l]);
}
return moves;
}
}
