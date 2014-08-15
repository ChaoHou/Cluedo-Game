package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.client.Notify;
import cluedo.controller.connection.MasterConnection;
import cluedo.model.Board;

public class Move implements MasterAction{

	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	}
	
	private MasterConnection connection; 
	private Direction direction;
	
	/**
	 * Constructor for a move action from slave
	 * @param x
	 * @param y
	 */
	public Move(MasterConnection con){
		connection = con;
		try {
			direction = Direction.values()[connection.getInput().readInt()];
		} catch (IOException e) {
			e.printStackTrace();
		}
;
	}
	
	@Override
	public void execute(MasterConnection[] connections,Board game) {
		System.out.println("Move recieved");
		//make the board moving the player
		
		ActionHelper.broadcast(connections, ActionType.MOVE);
		System.out.println("Sent confirmition to client");
	}

}
