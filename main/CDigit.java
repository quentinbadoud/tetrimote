package main;

import ihm.PDigit;

public class CDigit {

	private static final boolean
		T = true, F = false;
	
	private static final boolean[][] DIGITS =
	{	{ T, T, T, T, F, T, T, F, T, T, F, T, T, T, T },
		{ F, T, F, T, T, F, F, T, F, F, T, F, T, T, T },
		{ T, T, T, F, F, T, T, T, T, T, F, F, T, T, T },
		{ T, T, T, F, F, T, T, T, T, F, F, T, T, T, T },
		{ T, F, F, T, F, T, T, T, T, F, F, T, F, F, T },
		{ T, T, T, T, F, F, T, T, T, F, F, T, T, T, T },
		{ T, T, T, T, F, F, T, T, T, T, F, T, T, T, T },
		{ T, T, T, T, F, T, F, F, T, F, F, T, F, F, T },
		{ T, T, T, T, F, T, T, T, T, T, F, T, T, T, T },
		{ T, T, T, T, F, T, T, T, T, F, F, T, T, T, T }
	};
	
	private PDigit presentation;
	private int digit;	
	
	public void setActive(boolean active) {
		presentation.setActive(active);
	}



	public CDigit(CValuePanel parent){
		presentation = new PDigit();
		parent.addDigit(this);
		setDigit(0);
	}
	
	
	
	public PDigit getPresentation() {
		return presentation;
	}

	public void setDigit(int digit) {
		this.digit = digit;
		presentation.setSegmentsOn(DIGITS[digit]);
	}



	public int getDigit() {
		return digit;
	}
	
}
