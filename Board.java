package test;

import java.util.ArrayList;
/*
Class name: Board

Member variable:
myBoard: A 2D array of Tile objects representing the game board

Member function:
getTiles(): Returns a copy of the myBoard array.
boardLegal(Word w1): Checks if the word passed as input argument is legal 
to be placed on the board. Returns true if legal, false otherwise.
dictionaryLegal(Word word): Returns true as a placeholder. 
In a real implementation, it would check if the word is in the game dictionary and return true if it is, 
and false otherwise.
getWords(Word w1): Returns a list of words that can be formed by combining 
the letters on the board with the letters of the word passed as input argument.
getScore(Word w1): Returns the score earned for placing the word passed as input argument on the board. 
The score is calculated based on the length of the word and the bonuses earned for using special tiles.
tryPlaceWord(Word w1): Attempts to place the word passed as input argument on the board. 
Returns true if the word was successfully placed, false otherwise.
class operation: The operations that can be performed on the Board class. 
This could include placing a word, checking for legal words, calculating the score, etc.
*/
public class Board {
	private Tile myBoard[][];
    public Board(){
        myBoard=new Tile[15][15];
    }
    
    /*
     * function name: getTiles
     * input: None
     * output: A 2D array of Tile objects
     * functional operation: Copies the contents of the `myBoard` array into a new 2D array `boardCopy` and returns it.
     */
    public Tile[][] getTiles() {
        // Create a copy of the tiles array
        Tile[][] boardCopy = new Tile[15][15];
        for (int i = 0; i < myBoard.length; i++) {
            for (int j = 0; j < myBoard[i].length; j++) {
            	boardCopy[i][j] = myBoard[i][j];
            }
        }
        return boardCopy;
    }


    /*
    function name: boardLegal
    input: Word w1
    output: boolean
    functional operation:
    This function checks if the word passed as input argument w1 is legal to be placed on the board.
    It checks if the word fits within the bounds of the board,
    if the word starts on the star slot if the board is empty,
    and if the word overlaps or is adjacent to an existing tile.
    If all checks pass, the function returns true, else it returns false.
    */
    public boolean boardLegal(Word w1) {
        // Get the tiles, row, column, and orientation of the word
        Tile[] wordTiles = w1.getTiles();
        int row = w1.getRow();
        int col = w1.getCol();
        boolean vertical = w1.getVertical();

     // Check if the whole word is inside the board 
        if ((vertical && (row + wordTiles.length > 15 || row < 0)) ||
            (!vertical && (col + wordTiles.length > 15 || col < 0))){
        	return false;
        }

        // Check if the word can only start on the star slot
        //if the board is empty
        boolean boardOk=false;
        if (myBoard[7][7] == null && ((!(vertical && col == 7 && (row + wordTiles.length > 7)))&&
        		(!(!vertical && row == 7 && (col + wordTiles.length > 7))))){
            return false;
        }
        else if(myBoard[7][7] == null) {
        	boardOk=true;
        	}
        boolean overlap = false;
     //   if((vertical && col>0 && myBoard[row][col-1]!=null)||(!vertical && row > 0 && myBoard[row-1][col]!=null))//check if the word start from a letter from other word
       // 	overlap = true;
        // Check if the word overlaps or is adjacent to an existing tile
        for (int i = 0; i < wordTiles.length; i++) {
            int r = row + (vertical ? i : 0);//if vertical true so r=row+i else r=row
            int c = col + (vertical ? 0 : i);//same for col
            
            if (myBoard[r][c] != null && !(myBoard[r][c].equals(wordTiles[i])||wordTiles[i]==null)) {
            	//if the place is not null and not equal to the current tile then there is overlap and it's not legal
            	return false;
            }
            //check if before or after the word there is a other word already
            if((r > 0 && myBoard[r-1][c] != null) ||(r != 14 && myBoard[r+1][c] != null) || (c > 0 && myBoard[r][c-1] != null) ||(c != 14 && myBoard[r][c+1] != null)) {
            	overlap = true;
            }
            if (myBoard[r][c] != null) {
                overlap = true;
            }
        }
        if (!overlap && !boardOk) {//if overlap is false but the board is still empty so it is the first word
            return false;
        }

        // The word is legal, so return true
        return true;
    }

    
    /*
     * function name: dictionaryLegal
     * input: A Word object
     * output: A boolean value indicating whether the word is legal or not
     * functional operation: Returns `true` as a placeholder. In a real implementation,
     *  the method would check if the word is in the game dictionary and return `true` if it is, and `false` otherwise.
     */
    public boolean dictionaryLegal(Word w1) {
        // For now, we will just return true to indicate that the word is legal
        return true;
        }
    
    
    /*

    function name: getWords
    input: a Word object "w"
    output: an ArrayList of Word objects "newWords"
    functional operation: This function takes a Word object as input and returns an 
    ArrayList of new Word objects created from the given word and tiles on the board. 
    It iterates through each tile in the input word, 
    checks for any existing tiles in the corresponding row or column on the board,
     and adds those tiles and the current tile to form a new Word object and add it to the list of newWords. 
     The function also checks if the new word formed is vertical or horizontal.
    */
    
