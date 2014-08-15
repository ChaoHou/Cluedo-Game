package cluedo.controller.action.server;

import java.io.DataInputStream;
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
import cluedo.model.Card;
import cluedo.model.Chara;
import cluedo.model.Player;
import cluedo.model.Player.STATUS;

public class Initialize implements MasterAction{
	
	private MasterConnection connection;
	
	private byte[] nameBytes;
	private Card.CHARACTER character;
	
	public Initialize(MasterConnection master) {
		this.connection = master;
		
		try {
			
			System.out.println("Server initialize recieved");
			DataInputStream input = connection.getInput();
			//read the player info from client
			character = Card.CHARACTER.values()[input.readInt()];
			int length;
			length = input.readInt();
			nameBytes = new byte[length];
			input.read(nameBytes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void execute(MasterConnection[] connections,Board game) {
		try {
			assert(connections != null);
			assert(game != null);
			
			
			String name = new String(nameBytes,"UTF-8");
			//update the player info
			Player player = game.getPlayer(connection.uid());
			player.setCharacter(new Chara(character));
			player.setUName(name);
			player.setStatus(STATUS.WATCHING);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//breadcast the state
		ActionHelper.broadcast(connections, ActionType.INITIALIZE,game);
	}
	
	
}
