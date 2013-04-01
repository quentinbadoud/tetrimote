package action;

import main.Tetris;

public class Actions {

	private Tetris tetris;
	
	private static ActionDown actionDown;
	private static ActionLeft  actionLeft;
	private static ActionRight actionRight;
	private static ActionRotate actionRotate;
	private static ActionStop actionStop;
	private static ActionLand actionLand;
	private static ActionPause actionPause;
	
	public Actions(Tetris tetris){
		this.tetris = tetris;
		createSingletons();
	}
	
	private void createSingletons(){
		actionDown = new ActionDown();
		actionDown.setTetris(tetris);
		actionLeft = new ActionLeft();
		actionLeft.setTetris(tetris);
		actionLand = new ActionLand();
		actionLand.setTetris(tetris);
		actionPause = new ActionPause();
		actionPause.setTetris(tetris);
		actionRight = new ActionRight();
		actionRight.setTetris(tetris);
		actionRotate = new ActionRotate();
		actionRotate.setTetris(tetris);
		actionStop = new ActionStop();
		actionStop.setTetris(tetris);
	}

	public static ActionDown getActionDown() {
		return actionDown;
	}

	public static ActionLand getActionLand() {
		return actionLand;
	}

	public static ActionLeft getActionLeft() {
		return actionLeft;
	}

	public static ActionPause getActionPause() {
		return actionPause;
	}

	public static ActionRight getActionRight() {
		return actionRight;
	}

	public static ActionRotate getActionRotate() {
		return actionRotate;
	}

	public static ActionStop getActionStop() {
		return actionStop;
	}
	
	
	
}
