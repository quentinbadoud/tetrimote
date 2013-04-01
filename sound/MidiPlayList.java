package sound;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import options.PanelOptionsSon;

/**
 * A MidiPlayList store MidiSounds. Such a playlist can be played
 * in a MidiPlayer.
 * @author Guéhenneux
 *
 */
public class MidiPlayList {

	public enum PlayMode {
		SHUFFLE,
		REPEATONE,
		NORMAL
	}

	private class FileExtFilter implements FileFilter{
		private String extensionWanted;
		
		public FileExtFilter(String extensionWanted){
			this.extensionWanted = extensionWanted;
		}
		
		public boolean accept(File pathname) {
			if(pathname.isDirectory()) return true;
			return pathname.getAbsolutePath().endsWith(extensionWanted);
		}
	}

	private LinkedList<MidiSound> sounds;
	
	private static FileFilter filter;

	private PlayMode playMode;
	private int index;
	
	private PanelOptionsSon presentation;

	/**
	 * this constructor is called when we want to get all the midi sounds
	 * in a zip file or a jar file
	 * @param zip
	 */
	public MidiPlayList(String location, String extension){
		this();
		ZipFile zip;
		try {
			zip = new ZipFile(location);
			Enumeration entries = zip.entries();
			ZipEntry entrie;

			while(entries.hasMoreElements()){
				entrie = (ZipEntry) entries.nextElement();
				if(entrie.getName().endsWith(extension))
					addSound(new MidiSound(entrie.getName()));
			}
		} catch (IOException e) {
			URL soundsDirURL = getClass().getResource("/sounds/");
			if(soundsDirURL == null)
				System.out.println("Dossier sounds introuvable.");
			else{
				try {
					File soundsDir = new File(soundsDirURL.toURI());
					filter = new FileExtFilter(extension);
					addSoundsFrom(soundsDir);
				} catch (URISyntaxException e1) {
					System.out.println("Impossible " +
							"de créer un fichier à partir de l'URL : " + soundsDirURL);
				}
			}
		}
		
	}
		
	private void addSoundsFrom(File f){
		if(f.isFile()) addSound(new MidiSound(f));
		else{
			File[] files = f.listFiles(filter);
			for(File fc : files)
				addSoundsFrom(fc);
		}
	}

	public MidiPlayList(){
		sounds = new LinkedList<MidiSound>();
		index = 0;
		playMode = PlayMode.NORMAL;
	}

	public void addSound(MidiSound sound){
		sounds.addLast(sound);
	}

	public void addSound(MidiSound sound, int index){
		sounds.add(index, sound);
	}

	public void removeSound(MidiSound sound){
		sounds.remove(sound);
	}

	public void remove(int index){
		if(index >= 0 && index < sounds.size())
			sounds.remove(index);
	}

	public PlayMode getPlayMode() {
		return playMode;
	}
	
	public void setPlayModeMan(PlayMode playMode) {
		if(presentation != null) presentation.setPlayMode(playMode);
		else setPlayModeAuto(playMode);
	}
	
	public void setPlayModeAuto(PlayMode playMode){
		this.playMode = playMode;
	}

	private int getRandomIndex(){
		return (int)(sounds.size() * Math.random());
	}

	public void reset(){
		index = 0;
	}

	public boolean isFinished(){
		return index >= sounds.size();
	}

	private MidiSound getSelectedSound(){
		return sounds.get(index);
	}

	public MidiSound getNextSound() {

		if(sounds.isEmpty()) return null;

		MidiSound sound;

		switch(playMode){
		case NORMAL:
			if(isFinished()) reset();
			sound = getSelectedSound();
			index++;
			break;
		case REPEATONE:
			if(isFinished()) reset();
			sound = getSelectedSound();
			break;
		case SHUFFLE:
			index = getRandomIndex();
			sound = getSelectedSound();
			break;

		default:
			sound = null;
		}
		
		if(presentation != null) presentation.setSoundIndex(index);
		
		return sound;
	}

	public boolean isEmpty(){
		return sounds.isEmpty();
	}
	
	public MidiSound getSound(int index){
		if(index >= 0 && index < sounds.size())
			return sounds.get(index);
		else return null;
	}
	
	public String toString(){
		String str = "";
		for(MidiSound ms : sounds)
			str += ms + " | ";
		return str;
	}

	public int getSoundCount() {
		return sounds.size();
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPresentation(PanelOptionsSon presentation) {
		this.presentation = presentation;
		presentation.setPlayMode(PlayMode.NORMAL);
		presentation.setSoundIndex(index);
	}

	
}
