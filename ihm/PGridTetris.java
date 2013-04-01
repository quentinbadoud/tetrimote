package ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.Tetris;
import main.TetrisBlock;
import main.TetrisGrid;

public class PGridTetris extends JPanel{

	private static final long serialVersionUID = 1L;

	public static final int squareWidth = 18;
	public static final int squareHeight = 18;
	

	
	private BufferedImage bufferImage;
	private Graphics bufferGraphics;
	private BufferedImage gameImage;
	private Graphics gameGraphics;
	private BufferedImage backgroundImage;
	private Graphics backgroundGraphics;
	

	private enum PaintingMode { ONLY_FALLING_BLOCK, ALL_GRID, ONLY_ONE_SQUARE }
	
	
	private PaintingMode mode;
	private int bx, by;

	private Tetris tetris;
	
	
	public PGridTetris(Tetris tetris){
		this.tetris = tetris;
		
		bufferImage = new BufferedImage(
				Tetris.width * squareWidth + 2,
				Tetris.height * squareHeight + 2,
				BufferedImage.TYPE_INT_RGB);
		
		bufferGraphics = bufferImage.createGraphics();
		
		gameImage = new BufferedImage(
				Tetris.width * squareWidth + 2,
				Tetris.height * squareHeight + 2,
				BufferedImage.TYPE_INT_RGB);
		
		gameGraphics = gameImage.createGraphics();
		
		backgroundImage = new BufferedImage(
				Tetris.width * squareWidth + 2,
				Tetris.height * squareHeight + 2,
				BufferedImage.TYPE_INT_RGB);
		
		backgroundGraphics = backgroundImage.createGraphics();
		
		backgroundGraphics.setColor(Color.black);
		backgroundGraphics.fillRect(0, 0,
				Tetris.width * squareWidth + 2,
				Tetris.height * squareHeight + 2);
		
		backgroundGraphics.setColor(new Color(150, 75, 0));
		backgroundGraphics.drawRect(0, 0,
				Tetris.width * squareWidth + 1,
				Tetris.height * squareHeight + 1);

		clearGame();
		
		mode = PaintingMode.ALL_GRID;
	}

	
	public void paintSquare(int x, int y){
		bx = x;
		by = y;
		mode = PaintingMode.ONLY_ONE_SQUARE;
		repaint();
	}

	
	public void paintAll(){
		mode = PaintingMode.ALL_GRID;
		repaint();
	}
	
	public void paintCurrentBlock(){
		mode = PaintingMode.ONLY_FALLING_BLOCK;
		repaint();
	}
	
	
	public void clearGame(){
		gameGraphics.drawImage(backgroundImage, 0, 0, null);
	}
	
	public void paint(Graphics g) {
		switch (mode) {

		case ONLY_FALLING_BLOCK:

			TetrisBlock currentBlock = tetris.getCurrentBlock();
			bufferGraphics.drawImage(gameImage, 0, 0, null);
			int x = currentBlock.getX();
			int y = currentBlock.getY();
			bufferGraphics.setColor(currentBlock.getColor());
			for(int i = 0 ; i < 4 ; i++)
				for(int j = 0 ; j < 4 ; j++)
					if(currentBlock.isSquare(i, j))
						bufferGraphics.fill3DRect(
								1 + squareWidth*(x+i),
								1 + squareHeight*(y+j),
								squareWidth, squareHeight, true);
		
			g.drawImage(bufferImage, 0, 0, null);
			break;

		case ALL_GRID:

			TetrisGrid grid = tetris.getGrid();
			
			bufferGraphics.drawImage(backgroundImage, 0, 0, null);
			for(int i = 0 ; i < Tetris.width ; i++)
				for(int j = 0 ; j < Tetris.height ; j++){
					if(grid.isFull(i, j)){
						bufferGraphics.setColor(grid.getColorAt(i, j));
						bufferGraphics.fill3DRect(
								1 + squareWidth*i,
								1 + squareHeight*j,
								squareWidth, squareHeight, true);
					}
				}

			gameGraphics.drawImage(bufferImage, 0, 0, null);
			g.drawImage(bufferImage, 0, 0, null);
			break;

		case ONLY_ONE_SQUARE:
			TetrisGrid grid2 = tetris.getGrid();
			bufferGraphics.setColor(grid2.getColorAt(bx, by));
			bufferGraphics.fill3DRect(
					1 + squareWidth*bx,
					1 + squareHeight*by,
					squareWidth, squareHeight, true);
			g.drawImage(bufferImage, 0, 0, null);
			break;

		
		default:
			break;
		}
		
		
	}
	

}
