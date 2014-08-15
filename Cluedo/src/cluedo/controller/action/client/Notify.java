package cluedo.controller.action.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.server.Move.Direction;

public class Notify implements Action{
	
	private ActionType actionType;
	private byte[] boardInfo;
	
	public Notify(DataInputStream input){
		try {
			actionType = ActionType.values()[input.readInt()];
			System.out.println("Recieved Action Type: "+actionType);
			
			//TODO
			//read the length of the bytes
			//read the bytes into field
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {
		//TODO
		//update the board using the bytes
		
		//update the board message depends on the action performed
		//needs to check whether it's current player's action
		if(actionType.equals(ActionType.MOVE)){
			System.out.println("Move confirmed");
		}
	}

}
