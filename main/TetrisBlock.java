package main;


import java.awt.Color;

import main.Tetris.Position;

public class TetrisBlock {

	private boolean[][][] rotations;
	private int rotationIndex;
	private int x, y;
	private int index;
	
	private static Lander lander;
	
	private boolean landing;
	private boolean landed;
	
	private Tetris tetris;
	
	public Tetris getTetris() {
		return tetris;
	}
	
	public Color getColor(){
		return allBlocksColor[index];
	}
	
	public static Color getColor(int index){
		return allBlocksColor[index];
	}
	
	public int getNumber(){
		return index;
	}
	
	private static Color[] allBlocksColor = {
			Color.cyan,
			Color.yellow,
			Color.orange,
			Color.blue,
			Color.magenta,
			Color.green,
			Color.red
	};
	
	
	private static final String[] allBlocks = {
		"    " +
		"####" +
		"    " +
		"    ",
		
		"    " +
		" ## " +
		" ## " +
		"    ",
		
		" #  " +
		" #  " +
		" ## " +
		"    ",
		
		"  # " +
		"  # " +
		" ## " +
		"    ",
		
		"  # " +
		" ## " +
		"  # " +
		"    ",
		
		" #  " +
		" ## " +
		"  # " +
		"    ",
		
		"  # " +
		" ## " +
		" #  " +
		"    "
		
	};
	
	
	private void convertStringToBlock(String str){
		for(int i = 0 ; i < 16 ; i++)
			rotations[0][i/4][i%4] = str.charAt(i) == '#';
		
		boolean[][] tmp;
		
		for(int i = 0 ; i < 3 ; i++){
			tmp = new boolean[4][4];
			for(int j = 0 ; j < 4 ; j++)
				for(int k = 0 ; k < 4 ; k++)
					tmp[3-k][j] = rotations[i][j][k];
			for(int j = 0 ; j < 4 ; j++)
				for(int k = 0 ; k < 4 ; k++)
					rotations[i+1][j][k] = tmp[j][k];
		}
	}
	
	private void hasMoved(){
		if(landing)
			tetris.mustIgnoreNextLanding = true;
		landing = false;
		tetris.currentBlockHasMoved();
	}
	
	public boolean canRotate() {
		if(landed) return false;
		int saveRotation = rotationIndex;
		rotationIndex = (rotationIndex + 1) % 4;
		
		Position result = tetris.whereIAm(this, x, y);
		
		int decalage = 0;
		
		if(result == Position.OUT_LEFT)
			while(result == Position.OUT_LEFT)
				result = tetris.whereIAm(this, x + ++decalage, y);
		else if(result == Position.OUT_RIGHT)
			while(result == Position.OUT_RIGHT)
				result = tetris.whereIAm(this, x + --decalage, y);
		else if(result == Position.OUT_NORTH)
			while(result == Position.OUT_NORTH)
				result = tetris.whereIAm(this, x, y + ++decalage);
		
		rotationIndex = saveRotation;
		
		return result == Position.IN;
	}
	
	public void rotate(){
		rotationIndex = (rotationIndex+1)%4;
		
		Position result = tetris.whereIAm(this, x, y);
		
		int decalage = 0;
		
		if(result == Position.OUT_LEFT){
			while(result == Position.OUT_LEFT)
				result = tetris.whereIAm(this, x + ++decalage, y);
			x += decalage;
		}
		else if(result == Position.OUT_RIGHT){
			while(result == Position.OUT_RIGHT)
				result = tetris.whereIAm(this, x + --decalage, y);
			x += decalage;
		}
		else if(result == Position.OUT_NORTH){
			while(result == Position.OUT_NORTH)
				result = tetris.whereIAm(this, x, y + ++decalage);
			y += decalage;
		}
		
		
		
		hasMoved();
	}
	
	public boolean canMoveLeft(){
		return !landed && tetris.whereIAm(this, x-1, y) == Position.IN;
	}
	
	
	
	public void moveLeft(){
		x--;
		hasMoved();
	}
	
	public boolean canMoveRight(){
		return !landed && tetris.whereIAm(this, x+1, y) == Position.IN;
	}
	
	public void moveRight(){
		x++;
		hasMoved();
	}
	
	public boolean canMoveUp(){
		return !landed && tetris.whereIAm(this, x, y-1) == Position.IN;
	}
	
	public void moveUp(){
		y--;
		hasMoved();
	}
	
	public boolean canMoveDown(){
		return !landed && tetris.whereIAm(this, x, y+1) == Position.IN;
	}
	
	public void moveDown(){
		y++;
		hasMoved();
	}
	
	private int getHighestSquareHeight(){
		int highest = 3;
		
		for(int j = 3 ; j >= 0 ; j--)
			for(int i = 0 ; i < 4 ; i++)
				if(isSquare(i, j)) highest = j;
		
		return highest;
	}
	
	public TetrisBlock(Tetris tetris) {
		this.tetris = tetris;
		rotations = new boolean[4][4][4];
		index = (int)(7*Math.random());
		convertStringToBlock(allBlocks[index]);
		rotationIndex = (int)(4*Math.random());
		y = -getHighestSquareHeight();
		x = Tetris.width / 2 - 2;
		landing = false;
		landed = false;
	}

	public boolean isSquare(int i, int j){
		return rotations[rotationIndex][i][j];
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	public void prepareToLand() {
		landing = true;
		if(lander != null) lander.kill();
		lander = new Lander(tetris.getTimeBeforeLanding(), this, tetris.getActionReceiver());
		lander.start();
	}


	public boolean isLanding() {
		return landing;
	}
	

	public static Lander getLander() {
		return lander;
	}
	
	public void setLanding(boolean b) {
		landing = b;
	}

}
