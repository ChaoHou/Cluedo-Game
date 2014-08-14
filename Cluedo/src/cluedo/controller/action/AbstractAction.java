package cluedo.controller.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.AbstractAction.ActionType;
import cluedo.controller.action.server.Move;
import cluedo.controller.action.server.Move.Direction;
import cluedo.controller.connection.Slave;

public abstract class AbstractAction implements Action{
	
	/**
	 * Type of the actions, the ordinal value will be passed to the slave 
	 * in order to indicate current action type
	 * @author C
	 *
	 */
	public enum ActionType {
		INITIALIZE,
		MOVE,
		SUGGESTION,
		ACCUSATION,
		ROLL,
		REFUTE,
		NOTIFY,
		DISCONNECTED,
	}
	
	/**
	 * To indicate whether this is a server side action or a client side action
	 */
	protected boolean server;
	
	public abstract void execute();
	
	public static Action serverSideAction(ActionType type,DataOutputStream output, DataInputStream input){
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

	public static Action clientSideAction(ActionType type, DataInputStream input) {
		
		
		return null;
	}
}
