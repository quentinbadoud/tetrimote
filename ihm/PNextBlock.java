package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.Tetris;
import main.TetrisBlock;

public class PNextBlock extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int height = 4;
	
	private Tetris tetris;
	
	public static final Dimension dimension = new Dimension(
			Tetris.width * PGridTetris.squareWidth + 2,
			height * PGridTetris.squareHeight );
	
	private BufferedImage bufferImage;
	private Graphics bufferGraphics;
	
	public PNextBlock(Tetris tetris){
		this.tetris = tetris;
		setSize(dimension);
		bufferImage = new BufferedImage(
				dimension.width,
				dimension.height,
				BufferedImage.TYPE_INT_RGB);
		bufferGraphics = bufferImage.createGraphics();
	}
	
	public void paint(Graphics g){
	
		bufferGraphics.setColor(Color.black);
		bufferGraphics.fillRect(0, 0, getWidth(), getHeight());
		
		TetrisBlock nextBlock = tetris.getNextBlock();
		
		bufferGraphics.setColor(new Color(140, 70, 0));

		for(int i = 0 ; i < 4 ; i++)
			for(int j = 0 ; j < 4 ; j++)
				if(nextBlock.isSquare(i, j))
					bufferGraphics.fill3DRect(
							PGridTetris.squareWidth*(i+nextBlock.getX()),
							PGridTetris.squareHeight*j,
							PGridTetris.squareWidth,
							PGridTetris.squareHeight, true);

		
		g.drawImage(bufferImage, 0, 0, null);
		
	}
	
	
}
