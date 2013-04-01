package main;

import ihm.PValuePanel;

import java.util.LinkedList;

public class CValuePanel {

	private LinkedList<CDigit> digits;
	private PValuePanel presentation;

	private static final int NB_DIGITS = 6;
	
	public CValuePanel(String title){
		digits = new LinkedList<CDigit>();
		presentation = new PValuePanel(title);
		for(int i = 0 ; i < NB_DIGITS ; i++)
			new CDigit(this);
	}
	
	public void setValue(int value){
		boolean firstDigit = true;
		for(CDigit digit : digits){
			digit.setActive(firstDigit || value != 0);
			digit.setDigit(value%10);
			value = value / 10;
			firstDigit = false;
		}
	}
	
	public CDigit getDigit(int i){
		return digits.get(i);
	}
	
	public void addDigit(CDigit digit) {
		digits.addFirst(digit);
		presentation.addDigit(digit.getPresentation());
	}

	public PValuePanel getPresentation() {
		return presentation;
	}

}
