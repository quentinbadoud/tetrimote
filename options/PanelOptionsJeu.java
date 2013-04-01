package options;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Tetris;

public class PanelOptionsJeu extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SliderPanel timeLand, timeAct, timeBeforeRepeat;

	private Tetris tetris;
	
	
	
	public PanelOptionsJeu(Tetris tetris){
		
		this.tetris = tetris;
		
		setSize(350, 300);
		
		setBorder(BorderFactory.createTitledBorder("Options jeu"));
		
		
		timeLand = new SliderPanel("Temps pour glisser", "ms", 0, 1000, 200, new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				PanelOptionsJeu.this.tetris.setTimeBeforeLanding(timeLand.getValue());
				timeLand.updateValue();
			}
			
		});
		
		timeAct = new SliderPanel("Temps entre deux deplacements", "ms", 10, 1000, 100, new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				PanelOptionsJeu.this.tetris.setTimeBetweenActions(timeAct.getValue());
				timeAct.updateValue();
			}
			
		});
		
		timeBeforeRepeat = new SliderPanel("Delai avant repetition", "ms", 0, 1000, 0, new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				PanelOptionsJeu.this.tetris.setDelayBeforeRepetition(timeBeforeRepeat.getValue());
				timeBeforeRepeat.updateValue();
			}
			
		});
		
		setLayout(new GridLayout(4, 1, 5, 5));
		
		add(timeLand);
		add(timeAct);
		add(timeBeforeRepeat);
		add(new JLabel("Version 3.0 Beta"));
		
	}
	
}
