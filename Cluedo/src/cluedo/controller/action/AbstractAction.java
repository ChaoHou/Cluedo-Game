package cluedo.controller.action;

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
}
