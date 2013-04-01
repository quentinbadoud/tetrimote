package options;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import sound.MidiPlayList;
import sound.MidiPlayer;

import main.Tetris;

public class FrameOptions extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private PanelGenerique generique;
	private PanelOptionsSon optionsSon;
	private PanelOptionsJeu optionsJeu;
	
	private Tetris tetris;
	
	public FrameOptions(Tetris tetris, MidiPlayer player, MidiPlayList playbacks){
		
		super("Options tetris");
		
		this.tetris = tetris;
		
		setSize(700 + 6, 500 + 33);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((screenWidth - getWidth()) / 2 , (screenHeight - getHeight()) / 2);
		
		setLayout(null);
		
		generique = new PanelGenerique();
		generique.setLocation(0, 300);
		add(generique);
		
		optionsSon = new PanelOptionsSon(player, playbacks);
		optionsSon.setLocation(350, 0);
		add(optionsSon);
		
		optionsJeu = new PanelOptionsJeu(tetris);
		optionsJeu.setLocation(0, 0);
		add(optionsJeu);
		
		addWindowListener(new MyWindowsListener());
		setResizable(false);
	}
	
	
	public void startGenerique(){
		generique.start();
	}
	
	private class MyWindowsListener extends WindowAdapter{


		public void windowClosing(WindowEvent e) {
			tetris.pause();
			generique.stop();
		}

		
		
	}

	public void stopGeneric() {
		generique.stop();
	}
	
	
	
}
