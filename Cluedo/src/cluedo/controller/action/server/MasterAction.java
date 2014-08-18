package cluedo.controller.action.server;

import cluedo.controller.action.Action;
import cluedo.controller.connection.MasterConnection;
import cluedo.model.Board;

/**
 * Interface for Server side Actions
 * @author C
 *
 */
public interface MasterAction extends Action {
	public void execute(MasterConnection[] connections,Board game);
}
