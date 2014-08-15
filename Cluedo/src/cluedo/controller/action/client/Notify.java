package cluedo.controller.action.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.server.Move.Direction;
import cluedo.model.Board;

public class Notify implements SlaveAction{
	
	private ActionType actionType;
	private byte[] state;
	
	public Notify(DataInputStream input){
		try {
			//actionType = ActionType.values()[input.readInt()];
			System.out.println("Recieved Action Type: "+actionType);
			
			int lenght = input.readInt();
			state = new byte[lenght];
			input.read(state);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute(Board game) {
		//update the board
		try {
			game.fromByte(state);
			System.out.println("Slave board updated");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//check player's status then change the ui correspondingly
	}

}
