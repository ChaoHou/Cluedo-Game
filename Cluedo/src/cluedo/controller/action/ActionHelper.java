package cluedo.controller.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.server.Move;
import cluedo.controller.action.server.Move.Direction;
import cluedo.controller.connection.Slave;

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
	
	public static Action genServerAction(ActionType type,DataOutputStream output, DataInputStream input){
		if(type.equals(ActionType.INITIALIZE)){
			//return new Initialize(slave);
		}
		if(type.equals(ActionType.MOVE)){
			
			try {
				Direction dir = Direction.values()[input.readInt()];
				return new Move(output,dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
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
	public static void requestInitialize(DataOutputStream output, String[] playerInfo){
		try {
			assert(output != null);
			assert(playerInfo.length == 2);
			
			System.out.println("Send initialize request");
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
	public static void requestRoll(DataOutputStream output){
		try {
			assert(output != null);
			
			System.out.println("Send roll request");
			output.writeInt(ActionType.ROLL.ordinal());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Client request
	 */
	public static void requestMove(DataOutputStream output,Direction dir){
		try {
			assert(output != null);
			assert(dir != null);
			
			System.out.println("Send move request");
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
	public static void broadcastInit(){
		
	}
	/**
	 * Server broadcast
	 */
	public static void broadcastMove(){
		
	}
	/**
	 * Server broadcast
	 */
	public static void broadcastRoll(){
		
	}
	/**
	 * Server broadcast
	 */
	public static void broadcastSuggestion(){
		
	}
	/**
	 * Server broadcast
	 */
	public static void broadcastAccusation(){
		
	}
	/**
	 * Server broadcast
	 */
	public static void broadcastRefute(){
		
	}
	
	
}
