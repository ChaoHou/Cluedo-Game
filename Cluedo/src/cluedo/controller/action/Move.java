package cluedo.controller.action;

import java.io.DataOutputStream;
import java.io.IOException;

public class Move extends AbstractAction{

	private int x;
	private int y;
	public Move(int x,int y){
		this.x = x;
		this.y = y;
		server = false;
	}
	
	@Override
	protected void serverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
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

}
