package cluedo.controller.action.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.AbstractAction;
import cluedo.controller.action.AbstractAction.ActionType;
import cluedo.controller.action.server.Move.Direction;
import cluedo.controller.connection.Master;
import cluedo.controller.connection.Slave;

public class Notify extends AbstractAction {
	
	private DataInputStream input;
	
	private ActionType actionType;
	
	private Direction direction;
	
	public Notify(DataInputStream in){
		input = in;
		try {
			actionType = ActionType.values()[input.readInt()];
			System.out.println("Recieved Action Type: "+actionType);
			if(actionType.equals(ActionType.MOVE)){
				direction = Direction.values()[input.readInt()];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		server = false;
	}

	@Override
	public void execute() {
		if(actionType.equals(ActionType.MOVE)){
			System.out.println("Move confirmed");
			System.out.println("Direction: "+direction);
		}
	}
	
	public static void sendMessageMove(DataOutputStream output,Direction dir){
		try {
			output.writeInt(ActionType.NOTIFY.ordinal());
			output.writeInt(ActionType.MOVE.ordinal());
			output.writeInt(dir.ordinal());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
