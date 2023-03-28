package test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/*
Class name: Tile

member variable:
letter: character
score: integer

member function:
hashCode: returns hashcode of the object
equals: compares if two objects are equal
getLetter: returns the letter of the tile
getScore: returns the score of the tile

functional operation: represents a tile object with a letter and a score.
*/
public class Tile {	
	final char letter;
	final int score;	
	@Override
	public int hashCode() {
		return Objects.hash(letter, score);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		return letter == other.letter && score == other.score;
	}

	private Tile(char l, int s) {
		this.letter=l;
		this.score = s;
		// TODO Auto-generated constructor stub
	}
	
	public char getLetter() {
		// TODO Auto-generated method stub
		return this.letter;
	}
	public int getScore() {
		// TODO Auto-generated method stub
		return this.score;
	}
	
	/*
	 * Class name: Bag (Inner class of Tile)
	
	member variable:
	letterCounts: integer array
	maxletterCounts: integer array
	tile_arr: Tile array
	bag_size: integer
	
	member function:
	Bag (Constructor): Initializes the letterCounts, maxletterCounts arrays and creates an array of 26 tiles with the 
						letters A through Z and their respective values.
	getRand: chooses a random tile and returns it. If the tile's count becomes 0, it won't be returned.
	getTileCount: returns the number of tiles of the specified letter left in the bag.
	isEmpty: returns true if the bag is empty, false otherwise.

	functional operation: represents a bag of tiles for the game.
	 */
	public static class Bag{
		
		   int letterCounts[];
		   int maxletterCounts[];
		   Tile tile_arr[];
		   int bag_size=98;
		   
		   /*
		   function name: Bag (Constructor)
		   input: None
		   output: None
		   functional operation: Initializes the letterCounts, maxletterCounts arrays and creates an array of 26 tiles with the letters A through Z and their respective values.
		   */
		   public Bag() { //constructor
			this.letterCounts = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
			this.maxletterCounts = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
			
			// Create an array of 26 tiles
			this.tile_arr=new Tile[26];
			// Initialize the tiles with the letters A through Z and their respective values
	        for (int i = 0; i < 26; i++) {
	            char letter = (char)('A' + i);
	            int value = 0;
	            switch (letter) {
	                case 'A': value = 1; break;
	                case 'B': value = 3; break;
	                case 'C': value = 3; break;
	                case 'D': value = 2; break;
	                case 'E': value = 1; break;
	                case 'F': value = 4; break;
	                case 'G': value = 2; break;
	                case 'H': value = 4; break;
	                case 'I': value = 1; break;
	                case 'J': value = 8; break;
	                case 'K': value = 5; break;
	                case 'L': value = 1; break;
	                case 'M': value = 3; break;
	                case 'N': value = 1; break;
	                case 'O': value = 1; break;
	                case 'P': value = 3; break;
	                case 'Q': value = 10; break;
	                case 'R': value = 1; break;
	                case 'S': value = 1; break;
	                case 'T': value = 1; break;
	                case 'U': value = 1; break;
	                case 'V': value = 4; break;
	                case 'W': value = 4; break;
	                case 'X': value = 8; break;
	                case 'Y': value = 4; break;
	                case 'Z': value = 10; break;
	            }
	            tile_arr[i] = new Tile(letter, value);
	        }
		}
		   
		/*
		 * function name:getRand
		 * input:none
		 * output:tile
		 * function operation:this function choose random tile and return it
		 * if all bag is empty return null
		 */
		public Tile getRand() {
			if(bag_size==0) {
				return null;
			}
			Random rand=new Random();
			do{//move on rand num until we find some one who not empty
				int rand_num=rand.nextInt(26);
				if(letterCounts[rand_num]!=0) {
					letterCounts[rand_num]-=1;
					bag_size--;//we put one tile out so the bag size minus 1
					return tile_arr[rand_num];
				}	
			}while(true);
			
		}
		
		/*
		 * function name:getTile
		 * input:char
		 * output:tile
		 * function operation:check if still there is this char tile in the bag and if there is return it
		 */	
		public Tile getTile(char c) {
			if(Character.isUpperCase(c)) {
				int tile_num=c-'A';//find the place of the char by move to ascii and down the A ascii for move from 0-25
				if(letterCounts[tile_num]>0) {
					letterCounts[tile_num]--;
					bag_size--;
					return tile_arr[tile_num];
				}	
			}
			//if it is zero we return null
			return null;
		}
		
		 /*
		  * function name: getQuantities
			input: None
			output: Array of integers
			functional operation: Returns a copy of the letterCounts array.
		  */
		public int[] getQuantities() {
			  return Arrays.copyOf(letterCounts, letterCounts.length);
			}
		

		/*
		 *  function name: put
			input: Tile object t
			output: None
			functional operation: Finds the place of the tile letter in the letterCounts array and increments its 
			count if it is not equal to the corresponding value in maxletterCounts. 
			Also increments the bag_size by 1 if the tile was successfully placed.
		 */
		public void put(Tile t) {
			int tile_num=t.letter-'A';
			//find the place of the char by move to ascii and down the A ascii for move from 0-25			
			if(letterCounts[tile_num]!=maxletterCounts[tile_num]) {
				letterCounts[tile_num]+=1;
				bag_size++;
			}//if it equal do nothing
			
		}
		
		/*
		 *  function name: size
			input: None
			output: Integer
			functional operation: Returns the current size of the bag (number of tiles in the bag).
		 */
		public int size() {
			return bag_size;
		}
		
		/*
		 * function name: getBag
			input: None
			output: Bag object
			functional operation: Returns a new instance of the Bag class.
		 */
		public static Bag getBag() {
			// TODO Auto-generated method stub
			return new Bag();
		}
	}
}
