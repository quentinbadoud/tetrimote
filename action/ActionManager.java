package action;


public class ActionManager extends Thread{

	private ActionReceiver actionReceiver;
	
	public ActionManager(ActionReceiver actionReceiver){
		this.actionReceiver = actionReceiver;
	}
	
	public void run(){
		while(true)
			actionReceiver.getNextAction().execute();
	}
	
}
