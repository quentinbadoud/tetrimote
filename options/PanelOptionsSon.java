package options;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sound.MidiPlayList;
import sound.MidiPlayer;
import sound.MidiPlayList.PlayMode;

public class PanelOptionsSon extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PanelReglagesSon reglages;
	private JList pbs;
	
	private MidiPlayer player;
	private MidiPlayList playbacks;
	
	@SuppressWarnings("unchecked")
	public PanelOptionsSon(MidiPlayer player, MidiPlayList playbacks){
	
		
		this.player = player;
		this.playbacks = playbacks;
		
		setSize(350, 300);
		
		setBorder(BorderFactory.createTitledBorder("Options son"));
		
		setLayout(new GridLayout(1, 2, 5, 5));
		
		int nbPlaybacks = playbacks.getSoundCount();
		
		Vector items = new Vector();
		for(int i = 0 ; i < nbPlaybacks ; i++)
			items.add(playbacks.getSound(i));
		
		pbs = new JList(items);
		
		pbs.addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent e) {
				PanelOptionsSon.this.playbacks.setIndex(pbs.getSelectedIndex());
				PanelOptionsSon.this.player.play(
						PanelOptionsSon.this.playbacks.getSound(pbs.getSelectedIndex()));
			}
			
		});
		reglages = new PanelReglagesSon(player, playbacks);
		
		add(reglages);
		add(new JScrollPane(pbs));
		
		playbacks.setPresentation(this);
		setSoundIndex(0);
	}

	public void setSoundIndex(int index) {
		pbs.setSelectedIndex(index);
	}

	public void setPlayMode(PlayMode playMode) {
		reglages.setPlayMode(playMode);
	}
	
	
}
