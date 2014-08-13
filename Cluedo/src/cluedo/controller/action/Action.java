package cluedo.controller.action;

public interface Action {
	/**
	 * An action will be create when the server or client recieve an action.
	 * Then will be executed
	 */
	public void execute();
}
