package main;

import action.ActionReceiver;
import action.IAction;

public class Timer extends Thread{

	private int timing;
	private boolean mustContinue;
	private ActionReceiver actionManager;
	private IAction action;
	private boolean direct;
	private int tbr; // delay before repetition
	private boolean firstTop;
	
	public Timer(ActionReceiver actionManager, int timing, IAction action, boolean direct, int tbr){
		this.timing = timing;
		this.actionManager = actionManager;
		this.action = action;
		this.direct = direct;
		this.tbr = tbr;
		firstTop = true;
		mustContinue = false;
	}
	
	public Timer(ActionReceiver actionManager, int timing, IAction action, int tbr){
		this(actionManager, timing, action, true, tbr);
	}
	
	
	public Timer(ActionReceiver actionReceiver, int timing, IAction action, boolean direct) {
		this(actionReceiver, timing, action, direct, 0);
	}

	public boolean isRunning(){
		return mustContinue;
	}
	
	public void myStart(){
		mustContinue = true;
		start();
	}
	
	public void setTiming(int timing){
		this.timing = timing;
	}
	
	public int getTiming(){
		return timing;
	}
	
	public void finish(){
		mustContinue = false;
	}
	
	public void run(){
		int t;
		while(mustContinue){
			if(direct && mustContinue) actionManager.addAction(action, false);
			if(firstTop){
				t = tbr + timing;
				firstTop = false;
			}
			else
				t = timing;
			try {
				sleep(t);
			} catch (InterruptedException e) {
				System.err.println("Erreur : processus interrompu pendant un sleep!");
			}
			if(!direct && mustContinue) actionManager.addAction(action, false);
		}
	}

}
