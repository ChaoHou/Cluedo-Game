package cluedo.controller;

import cluedo.controller.action.Action;

/**
 * Interface for action handler
 * @author C
 *
 */
public interface ActionHandler {
	/**
	 * Check if the action queue is empty
	 * @return
	 */
	public boolean isEmpty();
	/**
	 * poll an action from action queue
	 * @return
	 */
	public Action pollAction();
	
	/**
	 * Offer an action to the action queue
	 * @param action
	 */
	public void offerAction(Action action);
}
