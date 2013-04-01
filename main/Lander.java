package main;

import action.ActionReceiver;
import action.Actions;

public class Lander extends Thread{

	private int timing;
	private boolean alive;
	private ActionReceiver actionReceiver;
	
	public Lander(int timing, TetrisBlock block, ActionReceiver actionReceiver){
		this.timing = timing;
		this.actionReceiver = actionReceiver;
		alive = true;
	}


	public void run(){

		try {
			sleep(timing);
		} catch (InterruptedException e) {
		}
		
		if(alive) actionReceiver.addAction(Actions.getActionLand(), false);
		
	}


	public void kill() {
		alive = false;
	}

}