    public ArrayList<Word> getWords(Word w1) {
        ArrayList<Word> newWords = new ArrayList<Word>();
        Tile[] wordTiles = w1.getTiles();
        int row = w1.getRow();
        int col = w1.getCol();
        boolean vertical = w1.getVertical();
        ArrayList<Tile> wordTile = new ArrayList<Tile>();
        for (int i = 0; i < wordTiles.length; i++) {
            int r = row + (vertical ? i : 0);
            int c = col + (vertical ? 0 : i);
            if(wordTiles[i]==null) {//we lean on tile who in the word so not need to check if there is a new word
            	wordTile.add(myBoard[r][c]);//add the tiles of the word to the list              
            }
            else {//the tile we put is not a null
            	wordTile.add(wordTiles[i]);
            	// Check for new horizontal word
                if (((c > 0 && myBoard[r][c - 1] != null) || (c < 15 && myBoard[r][c + 1] != null)) && vertical ) { 
                    // check if there is an existing tile to the left or to the right when the new word is vertical
                    if(c > 0 && myBoard[r][c - 1] != null){
                    ArrayList<Tile> hTiles = new ArrayList<Tile>();
                    int start = c;
                    while (start > 0 && myBoard[r][start - 1] != null) {
                        start--;
                    }
                    int j;
                    for (j = start; myBoard[r][j] != null && j < 15; j++) {
                        hTiles.add(myBoard[r][j]);
                    }
                    hTiles.add(wordTiles[i]);
                    j++;
                    if(myBoard[r][j] != null) {
                    	for (; myBoard[r][j] != null && j < 15; j++) {
                            hTiles.add(myBoard[r][j]);
                        }	
                    }
                    
                    newWords.add(new Word(hTiles.toArray(new Tile[0]), r, start, false));
                    }
                    else//the new word is vertical and the new word start with the new tile it place
                    {
                        ArrayList<Tile> hTiles = new ArrayList<Tile>();
                        int start = c;
                        hTiles.add(wordTiles[i]);
                        for (int j = start+1; myBoard[r][j] != null && j < 15 ; j++) {
                            hTiles.add(myBoard[r][j]);
                        }
                        newWords.add(new Word(hTiles.toArray(new Tile[0]), r, start, false));
                    }
                }
                
                // Check for new vertical word
                if (((r > 0 && myBoard[r - 1][c] != null)||(r < 15 && myBoard[r + 1][c] != null)) && !vertical) { 
                    // check if there is an existing tile above or below when the new word is horizontal
                    if (r > 0 && myBoard[r - 1][c] != null){
                    ArrayList<Tile> vTiles = new ArrayList<Tile>();
                    int start = r;
                    while (start > 0 && myBoard[start - 1][c] != null) {
                        start--;
                    }
                    int j;
                    for (j = start; myBoard[j][c] != null && j < 15 ; j++) {
                        vTiles.add(myBoard[j][c]);
                    }
                    vTiles.add(wordTiles[i]);
                    j++;
                    if( myBoard[j][c] != null) {
                    	for (; myBoard[j][c] != null && j < 15 ; j++) {
                            vTiles.add(myBoard[j][c]);
                        }
                    }
                    newWords.add(new Word(vTiles.toArray(new Tile[0]), start, c, true));
                    }
                    else//the new word is horizontal and the new word start with the new tile it place
                    {
                        ArrayList<Tile> vTiles = new ArrayList<Tile>();
                        int start = r;
                        vTiles.add(wordTiles[i]);
                        for (int j = start+1; myBoard[j][c] != null && j < 15; j++) {
                            vTiles.add(myBoard[j][c]);
                        }
                        newWords.add(new Word(vTiles.toArray(new Tile[0]), start, c, true));
                    }
                }
            }
        }
        newWords.add(new Word(wordTile.toArray(new Tile[0]), row, col, vertical));
        return newWords;
    }
    
