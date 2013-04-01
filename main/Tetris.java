package main;

import ihm.PGridTetris;
import ihm.PNextBlock;
import ihm.PPartie;
import action.ActionReceiver;
import action.Actions;



/**
 * 
 * @author Guéhenneux Jonathan
 * 04/10/07
 * 
 * Tetris : main object for a tetris game.
 *
 */

public class Tetris {

	// classic rules : width = 10, height = 22
	public static final int width = 10, height = 22;

	private static final int[] speeds = { 500, 400, 320, 260, 200, 160, 130, 110, 90, 70, 60 ,50, 37, 25, 15, 10 };

	public enum Position { OUT_LEFT, OUT_RIGHT, OUT_SOUTH, OUT_NORTH, OVER, IN }

	private TetrisBlock nextBlock, currentBlock;
	private TetrisGrid grid;
	private boolean pause, lost;
	public boolean mustIgnoreNextLanding;

	private ActionReceiver actionReceiver;
	
	private PGridTetris gridPanel;
	private PPartie statePanel;
	private PNextBlock nextBlockPanel;

	private Timer gravity;
	private int level;

	private int nbLines, nbLinesInThisLevel;

	private int score;

	private int timeBetweenActions;
	private int timeBeforeLanding;

	private int delayBeforeRepetition;
	

	public void setGridPanel(PGridTetris gridPanel) {
		this.gridPanel = gridPanel;
	}

	public Tetris(PPartie statePanel, ActionReceiver actionReceiver){
		this.actionReceiver = actionReceiver;
		this.statePanel = statePanel;
		timeBeforeLanding = 200;
		timeBetweenActions = 100;
		delayBeforeRepetition = 0;
		init();
	}

	public void init(){
		currentBlock = null;
		nextBlock = new TetrisBlock(this);
		setPause(false);
		lost = false;
		grid = new TetrisGrid(width, height);
		setLevel(0);
		setTotal(0);
		setLines(0);
		setScore(0);
		mustIgnoreNextLanding = false;
	}

	private void setPause(boolean b) {
		pause = b;
	}

	private void setScore(int i) {
		score = i;
		statePanel.setScore(i);
	}

	private void setLines(int i) {
		nbLinesInThisLevel = i;
		statePanel.setLines(i);
	}

	private void setTotal(int i) {
		nbLines = i;
		statePanel.setTotal(i);
	}

	private void setLevel(int i) {
		level = i;
		statePanel.setLevel(i);
	}

	public void start(){
		newBlock();
		launchGravity();
	}
	
	

	private void launchGravity(){
		gravity = new Timer(actionReceiver, speeds[level], Actions.getActionDown(), false);
		gravity.myStart();
	}

	private void newBlock() {
		currentBlock = nextBlock;

		if(whereIAm(currentBlock, currentBlock.getX(), currentBlock.getY()) ==
			Position.OVER)
			lose();

		else{
			gridPanel.paintCurrentBlock();
			nextBlock = new TetrisBlock(this);
			nextBlockPanel.repaint();
		}
		
		actionReceiver.clearActions();
		actionReceiver.allowActions();
	}

	public TetrisBlock getCurrentBlock(){
		return currentBlock;
	}

	public TetrisBlock getNextBlock(){
		return nextBlock;
	}

	public void stop(){
		statePanel.hideOptionFrame();
		gravity.finish();
		currentBlock = null;
		init();
		gridPanel.clearGame();
		start();
	}

	public void pause(){
		if(pause){
			statePanel.hideOptionFrame();
			launchGravity();
			pause = false;
		}
		else{
			gravity.finish();
			pause = true;
		}
	}

	public void rotate() {
		if(currentBlock != null && currentBlock.canRotate()) currentBlock.rotate();
	}

	public void moveDown(){
		if(currentBlock != null){
			if(currentBlock.canMoveDown()) currentBlock.moveDown();
			else if(!currentBlock.isLanding()) currentBlock.prepareToLand();
		}
	}

	public void moveLeft() {
		if(currentBlock != null && currentBlock.canMoveLeft()) currentBlock.moveLeft();
	}

	public void moveRight() {
		if(currentBlock != null && currentBlock.canMoveRight()) currentBlock.moveRight();
	}

	public Position whereIAm(TetrisBlock block, int x, int y) {
		for(int i = 0 ; i < 4 ; i++)
			for(int j = 0 ; j < 4 ; j++)
				if(block.isSquare(i, j)){
					if(x+i < 0) return Position.OUT_LEFT;
					if(x+i >= width) return Position.OUT_RIGHT;
					if(y+j >= height) return Position.OUT_SOUTH;
					if(y+j < 0) return Position.OUT_NORTH;
					if(!isFree(x+i, y+j)) return Position.OVER;
				}
		return Position.IN;
	}

