package sound;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import options.PanelReglagesSon;

/**
 * 
 * MidiPlayer is an high-level class for MIDI playing.
 * It seems that the devices allows us to play only one midi file in the same time.
 * 
 * MidiPlayer must be used with MidiSound and MidiPlayList.
 * 
 * @author Guéhenneux
 *
 */
public class MidiPlayer {

	private Sequencer sequencer;
	
	private MidiPlayList playList;
	
	@SuppressWarnings("unused")
	private PanelReglagesSon presentation;
	
	/**
	 * It just instanciates a MidiPlayer with the default sequencer.
	 *
	 */
	public MidiPlayer(){
		try {
			sequencer = MidiSystem.getSequencer();			
		} catch (MidiUnavailableException e) {
			System.out.println("Pas de sequenceur disponible pour jouer des sons midi.");
		}		
		
		try {
			sequencer.open();
		} catch (MidiUnavailableException e1) {
			System.out.println("Impossible d'ouvrir le sequenceur.");
		}
		
		sequencer.addMetaEventListener(new MetaEventListener(){
			public void meta(MetaMessage e) {
				/*
				 * if the meta event is an 'end of track' event
				 */
				if(e.getType() == 47){
					playNextSound();
				}
			}
		});
		
		
	}
	
	public void setPresentation(PanelReglagesSon presentation){
		this.presentation = presentation;
		presentation.setMute(false);
	}
	

	public void playNextSound(){
		if(playList != null && !playList.isEmpty())
			play(playList.getNextSound());
	}
	
	public void play(MidiSound sound){
		if(sound == null) return;
		
		sequencer.stop();
		try {
			sequencer.setSequence(sound.getSequence());
		} catch (InvalidMidiDataException e) {
			System.out.println("La sequence midi est corrompue, impossible de la jouer.");
		}
		
		sequencer.start();
	}
	



	public MidiPlayList getPlayList() {
		return playList;
	}

	public void setMute(boolean mute){
		for(int i = 0 ; i < 16 ; i++)
			sequencer.setTrackMute(i, mute);
	}

	public void setPlayList(MidiPlayList playList) {
		this.playList = playList;
	}
	
	
}
