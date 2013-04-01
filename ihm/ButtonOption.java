package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import main.Tetris;

import options.FrameOptions;
import sound.MidiPlayList;
import sound.MidiPlayer;

public class ButtonOption extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean pressed;
	
	private Shape box;
	
	private Font font = new Font("Mistral", 0, 30);
	private FontMetrics metrics;

	private FrameOptions frame;
	
	private Tetris tetris;

	private MidiPlayer player;

	private MidiPlayList playbacks;
	
	public ButtonOption(MidiPlayer player, MidiPlayList playbacks){
		this.player = player;
		this.playbacks = playbacks;
		addMouseListener(new MyMouseListener());
		pressed = false;
	}

	public boolean contains(int x, int y){
		return box != null && box.contains(x, y);
	}
	
	public void paint(Graphics g){
		
		int totalWidth = getWidth(), totalHeight = getHeight();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, totalWidth, totalHeight);
		
		int width = totalWidth, height = totalHeight;
		
		if(width > 80){
			height = height * 80 / width;
			width = 80;
		}
		
		if(height > 40){
			width = width * 40 / height;
			height = 40;
		}
		
		int squareWidth = width / 4;
		int buttonWidth = 4 * squareWidth;
		
		int squareHeight = height / 2;
		int buttonHeight = 2 * squareHeight;
		
		int strokeWidth = (totalWidth - buttonWidth) / 2;
		int strokeHeight = (totalHeight - buttonHeight) / 2;
		
		box = new Rectangle(strokeWidth, strokeHeight, buttonWidth, buttonHeight);
		
		g.setColor(new Color(150, 75, 0));
		
		for(int i = 0 ; i < 4 ; i++)
			for(int j = 0 ; j < 2 ; j++)
				g.fill3DRect(
						strokeWidth + i * squareWidth,
						strokeHeight + j * squareHeight,
						squareWidth, squareHeight, !pressed);
		
		
		g.setColor(new Color(50, 25, 0));
		
		g.setFont(font);
		metrics = g.getFontMetrics();
		int textWidth = metrics.stringWidth("Options");
		int textHeight = metrics.getHeight();
		
		g.drawString("Options",
				strokeWidth + (buttonWidth - textWidth) / 2,
				strokeHeight + (buttonHeight + textHeight) / 2 - 5);

		
		
	}
	
	private class MyMouseListener extends MouseAdapter{

		public void mousePressed(MouseEvent e) {
			pressed = true;
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			if(!contains(e.getX(), e.getY())) return;
			pressed = false;
			repaint();
			if(!frame.isVisible()){
				if(!tetris.isPaused()) tetris.pause();
				frame.setVisible(true);
				frame.startGenerique();

			}	
		}	
		
		public void mouseExited(MouseEvent e) {
			pressed = false;
			repaint();
		}
		
	}

	public void setTetris(Tetris tetris) {
		this.tetris = tetris;
		frame = new FrameOptions(tetris, player, playbacks);
	}

	public void hideOptionFrame() {
		if(frame != null){
			frame.setVisible(false);
			frame.stopGeneric();
		}
	}
	
	
}
