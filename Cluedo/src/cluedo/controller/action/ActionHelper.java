package cluedo.controller.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.server.Initialize;
import cluedo.controller.action.server.Move;
import cluedo.controller.action.server.Move.Direction;
import cluedo.controller.connection.MasterConnection;
import cluedo.controller.connection.SlaveConnection;
import cluedo.model.Board;

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
		DISCONNECTED,
	}
	
	public static Action genServerAction(MasterConnection connection,ActionType type){
		if(type.equals(ActionType.INITIALIZE)){
			return new Initialize(connection);
		}
		if(type.equals(ActionType.MOVE)){
			return new Move(connection);
		}
		if(type.equals(ActionType.SUGGESTION)){
			
		}
		if(type.equals(ActionType.ACCUSATION)){
			
		}
		if(type.equals(ActionType.ROLL)){
			
		}
		if(type.equals(ActionType.REFUTE)){
	
		}
		if(type.equals(ActionType.NOTIFY)){
			//return new Notify(slave);
		}
		
		throw new IllegalArgumentException("INVALID TYPE");
	}

	public static Action genClientAction(ActionType type, DataInputStream input) {
		
		return null;
	}
	
	/**
	 * Client request
	 * @param playerInfo 
	 * @param output 
	 */
	public static void requestInitialize(SlaveConnection connection, String[] playerInfo){
		try {
			assert(connection != null);
			assert(playerInfo.length == 2);
			
			System.out.println("Send initialize request");
			DataOutputStream output = connection.getOutput();
			output.writeInt(ActionType.INITIALIZE.ordinal());
			output.writeUTF(playerInfo[0]);
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
	public static void requestSuggestion(){
		
	}
	/**
	 * Client request
	 */
	public static void requestAccusation(){
		
	}
	/**
	 * Client request
	 */
	public static void requestRefute(){
		
	}
	
	/**
	 * Server broadcast
	 */
	public static void broadcast(MasterConnection[] connections,ActionType type){
		try {
			assert(connections != null);
			
			System.out.println("Send "+type.toString()+" broadcast to all");
			for(MasterConnection connection:connections){
				assert(connection != null);
				
				System.out.println("Send "+type.toString()+" broadcast to uid: "+connection.uid());
				DataOutputStream output = connection.getOutput();
				output.writeInt(ActionType.NOTIFY.ordinal());
				output.writeInt(type.ordinal());
				//TODO
				//get bytes from board
				//send the length of the bytes
				//send the bytes
				
				output.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
