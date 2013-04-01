package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PValuePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel digitsPanel;
	
	private int nextDigitLocationX;
	
	public PValuePanel(String title){
		
		setBackground(Color.black);
		setBorder(BorderFactory.createTitledBorder(title));
		setLayout(new BorderLayout());
		digitsPanel = new JPanel();
		digitsPanel.setLayout(null);
		digitsPanel.setBackground(Color.black);
		add(digitsPanel);
		
		nextDigitLocationX = PDigit.SQUARE_WIDTH;
		
	}
	
	public void addDigit(Component digit){
		digit.setLocation(nextDigitLocationX, 0);
		digitsPanel.add(digit);
		nextDigitLocationX += digit.getWidth() + PDigit.SQUARE_WIDTH;
	}
	
}
