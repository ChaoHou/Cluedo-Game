package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.action.client.Notify;

public class Move implements Action{

	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	}
	
	private Direction direction;
	
	private DataOutputStream output;
	/**
	 * Constructor for a move action from slave
	 * @param x
	 * @param y
	 */
	public Move(DataOutputStream out,Direction dir){
		output = out;
		direction = dir;
	}
	
	@Override
	public void execute() {
		System.out.println("Move recieved");
		Notify.sendMessageMove(output, direction);
		System.out.println("Sent confirmition to client");
	}

}
