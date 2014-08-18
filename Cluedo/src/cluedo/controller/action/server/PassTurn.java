package cluedo.controller.action.server;

import cluedo.controller.action.ActionHelper;
import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Player;
import cluedo.model.Player.STATUS;

/**
 * Server side Action
 * 
 * This will happen when a user finish move but not in a room, the user can choose to pass the turn
 * @author C
 *
 */
public class PassTurn implements MasterAction {

	MasterConnection connection;
	
	public PassTurn(MasterConnection master){
		connection = master;
	}
	
	@Override
	public void execute(MasterConnection[] connections, Board game) {
		try {
			Player player = game.getPlayer(connection.uid());
			player.setStatus(STATUS.FINISHTURN);
			
		} catch (IllegalRequestException e) {
			e.printStackTrace();
		}
		
		ActionHelper.broadcast(connections, game);
	}

}
