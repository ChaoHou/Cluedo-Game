package cluedo.controller.action.server;

import cluedo.controller.action.Action;
import cluedo.controller.connection.MasterConnection;
import cluedo.model.Board;

public interface MasterAction extends Action {
	public void execute(MasterConnection[] connections,Board game);
}