package options;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;


public class SliderPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel value;
	private String title, unit;
	private JSlider slider;
	
	
	public SliderPanel(String title, String unit, int min, int max, int init,
			ChangeListener cl) {
		
		this.title = title;
		this.unit = unit;
		
		setLayout(new GridLayout(2, 1, 5, 5));
		
		slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
		slider.addChangeListener(cl);
		
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
	
		value = new JLabel();
		updateValue();
		
		add(value);
		add(slider);
	}
	
	public void updateValue(){
		
		value.setText(title + " = " + slider.getValue() + " " + unit);
		
	}

	public int getValue() {
		return slider.getValue();
	}
	
	
	
}