	public boolean isFree(int i, int j){	
		return grid.isFree(i, j);
	}

	public void land() {
		if(!mustIgnoreNextLanding){
			actionReceiver.blockActions();
			int x = currentBlock.getX();
			int y = currentBlock.getY();
			int c = currentBlock.getNumber();
			for(int i = 0 ; i < 4 ; i++)
				for(int j = 0 ; j < 4 ; j++)
					if(currentBlock.isSquare(i, j))
						grid.fill(x+i, y+j, c);
			currentBlock = null;
	
			destroyFullLines();
		}
		else{
			mustIgnoreNextLanding = false;
			currentBlock.setLanding(false);
		}
	}

	public void destroyFullLines() {
		new Thread(){
			public void run(){
				boolean[] frozenLines = new boolean[height];
				int nbFrozenLines = 0;
				for(int i = 0 ; i < height ; i++)
					if(grid.isLineFull(i)){
						frozenLines[i] = true;
						nbFrozenLines++;
					}


				for(int by = 0 ; by < height ; by++)
					for(int bx = 0 ; bx < width ; bx++){

						if(frozenLines[by]){
							grid.fill(bx, by, 7);
							gridPanel.paintSquare(bx, by);
							try {
								sleep(5);
							} catch (InterruptedException e) {
							}
						}
					}


				for(int by = 0 ; by < height ; by++)
					for(int bx = 0 ; bx < width ; bx++){

						if(frozenLines[by]){
							grid.empty(bx, by);
							gridPanel.paintSquare(bx, by);
							try {
								sleep(5);
							} catch (InterruptedException e) {
							}
						}
					}

				setLines(nbLinesInThisLevel += nbFrozenLines);
				setTotal(nbLines + nbFrozenLines);

				if(nbLinesInThisLevel > 9){
					setLines(nbLinesInThisLevel-= 10);

					if(level < 15){
						setLevel(level+1);
						gravity.setTiming(speeds[level]);
					}
				}

				int nbDestroyedLines = 0;

				for(int j = height-1 ; j >= 0 ; j--){
					if(frozenLines[j]) nbDestroyedLines++;
					else{
						if(nbDestroyedLines > 0){
							grid.downLine(j, nbDestroyedLines);

						}
					}
				}

				switch(nbDestroyedLines){
				case 1:
					setScore(score + 40 * (level+1));
					break;
				case 2:
					setScore(score + 100 * (level+1));
					break;
				case 3:
					setScore(score + 300 * (level+1));
					break;
				case 4:
					setScore(score + 1000 * (level+1));
					break;
				}

				gridPanel.paintAll();
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				newBlock();

			}
		}.start();
	}

	public void lose() {
		lost = true;
		currentBlock = null;
	
		new Thread(){

			public void run(){
				for(int by = 0 ; by < height ; by++)
					for(int bx = 0 ; bx < width ; bx++){
						if(grid.isFull(bx, by)) grid.fill(bx, by, 7);
						gridPanel.paintSquare(bx, by);
						try {
							sleep(5);
						} catch (InterruptedException e) {
						}
					}
			}
		}.start();
	}

	public boolean isLost() {
		return lost;
	}

	public TetrisGrid getGrid() {
		return grid;
	}

	public void currentBlockHasMoved() {
		gridPanel.paintCurrentBlock();
	}

	public void setNextBlockPanel(PNextBlock nextBlockPanel) {
		this.nextBlockPanel = nextBlockPanel;
	}

	public boolean isPaused() {
		return pause;
	}

	public int getTimeBeforeLanding() {
		return timeBeforeLanding;
	}

	public void setTimeBeforeLanding(int timeBeforeLanding) {
		this.timeBeforeLanding = timeBeforeLanding;
	}

	public int getTimeBetweenActions() {
		return timeBetweenActions;
	}

	public void setTimeBetweenActions(int timeBetweenActions) {
		this.timeBetweenActions = timeBetweenActions;
	}
	

	public ActionReceiver getActionReceiver() {
		return actionReceiver;
	}

	public void setDelayBeforeRepetition(int delayBeforeRepetition) {
		this.delayBeforeRepetition = delayBeforeRepetition;
	}

	public int getDelayBeforeRepetition() {
		return delayBeforeRepetition;
	}
	

	

}
