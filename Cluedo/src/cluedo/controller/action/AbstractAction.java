package cluedo.controller.action;

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
	}
	
	/**
	 * To indicate whether this is a server side action or a client side action
	 */
	protected boolean server;
	
	/**
	 * depends on the mode of current user, dispatch the relevant method 
	 */
	public void execute() {
		if(server){
			serverAction();
		}else{
			clientAction();
		}
	}
	
	/**
	 * The server side action
	 */
	protected abstract void serverAction();
	
	/**
	 * The client side Action
	 */
	protected abstract void clientAction();
	
	public static Action slaveActionFromType(ActionType type, Slave slave){
		if(type.equals(ActionType.INITIALIZE)){
			return new Initialize(slave);
		}
		if(type.equals(ActionType.MOVE)){
			
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
			return new Notify(slave);
		}
		
		throw new IllegalArgumentException("INVALID TYPE");
	}
}
