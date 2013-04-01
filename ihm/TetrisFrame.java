package ihm;

import java.awt.Toolkit;

import javax.swing.JFrame;

import main.Tetris;

public class TetrisFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TetrisFrame(){
		super("Tetris 3.0 Beta by JoJoLeMaRiOle");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(2*(PGridTetris.squareWidth*Tetris.width) + 8, PGridTetris.squareHeight*(Tetris.height+PNextBlock.height)+36);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		setLocation((screenWidth - getWidth()) / 2 , (screenHeight - getHeight()) / 2);
	}
	
}
