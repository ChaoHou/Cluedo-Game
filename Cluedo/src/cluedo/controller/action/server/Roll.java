package cluedo.controller.action.server;

import java.util.Random;

import cluedo.controller.action.ActionHelper;
import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Player;


public class Roll implements MasterAction{

	MasterConnection connection;
	
	public Roll(MasterConnection con){
		connection = con;
	}
	
	@Override
	public void execute(MasterConnection[] connections,Board game) {
		try {
			Player player = game.getPlayer(connection.uid());
			Random r = new Random();
			player.setDice(r.nextInt(7));
			
			player.setStatus(Player.STATUS.ROLLING);
			
		} catch (IllegalRequestException e) {
			e.printStackTrace();
		}
		
		ActionHelper.broadcast(connections, game);
	}


}
