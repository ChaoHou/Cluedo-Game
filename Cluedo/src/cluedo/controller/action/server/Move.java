package cluedo.controller.action.server;

import java.io.DataOutputStream;
import java.io.IOException;

import cluedo.controller.action.AbstractAction;
import cluedo.controller.action.AbstractAction.ActionType;

public class Move extends AbstractAction{

	private int x;
	private int y;
	/**
	 * Constructor for a move action from slave
	 * @param x
	 * @param y
	 */
	public Move(int x,int y){
		this.x = x;
		this.y = y;
		server = false;
	}
	

	protected void clientAction() {
		System.out.println("Coord x: "+x+" y: "+y);
	}
	
	public static void sendMove(DataOutputStream output,int x,int y){
		try {
			output.writeInt(ActionType.MOVE.ordinal());
			output.writeInt(x);
			output.writeInt(y);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
