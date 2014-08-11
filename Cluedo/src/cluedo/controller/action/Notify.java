package cluedo.controller.action;

import java.io.IOException;

import cluedo.controller.connection.Master;

public class Notify extends AbstractAction {

	private boolean server;
	
	private Master[] connections;
	
	public Notify(Master[] con) {
		connections = con;
	}

	@Override
	public void execute() {
		for(Master con:connections){
			try {
				con.getOutput().writeInt(AbstractAction.ActionType.NOTIFY.ordinal());
				con.getOutput().writeBytes("data");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
