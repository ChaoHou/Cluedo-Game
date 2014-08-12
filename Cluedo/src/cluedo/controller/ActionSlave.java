package cluedo.controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.Test.MockSlave;
import cluedo.controller.action.AbstractAction;
import cluedo.controller.action.AbstractAction.ActionType;
import cluedo.controller.action.Action;
import cluedo.controller.connection.Slave;
import cluedo.model.Board;
import cluedo.view.BoardFrame;

public class ActionSlave extends Thread implements ActionHandler,WindowListener{

	private Slave connection;
	private Board game;
	private BoardFrame frame;
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	public ActionSlave(Slave con){
		connection = con;
		game = new Board(100,100);
		frame = new BoardFrame("cludo",game,new MockSlave());
		frame.addWindowListener(this);
	}
	
	public void run(){
		System.out.println("CLIENT RUNNING");
		
		while(!connection.isClosed()){
			try {
				if(connection.getInput().available() != 0){
					//read the type of the action
					int index = connection.getInput().readInt();
					ActionType actionType = ActionType.values()[index];
					System.out.println(actionType);
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

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		try {
			connection.getOutput().writeInt(ActionType.DISCONNECTED.ordinal());
			connection.close();
			System.out.println("closing");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
