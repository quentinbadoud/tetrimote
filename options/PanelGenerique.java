package options;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JPanel;

public class PanelGenerique extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int nbBuffer = 2;
	
	
	private int numBuffer;
	private LinkedList<String> generique;
	private int height;
	private int nextString;
	
	private BufferedImage[] bufferImages;
	private Graphics[] bufferGraphics;
	private int[] imagesY;
	
		
	private Font font = new Font("Showcard Gothic", 0, 25);
	private int fontHeight;
	private FontMetrics metrics;
	
	private Defileur prompter;
	private int nbImages;
	
	
	private void lireGenerique(String genericPath) throws IOException{
		
		Scanner s = new Scanner(PanelGenerique.class.getClassLoader().getResourceAsStream(genericPath));
		s.useDelimiter("\\n");
		while(s.hasNext())
			generique.add(s.next());
		
		s.close();
	
	}
	
	public PanelGenerique(){
		generique = new LinkedList<String>();
		try {
			lireGenerique("datas/Generique.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(700, 200);
		
		bufferImages = new BufferedImage[nbBuffer + 1];
		bufferGraphics = new Graphics[nbBuffer + 1];
		
		for(int i = 0 ; i < nbBuffer ; i++){
			bufferImages[i] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			bufferGraphics[i] = bufferImages[i].createGraphics();
			bufferGraphics[i].setFont(font);
		}
		
		bufferImages[nbBuffer] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		bufferGraphics[nbBuffer] = bufferImages[nbBuffer].createGraphics();
		
		metrics = bufferGraphics[0].getFontMetrics();
		fontHeight = metrics.getHeight();
		
	}
	
	public void lireImage(){
		bufferGraphics[numBuffer].setColor(Color.black);
		bufferGraphics[numBuffer].fillRect(0, 0, getWidth(), getHeight());
					
		
		bufferGraphics[numBuffer].setColor(Color.white);
		
		String line;
		int stringWidth;
		int y = (nextString+1) * fontHeight - nbImages * getHeight();
		
		
		
		while(y < getHeight()){
			if(nextString < generique.size())
				line = generique.get(nextString);
			else line = " ";
			

			stringWidth = metrics.stringWidth(line);
			bufferGraphics[numBuffer].drawString(
					line,
					(getWidth() - stringWidth) / 2,
					y);
			
			
			y += fontHeight;
			nextString = (nextString + 1) % (generique.size() + 5);
			
		}
		
		// potentiellement cette ligne sera partiellement visible
		// (coupee en bas de l'ecran, il faut cependant dessiner
		// ce qui est visible, le reste sera dessine sur l'image suivante
		
		if(nextString < generique.size())
			line = generique.get(nextString);
		else line = " ";
	
		stringWidth = metrics.stringWidth(line);
		bufferGraphics[numBuffer].drawString(
				line,
				(getWidth() - stringWidth) / 2,
				y);

		numBuffer = (numBuffer+1)%nbBuffer;
		nbImages++;
	}
	
	public void paint(Graphics g){
		
			
		/*
		 * La technique utilisée pour le défilement du générique est de calculer une image tous
		 * getHeight() pixels pour recouvrir tout le Panel pendant le défilement il nous faut
		 * précalculer au moins deux images
		 */
			
		
		for(int i = 0 ; i < nbBuffer ; i++)
			bufferGraphics[nbBuffer].drawImage(bufferImages[i], 0, imagesY[i], null);

		g.drawImage(bufferImages[nbBuffer], 0, 0, null);
		
	}
	
	
	
	private class Defileur extends Thread{
		
		private boolean alive;
		
		public Defileur(){
			alive = true;
			height = 0;
		}
		
		public void run(){
			while(alive){
				if(height % getHeight() == 0)
					lireImage();
				try {
					sleep(15);
				} catch (InterruptedException e) {
					// TODO Bloc catch auto-généré
					e.printStackTrace();
				}
				
				repaint();
				int h;
				for(int i = 0 ; i < nbBuffer ; i++){
					h = imagesY[i];
					h--;
					if(h <= -getHeight())
						h += nbBuffer * getHeight();
					imagesY[i] = h;
				}
				height++;
			}
		}
		
		public void kill(){
			alive = false;
			
		}
		
	}

	public void stop(){
		if(prompter != null) prompter.kill();
	}

	
	public void start() {
		stop();
		
		numBuffer = 0;
		nextString = 0;
		nbImages = 0;
		
		imagesY = new int[nbBuffer];
		for(int i = 0 ; i < nbBuffer ; i++){
			imagesY[i] = getHeight() - i * getHeight();
			bufferGraphics[i].setColor(Color.black);
			bufferGraphics[i].fillRect(0, 0, getWidth(), getHeight());
		}
		
		prompter = new Defileur();
		prompter.start();
	}
	
}
