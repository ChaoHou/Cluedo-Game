package cluedo.controller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.controller.action.Action;
import cluedo.controller.connection.Slave;

public class ActionSlave extends Thread implements ActionHandler {

	private Slave connection;
	
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	public ActionSlave(Slave con){
		connection = con;
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
