package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.client.Notify;
import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Player;

/**
 * Server side Actions
 * 
 * Will read user input which is Direction from connection
 * When executing, check the remaining steps,if remaining steps is 0,make the user starting make announcement
 * 
 * @author C
 *
 */
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
		System.out.println("Move instance created");
		
		connection = con;
		try {
			direction = Direction.values()[connection.getInput().readInt()];
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void execute(MasterConnection[] connections,Board game) {
		System.out.println("Server move recieved");
		//make the board moving the player
		try {
			game.movePlayer(connection.uid(), direction);
			Player player = game.getPlayer(connection.uid());
			if(!player.canMove()){
				player.setStatus(Player.STATUS.MAKINGANNOUNCEMENT);
			}
			
		} catch (IllegalRequestException e) {
			e.printStackTrace();
		}
		
		System.out.println("Server Player moved");
		
		ActionHelper.broadcast(connections,game);
	}

}
