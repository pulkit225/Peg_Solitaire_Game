import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class PegSolitaire {
class Move implements Comparable<Move>{
int from; // from where the marble is being moved
int hole; // to which hole its being moved
int to; // to which hole its being moved
Move(int from, int hole, int to){
// constructor
this.from = from;
this.hole = hole;
this.to = to;
}
// to print it we can use toString method
public String toString() {
return ("("+ this.from + "," + this.hole + "," + this.to + ")");
}
@Override
public int compareTo(Move m) {
return Integer.valueOf(this.from).compareTo(Integer.valueOf(m.from)); // comparing the
values and sorting in ascending order
}
}
ArrayList<ArrayList<Integer>> grid; // we store the array of the arrays for the board in the form of 1's and -1's
ArrayList<Move> movesList; // movesList stores from one location to another location.
ArrayList<ArrayList<ArrayList<Integer>>> unsuccessful Grid; // we are going to store the grids which has arraylist of arraylist inside an another arraylist

Then we have a peg Solitaire constructor which initializes the grid on which the input is passed from the main. The printOutput method calls the toString method above to print the possibilities of the hole from which it is jumping to the hole which it is jumping. The displayGrid method calls the forEach method for each input given from the user and if it's -1 then the - is put and if it’s not -1 then the toString method is called for the grid occupancy.

Pegsolitaire(ArrayList<ArrayList<Integer>> grid){
// constructor function for the initialize the peg Solitaire function
this.grid = grid; // it initializes the grid given by the hardcoded
movesList = new ArrayList<>(); // initializes the new array list for moveslist
unsuccessfullGrid = new ArrayList<>();
}
// we will write the method to print all the moves it has performed when called.
private void printOutput() {
for (Move move: movesList) {
System.out.println(move.toString()); // we had already created the move toString to print
output
}
}
private void displayGrid() {
//to print the entire grid
for (ArrayList<Integer> line : grid) {
for (int i: line) {
if (i == -1 ) {
System.out.print("- "); // whenever there is no grid value print the -
}
else {
System.out.print(Integer.toString(i)+ " ");
}
}
System.out.println();
}
}

The makeMove method takes the input as move and calls its own class Move and it gets the input from the column as it divides the move input/7 to indicate the row number, it gets the row number from get method and then sets the pointer to the column number by modulo division by 7 and sets the value to 1. And remaining rows are set to 0. As it is described, the hole which it is jumping is set to 0 and the hole from which it is jumping is set to 0 and the hole to which it is jumping is set as 1. The undoMove method also does the same thing as makeMove method but undoes the false moves in the from hole, in hole and to hole method and removes the added move in the movesList.
private void makeMove(Move move) {
grid.get(move.from/7).set(move.from % 7, 0); // we will get the number from the row number using the /7 and the column number using the %
grid.get(move.hole/7).set(move.hole % 7, 0);
grid.get(move.to/7).set(move.to % 7, 1);
movesList.add(move); // we add it to the arraylist of the moveList
}
private void UndoMove(Move move) {
grid.get(move.from/7).set(move.from % 7, 1); // we will undo the marble jumped on
grid.get(move.hole/7).set(move.hole % 7, 1);
grid.get(move.to/7).set(move.to % 7, 0);
movesList.remove(movesList.size()-1); // we will remove the last number from the grid. similar to pop
}
The deepCopy method takes the arraylist of arraylist as an input by initializing the return statement as arraylist of arraylist in the methods. It calls each input from the main method after computing the answer and then adds the remaining grid to the unsuccessful grid and also copies each iteration into the new grid as a copy, because whenever other methods call the deepCopy method it can have a look at the previous iteration and then complete the possible move based on it. The getCount method gets the count of number of 1 by pointing at the row then at the column and increases the counter based on it as to verify the method in the end of having only one possible 1 in the end. The solve method is the heart of the problem to verify the problem, if there is unsuccessful grid present same as the input
then the input might be wrong, or the computation is not done. If the count is 1 and the middle row has 1 as the output after computation, then it will call the display and print methods or else it will again sort all the moves in the move list and call the compute possibilities method to verify the method once again which calls the undoMove and makeMove method.

private ArrayList<Move> computePossibilities() {
// return the possibilities the list of moves
ArrayList<Move> possibilities = new ArrayList<>();
// we need to check the marbles that are two lengths difference or available to left right or top or bottom
for(int i = 0 ; i<grid.size(); i++) {
for (int j = 0; j<grid.get(i).size(); j++) {
if (grid.get(i).get(j) == 0) {
if ((i-2) >= 0) {
if ((grid.get(i-2).get(j) == 1) && (grid.get(i-1).get(j) == 1)) {
possibilites.add(new Move((i-2)*7+j,((i-1)*7+j), (i*7+j)));
}
}
if ((j-2) >= 0) {
if ((grid.get(i).get(j-2) == 1) && (grid.get(i).get(j-1) ==1))
{
possibilites.add(new Move(i*7 + j-2 , i*7 + j-1, i*7 +j));
}
}
if (i+2 <= 6){
if ((grid.get(i+2).get(j) == 1) && (grid.get(i+1).get(j) == 1)) {
possibilites.add(new Move((i+2)*7+j,(i+1)*7+j, i*7+j));
}
}
if (j+2 <= 6) {
if((grid.get(i).get(j+2) == 1) && grid.get(i).get(j+1) == 1) {
possibilites.add(new Move(i*7+j+2, i*7+j+1, i*7+j));
}
}
}
}
}
return possibilities;
}
private ArrayList<ArrayList<Integer>> deepCopy(ArrayList<ArrayList<Integer>> input){
ArrayList<ArrayList<Integer>> newGrid=new ArrayList<>();
for (ArrayList<Integer> line:input)
{
ArrayList<Integer> cpLine=new ArrayList<>();
for(Integer i:line) {
cpLine.add(Integer.valueOf(i));
}
newGrid.add(cpLine);
}
return newGrid;
}
private int getCount() {
int count = 0;
for (int i =0 ; i<grid.size();i++) {
for(int j =0 ; j<grid.get(i).size(); j++) {
if (grid.get(i).get(j) == 1) {
count ++;
}
}
}
return count;
}
public boolean solve() {
if (unsuccessfullGrid.contains(grid)) {
return false;
}
if (getCount() == 1 && grid.get(3).get(3) == 1) {
displayGrid();
printOutput();
return true;
}
else {
ArrayList<Move> moves = computePossibilities();
Collections.sort(moves);

for (Move move: moves) {
makeMove(move);
if (solve()) {
return true;
}
else {
UndoMove(move);

}
}
}
if (!unsuccessfullGrid.contains(grid)){
unsuccessfullGrid.add(deepCopy(grid));
}
return false;
}
public static void main(String args[]) {
ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
Integer[] line1 = {-1,-1,1,1,1,-1,-1};
Integer[] line2 = {-1,-1,1,1,1,-1,-1};
Integer[] line3 = {1,1,1,1,1,1,1};
Integer[] line4 = {1,1,1,0,1,1,1};
Integer[] line5 = {1,1,1,1,1,1,1};
Integer[] line6 = {-1,-1,1,1,1,-1,-1};
Integer[] line7 = {-1,-1,1,1,1,-1,-1};
grid.add(new ArrayList<Integer>(Arrays.asList(line1)));
grid.add(new ArrayList<Integer>(Arrays.asList(line2)));
grid.add(new ArrayList<Integer>(Arrays.asList(line3)));
grid.add(new ArrayList<Integer>(Arrays.asList(line4)));
grid.add(new ArrayList<Integer>(Arrays.asList(line5)));
grid.add(new ArrayList<Integer>(Arrays.asList(line6)));
grid.add(new ArrayList<Integer>(Arrays.asList(line7)));
new Pegsolitaire(grid).solve();
}
}
