package options;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import sound.MidiPlayList;
import sound.MidiPlayer;
import sound.MidiPlayList.PlayMode;

public class PanelReglagesSon extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ButtonGroup playModes;
	
	private JToggleButton modeRandom, modeNormal, modeRepeat;
	
	private MidiPlayer player;
	private MidiPlayList playbacks;
	private JCheckBox mute;
	
	public PanelReglagesSon(MidiPlayer player, MidiPlayList playbacks) {
		this.player = player;
		this.playbacks = playbacks;
		
		setBorder(BorderFactory.createTitledBorder("Reglages"));
		
		setLayout(new GridLayout(7, 1, 5, 5));
		
		playModes = new ButtonGroup();
		
		modeNormal = new JToggleButton("Lecture normale");
		modeRandom = new JToggleButton("Lecture aléatoire");
		modeRepeat = new JToggleButton("Lecture répétée");
		mute = new JCheckBox("Silence");
		
		modeNormal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				PanelReglagesSon.this.playbacks.setPlayModeAuto(PlayMode.NORMAL);
			}
		});
		playModes.add(modeNormal);
		add(modeNormal);
		
		modeRandom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				PanelReglagesSon.this.playbacks.setPlayModeAuto(PlayMode.SHUFFLE);
			}
		});
		playModes.add(modeRandom);
		add(modeRandom);
		
		modeRepeat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				PanelReglagesSon.this.playbacks.setPlayModeAuto(PlayMode.REPEATONE);
			}
		});
		playModes.add(modeRepeat);
		add(modeRepeat);
		
		mute.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				PanelReglagesSon.this.player.setMute(mute.isSelected());
			}
			
		});
		
		add(mute);
		
		player.setPresentation(this);

	}

	public void setMute(boolean b) {
		if(mute.isSelected() != b) mute.doClick();
	}

	public void setPlayMode(PlayMode playMode) {
		switch(playMode){
		case NORMAL:
			if(!modeNormal.isSelected()) modeNormal.doClick();
			break;
		case SHUFFLE:
			if(!modeRandom.isSelected()) modeRandom.doClick();
			break;
		case REPEATONE:
			if(!modeRepeat.isSelected()) modeRepeat.doClick();
			break;
		default:
		}
	}

	
	
	
}
