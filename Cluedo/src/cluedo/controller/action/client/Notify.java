package cluedo.controller.action.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.server.Move.Direction;
import cluedo.controller.connection.SlaveConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Player;
import cluedo.view.BoardFrame;

public class Notify implements SlaveAction{
	
	SlaveConnection connection;
	private byte[] state;
	
	public Notify(SlaveConnection con){
		try {
			connection = con;
			DataInputStream input = connection.getInput();
			int lenght = input.readInt();
			state = new byte[lenght];
			input.read(state);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute(Board game,BoardFrame frame) {
		//update the board
		try {
			game.fromByte(state);
			System.out.println("Slave board updated");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//check player's status then change the ui correspondingly
		try {
			Player player = game.getPlayer(connection.uid());
			
			Player.STATUS status = player.getStatus();
			if(status.equals(Player.STATUS.ROLLING)){
				frame.enableRoll();
			}
			//if the play's status is rolling, enable the roll
			//if it's moving enable moving
			//if it's making announcement enable buttons
			//if it's refuting, enable refute
			//if it's eliminated, disable actions other than refute
		} catch (IllegalRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
