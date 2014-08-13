package cluedo.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class ActionSlave extends Thread implements ActionHandler,MouseListener{

	private Slave connection;
	private Board game;
	private int gameClock;
	private BoardFrame frame;
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	public ActionSlave(Slave con,int clock){
		connection = con;
		gameClock = clock;
		game = new Board(100,100);
		frame = new BoardFrame("cludo",game,this);
		//frame.addWindowListener(this);
	}
	
	public void run(){
		System.out.println("CLIENT RUNNING");
		
		while(1 == 1){
			try {
				Thread.sleep(gameClock);
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
	public void offerAction(Action action) {
		actionQueue.offer(action);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//check player's state, if needs inputs, 
		// send info to server
		//if server confirmed the action, change player's state
		System.out.println("clicked");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