    /*
     * function name: getScore
     * input: Word object `w1`
     * output: Integer score
     * functional operation: calculates the score for the given word `w` placed on the board
     * by adding the scores of individual tiles, considering the effect of double/triple letter and
     * double/triple word bonus fields.
     */
    public int getScore(Word w1) {
        int score = 0;
        Tile[] wordTiles = w1.getTiles();
        int row = w1.getRow();
        int col = w1.getCol();
        boolean vertical = w1.getVertical();
    
        for (int i = 0; i < wordTiles.length; i++) {
            int r = row + (vertical ? i : 0);
            int c = col + (vertical ? 0 : i);
            score += wordTiles[i].getScore();
            //double letter
            if ((r == 0 && c == 3) || (r == 0 && c == 11) || (r == 2 && c == 6) || (r == 2 && c == 8) ||
            (r == 3 && c == 7) || (r == 3 && c == 0) || (r == 3 && c == 14) || (r == 6 && c == 2) ||
            (r == 6 && c == 6) || (r == 6 && c == 8) || (r == 6 && c == 12) || (r == 7 && c == 4) ||
            (r == 7 && c == 11) || (r == 14 && c == 3) || (r == 14 && c == 11) || (r == 12 && c == 6) ||
            (r == 12 && c == 8) || (r == 11 && c == 7) || (r == 11 && c == 0) || (r == 11 && c == 14) ||
            (r == 8 && c == 2) || (r == 8 && c == 6) || (r == 8 && c == 8) || (r == 8 && c == 12)) {
            score += wordTiles[i].getScore();
            }
            //triple letter
            else if ((r == 1 && c == 5) || (r == 1 && c == 9) || (r == 5 && c == 1) || (r == 5 && c == 5) || (r == 5 && c == 9) ||
            (r == 5 && c == 13) || (r == 13 && c == 5) || (r == 13 && c == 9) || (r == 9 && c == 1) || (r == 9 && c == 5) ||
            (r == 9 && c == 9) || (r == 9 && c == 13)) {
            score += wordTiles[i].getScore()*2;
            }
        }
        
        //for double word and triple word
        for (int i = 0; i < wordTiles.length; i++) {
            int r = row + (vertical ? i : 0);
            int c = col + (vertical ? 0 : i);
            if(r==7 && c==7 && myBoard[7][7]==null) {// if we are her so this is the first time in the star
            	score *= 2;
            }
            else{
	            if ((r == 0 && c == 0) || (r == 0 && c == 7) || (r == 0 && c == 14) ||
	                (r == 7 && c == 0) || (r == 7 && c == 14) || (r == 14 && c == 0) ||
	                (r == 14 && c == 7) || (r == 14 && c == 14)) {
	                score *= 3;//triple the total score
	            } else if ((r == 1 && c == 1) || (r == 2 && c == 2) || (r == 3 && c == 3) ||
	                (r == 4 && c == 4) || (r == 10 && c == 10) || (r == 11 && c == 11) ||
	                (r == 12 && c == 12) || (r == 13 && c == 13) || (r == 1 && c == 13) ||
	                (r == 2 && c == 12) || (r == 3 && c == 11) || (r == 4 && c == 10) ||
	                (r == 10 && c == 4) || (r == 11 && c == 3) || (r == 12 && c == 2) || (r == 13 && c == 1)) {
	                score *= 2;//double the total score
	            } 
            }
        }
        return score;
    }
    




    /*
    function name: tryPlaceWord
    input: w1 - an instance of the Word class
    output: int - returns the score of the placed word
    functional operation: This function checks if the given word 'w1' is legally placed on the board and if it is,
					     it calculates the score of the placed word along with any other words 
					     formed as a result of placing the word 'w1' on the board.
					      If the word is not legally placed, the function returns 0.
    */
	public int tryPlaceWord(Word w1) {
        int score = 0;
		if(boardLegal(w1)) {//if the word is legal on the board we start to check the other things else we return 0
			ArrayList<Word> newWords = new ArrayList<Word>();//get the words who create from the new word
			newWords = getWords(w1);
			for (Word word : newWords) {
			    if (!dictionaryLegal(word)) {// if we get false we get in, it mean the word are not legal
			        return 0;
			    }
 			    score += getScore(word);//if we are her we check the score of the word
			}
			Tile[] tiles = w1.getTiles();//take the parameters of the word to put in the board
		    int row = w1.getRow();
		    int col = w1.getCol();
		    boolean vertical = w1.getVertical();
		    for (int i = 0; i < tiles.length; i++) {
		        int r = row + (vertical ? i : 0);
		        int c = col + (vertical ? 0 : i);
		        if (tiles[i]!=null) {//if we place a new tile we put it in the board
		        	myBoard[r][c] = tiles[i];
		        }
		    }
        }
        return score;//if we not enter the first "if" it return 0, else it return the total score of all the word who created
	}

}
