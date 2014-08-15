package cluedo.controller.action.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cluedo.controller.Round;
import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.connection.MasterConnection;
import cluedo.controller.connection.SlaveConnection;
import cluedo.model.Board;

public class Initialize implements MasterAction{
	
	private MasterConnection connection;
	
	public Initialize(MasterConnection master) {
		this.connection = master;
	}

	@Override
	public void execute(MasterConnection[] connections,Board game) {
		//get the player from the  board using UID
		//update the player's user name, character, status
		//broadcast the board bytes
		
		ActionHelper.broadcast(connections, ActionType.INITIALIZE);
	}
	
	
}
