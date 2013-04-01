package sound;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

/**
 * 
 * MidiSound is the memory image of a Midi file. It can be played with
 * the MidiPlayer and stored in a MidiPlayList.
 * 
 * @author Guéhenneux
 *
 */
public class MidiSound {

	private Sequence sequence;
	private String name;
	
	/**
	 * It instanciates a MidiSound building the midi sequence.
	 * @param path the path of the midi file
	 */
	public MidiSound(String path){
		try {
			name = getName(path);
			sequence = MidiSystem.getSequence(getClass().getClassLoader().getResourceAsStream(path));
		} catch (InvalidMidiDataException e) {
			System.out.println("Le fichier " + path + " n'est pas un fichier midi valide.");
		} catch (IOException e) {
			System.out.println("Impossible d'acceder au fichier " + path + ".");
		}
	}

	private static String getName(String path){
		return removeExtension(path);
	}
	
	private static String removeExtension(String path){
		int indexExtension = path.lastIndexOf('.');
		if(indexExtension == -1) return path;
		return path.substring(0, indexExtension);
	}
	
	/**
	 * It Instanciates a MidiSound from a file
	 * @param midiFile
	 */
	public MidiSound(File midiFile) {
		try {
			name = getName(midiFile.getName());
			sequence = MidiSystem.getSequence(midiFile);
		} catch (InvalidMidiDataException e) {
			System.out.println("Le fichier " + midiFile.getAbsolutePath() + " n'est pas un fichier midi valide.");
		} catch (IOException e) {
			System.out.println("Impossible d'acceder au fichier " + midiFile.getAbsolutePath() + ".");
		}
	}

	/**
	 *
	 * @return the sequence built with the file given in the constructor.
	 * It can be null if the file was corrupted or not found.
	 */
	public Sequence getSequence() {
		return sequence;
	}
	
	public String toString(){
		return name;
	}
	
	
}
