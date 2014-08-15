package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.client.Notify;
import cluedo.controller.connection.Master;
import cluedo.model.Board;

public class Move implements Action{

	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	}
	
	private Master[] connections;
	private Board game;
	private int uid; 
	private Direction direction;
	
	/**
	 * Constructor for a move action from slave
	 * @param x
	 * @param y
	 */
	public Move(Master[] masters,Board board,int id,Direction dir){
		connections = masters;
		game = board;
		uid = id;
		direction = dir;
	}
	
	@Override
	public void execute() {
		System.out.println("Move recieved");
		//make the board moving the player
		
		ActionHelper.broadcast(connections, ActionType.MOVE);
		System.out.println("Sent confirmition to client");
	}

}
