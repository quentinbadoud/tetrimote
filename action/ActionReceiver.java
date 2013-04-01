package action;

import java.util.LinkedList;

public class ActionReceiver {

	private LinkedList<IAction> actions;
	
	private boolean actionsAllowed;
	
	public ActionReceiver(){
		actions = new LinkedList<IAction>();
		actionsAllowed = false;
	}
	
	
	
	public synchronized void blockActions(){
		actionsAllowed = false;
	}
	
	public synchronized void allowActions(){
		actionsAllowed = true;
	}
	
	public synchronized void clearActions(){
		actions.clear();
	}
	
	public synchronized void addAction(IAction a, boolean isPrioritary){
		if(actionsAllowed){
			if(isPrioritary) actions.addFirst(a);
			else actions.addLast(a);
			notify();
		}
	}
	
	public synchronized IAction getNextAction(){
		IAction action;
		while(actions.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Interruption pendant l'attente d'une action a traiter.\nCa peut arriver a la sortie de l'application.");
			}
		action = actions.getFirst();
		actions.removeFirst();
		return action;
		
	}
	
}
