package ihm;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import sound.MidiPlayList;
import sound.MidiPlayer;

import main.CValuePanel;
import main.Tetris;

public class PPartie extends JPanel{

	private static final long serialVersionUID = 1L;

	private CValuePanel score, nbLines, level, nbLinesInThisLevel;
	private ButtonOption buttonOption;
	
	public PPartie(MidiPlayer player, MidiPlayList playBacks){
		setLayout(new GridLayout(5, 1, 5, 5));
		setBackground(Color.black);
		score = new CValuePanel("Score");
		level = new CValuePanel("Level");
		nbLinesInThisLevel = new CValuePanel("Lines");
		nbLines = new CValuePanel("Total");
		buttonOption = new ButtonOption(player, playBacks);
		
		add(score.getPresentation());
		add(level.getPresentation());
		add(nbLinesInThisLevel.getPresentation());
		add(nbLines.getPresentation());
		add(buttonOption);
		
	}

	public void setTotal(int i) {
		nbLines.setValue(i);
	}

	public void setLines(int i) {
		nbLinesInThisLevel.setValue(i);
	}

	public void setScore(int i) {
		score.setValue(i);
	}

	public void setLevel(int i) {
		level.setValue(i);
	}

	public void setTetris(Tetris tetris) {
		buttonOption.setTetris(tetris);
	}

	public void hideOptionFrame() {
		buttonOption.hideOptionFrame();
	}
	
}
