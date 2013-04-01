package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PDigit extends JPanel{

	private static final long serialVersionUID = 1L;
	private static final Color ON = new Color(255, 128, 0), OFF = Color.black, IN = new Color(50, 25, 0);
	private static final int WIDTH = 3, HEIGHT = 5;
	public static final int SQUARE_WIDTH = 5, SQUARE_HEIGHT = 5;
	
	public static final Dimension dimension = new Dimension(WIDTH * SQUARE_WIDTH, HEIGHT * SQUARE_HEIGHT);
		
	private BufferedImage bufferImage;
	private Graphics bufferGraphics;
	
	private boolean[] segments;
	private boolean active;
	
	
	
	public PDigit(){
		segments = new boolean[WIDTH * HEIGHT];
		bufferImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
		bufferGraphics = bufferImage.createGraphics();
		setSize(dimension);
	}
	
	public void paint(Graphics g){
		
		for(int i = 0 ; i < WIDTH * HEIGHT ; i++){
			
			if(active){
				if(segments[i]) bufferGraphics.setColor(ON);
				else bufferGraphics.setColor(OFF);
			}
			else
				bufferGraphics.setColor(IN);
			
			bufferGraphics.fill3DRect(
					SQUARE_WIDTH * (i%WIDTH),
					SQUARE_HEIGHT * (i/WIDTH),
					SQUARE_WIDTH,
					SQUARE_HEIGHT,
					true);
			
		}
		
		g.drawImage(bufferImage, 0, 0, null);
		
	}

	public void setSegmentsOn(boolean[] segments) {
		this.segments = segments;
		repaint();
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
