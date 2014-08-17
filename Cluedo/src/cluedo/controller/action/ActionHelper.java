package cluedo.controller.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.server.Initialize;
import cluedo.controller.action.server.Move;
import cluedo.controller.action.server.Move.Direction;
import cluedo.controller.action.server.Roll;
import cluedo.controller.connection.MasterConnection;
import cluedo.controller.connection.SlaveConnection;
import cluedo.model.Board;
import cluedo.model.Card;

public class ActionHelper{
	
	/**
	 * Type of the actions, the ordinal value will be passed to the slave 
	 * in order to indicate current action type
	 * @author C
	 *
	 */
	public enum ActionType {
		INITIALIZE,
		ROLL,
		MOVE,
		SUGGESTION,
		ACCUSATION,
		REFUTE,
		NOTIFY,
		DISCONNECT,
	}
	
	public static Action genServerAction(MasterConnection connection,ActionType type){
		if(type.equals(ActionType.INITIALIZE)){
			return new Initialize(connection);
		}else if(type.equals(ActionType.MOVE)){
			return new Move(connection);
		}else if(type.equals(ActionType.SUGGESTION)){
			
		}else if(type.equals(ActionType.ACCUSATION)){
			
		}else if(type.equals(ActionType.ROLL)){
			return new Roll(connection);
		}else if(type.equals(ActionType.REFUTE)){
	
		}
		
		throw new IllegalArgumentException("INVALID TYPE");
	}
	
	/**
	 * Client request
	 * @param playerInfo 
	 * @param output 
	 */
	public static void requestInitialize(SlaveConnection connection, String name, Card.CHARACTER character){
		try {
			assert(connection != null);
			assert(name != null);
			assert(character != null);
			
			System.out.println("Send initialize request");
			DataOutputStream output = connection.getOutput();
			output.writeInt(ActionType.INITIALIZE.ordinal());
			
			//send the token and name to server
			output.writeInt(character.ordinal());
			byte[] nameBytes = name.getBytes("UTF-8");
			output.writeInt(nameBytes.length);
			output.write(nameBytes);
			
			output.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Client request
	 */
	public static void requestRoll(SlaveConnection connection){
		try {
			assert(connection != null);
			
			System.out.println("Send roll request");
			DataOutputStream output = connection.getOutput();
			output.writeInt(ActionType.ROLL.ordinal());
			output.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Client request
	 */
	public static void requestMove(SlaveConnection connection,Direction dir){
		try {
			assert(connection != null);
			assert(dir != null);
			
			System.out.println("Send move request");
			DataOutputStream output = connection.getOutput();
			output.writeInt(ActionType.MOVE.ordinal());
			output.writeInt(dir.ordinal());
			output.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Client request
	 */
	public static void requestAnnouncement(SlaveConnection connection,ActionType actionType,Card.CHARACTER character,Card.WEAPON weapon,Card.ROOM room){
		try {
			assert(connection != null);
			assert(character != null);
			assert(weapon != null);
			assert(room != null);
			
			System.out.println("Send suggestion request");
			DataOutputStream output = connection.getOutput();
			output.writeInt(actionType.ordinal());
			output.writeInt(character.ordinal());
			output.writeInt(weapon.ordinal());
			output.writeInt(room.ordinal());
			output.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Client request
	 */
	public static void requestRefute(SlaveConnection connection,Card card){
		try {
			assert(connection != null);
			assert(card != null);
			
			System.out.println("Send refute request");
			DataOutputStream output = connection.getOutput();
			output.writeInt(ActionType.REFUTE.ordinal());
			
			//send the card to server
			output.writeInt(card.getType().ordinal());
			String name = card.getName();
			byte[] nameByte = name.getBytes("UTF-8");
			output.writeInt(nameByte.length);
			output.write(nameByte);

			output.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void requestDisconnect(Socket socket){
		try {
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeInt(ActionType.DISCONNECT.ordinal());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Server broadcast
	 */
	public static void broadcast(MasterConnection[] connections,Board game){
		try {
			assert(connections != null);
			
			System.out.println("Send broadcast to all");
			for(MasterConnection connection:connections){
				assert(connection != null);
				
				System.out.println("Send broadcast to uid: "+connection.uid());
				DataOutputStream output = connection.getOutput();
				output.writeInt(ActionType.NOTIFY.ordinal());
				//output.writeInt(type.ordinal());
				
				byte[] state = game.toByte();
				output.writeInt(state.length);
				output.write(state);
				
				output.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
