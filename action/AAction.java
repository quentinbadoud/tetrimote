package action;

import main.Tetris;

public abstract class AAction implements IAction{

	public Tetris tetris;
	
	public void setTetris(Tetris tetris){
		this.tetris = tetris;
	}

	public Tetris getTetris() {
		return tetris;
	}
	
	
}
