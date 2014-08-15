package cluedo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.Test.MockSlave;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.server.Move;
import cluedo.controller.action.server.Move.Direction;
import cluedo.controller.action.Action;
import cluedo.controller.connection.SlaveConnection;
import cluedo.model.Board;
import cluedo.view.BoardFrame;

public class SlaveActionHandler extends Thread implements ActionHandler,MouseListener,ActionListener,KeyListener{

	private SlaveConnection connection;
	private Board game;
	private int gameClock;
	private BoardFrame frame;
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	public SlaveActionHandler(SlaveConnection con,int clock){
		connection = con;
		gameClock = clock;
		
		//game = new Board(400,400);
		frame = new BoardFrame("cludo",new Board(null),this,this);
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.requestFocus();
		//enable the popup to ask user for user name and token
		//then send to server
	}
	
	public void run(){
		System.out.println("CLIENT RUNNING");
		//TODO, the return value could be name(String),Index of chara(int)
		String[] playerInfo = frame.initPlayer();
		ActionHelper.requestInitialize(connection,playerInfo);
		
		while(1 == 1){
			try {
				if(!actionQueue.isEmpty()){
					Action action = actionQueue.poll();
					action.execute();
				}
				
				
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
	public void mouseClicked(MouseEvent arg) {
		//check player's state, if needs inputs, 
		// send info to server
		//if server confirmed the action, change player's state
		System.out.println("MOUSE CLICKED");
		int x = arg.getX();
		int y = arg.getY();
		
		//Move.sendMove(connection.getOutput(), x, y);
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg) {
		System.out.println("Key preessed");
		//TODO check the state of the board, if allows to move
		
		int keyCode = arg.getKeyCode();
		Direction dir = null;
		if(keyCode == KeyEvent.VK_UP){
			dir = Direction.UP;
		}else if(keyCode == KeyEvent.VK_DOWN){
			dir = Direction.DOWN;
		}else if(keyCode == KeyEvent.VK_LEFT){
			dir = Direction.LEFT;
		}else if(keyCode == KeyEvent.VK_RIGHT){
			dir = Direction.RIGHT;
		}
		ActionHelper.requestMove(connection, dir);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
