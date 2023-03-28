package test;

import java.util.Arrays;
import java.util.Objects;

/*
 * Class Name: Word
 * 
 * Members:
 * tiles: Tile[]
 * row: int
 * col: int
 * vertical: boolean
 * 
 * Methods:
 * Word(Tile[] ts, int i, int j, boolean b)
 * getCol(): int
 * getRow(): int
 * getTiles(): Tile[]
 * getVertical(): boolean
 * hashCode(): int
 * equals(Object obj): boolean
 * 
 * functional operation:
 * Represents a Word with its tiles, position, and orientation.
 */
public class Word {
    Tile tiles[];
    int row,col;//row is the first iterator col is the second(row,col)
    boolean vertical;
   
    /*
     * Constructor to initialize the members of the Word class.
     * 
     * Input:
     * ts: Tile[] - the tiles that make up the word.
     * i: int - the row position of the word.
     * j: int - the column position of the word.
     * b: boolean - the orientation of the word (vertical or horizontal).
     * 
     * Output: N/A
     * 
     * functional operation: Initializes the members of the Word class.
     */
    public Word(Tile[] ts, int i, int j, boolean b) {
        this.tiles=ts;
        this.row=i;
        this.col=j;
        this.vertical=b;
    }

    public int getCol() {
    return col;
    }
    public int getRow() {
        return row;
    }
    public Tile[] getTiles() {
        return tiles;
    }
    public boolean getVertical() {
        return vertical;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tiles);
		result = prime * result + Objects.hash(col, row, vertical);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		return col == other.col && row == other.row && Arrays.equals(tiles, other.tiles) && vertical == other.vertical;
	}
	
}
