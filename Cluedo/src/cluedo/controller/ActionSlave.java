package cluedo.controller;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.controller.action.AbstractAction;
import cluedo.controller.action.AbstractAction.ActionType;
import cluedo.controller.action.Action;
import cluedo.controller.connection.Slave;

public class ActionSlave extends Thread implements ActionHandler {

	private Slave connection;
	
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	public ActionSlave(Slave con){
		connection = con;
	}
	
	public void run(){
		System.out.println("CLIENT RUNNING");
		
		while(1 == 1){
			try {
				if(connection.getInput().available() != 0){
					//read the type of the action
					int index = connection.getInput().readInt();
					ActionType actionType = ActionType.values()[index];
					
					Action action = AbstractAction.slaveActionFromType(actionType, connection);
					actionQueue.offer(action);
					
				}
				
				Thread.sleep(1000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean isEmpty() {
		return actionQueue.isEmpty();
	}

	@Override
	public Action pollAction() {
		return actionQueue.poll();
	}
}
