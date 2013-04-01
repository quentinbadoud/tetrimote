package main;

import java.awt.Color;

public class TetrisGrid {

	private int width, height;
	
	private int[][] grid;
	
	public TetrisGrid(int width, int height) {
		this.width = width;
		this.height = height;
		
		grid = new int[width][height];
		reset();
	}

	public void reset(){
		for(int i = 0 ; i < width ; i++)
			for(int j = 0 ; j < height ; j++)
				grid[i][j] = -1;
	}
	
	public Color getColorAt(int i, int j){
		int v = grid[i][j];
		
		// carré vide
		if(v == -1) return Color.black;
		
		// pendant les animations, (destruction de lignes, défaite, ...)
		// un carré peut être gris
		if(v == 7){
			int alpha = (int)(256*Math.random());
			return new Color(alpha, alpha, alpha);
		}
		
		// sinon on retourne la couleur du carré
		else return TetrisBlock.getColor(v);
	}
	
	public int getValueAt(int i, int j){
		return grid[i][j];
	}
	
	public boolean isFree(int i, int j){
		return grid[i][j] == -1;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean isFull(int i, int j){
		return grid[i][j] != -1;
	}
	
	public void fill(int i, int j, int c) {
		grid[i][j] = c;
	}

	public void empty(int i, int j) {
		grid[i][j] = -1;
	}
	
	public boolean isLineFull(int i) {
		for(int j = 0 ; j < width ; j++)
			if(isFree(j, i)) return false;
		return true;
	}

	public void downLine(int j, int nbDestroyedLines) {
		for(int i = 0 ; i < width ; i++){
			grid[i][j+nbDestroyedLines] = grid[i][j];
			empty(i, j);
		}
	}
	
	public String toString(){
		String result = "";
		for(int j = 0 ; j < height ; j++){
			for(int i = 0 ; i < width ; i++){
				if(isFull(i, j)) result += '#';
				else result += ' ';
			}
			result += '\n';
		}
		return result + "_____________________________________";
	}
	
	
}
