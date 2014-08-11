package cluedo.controller.action;

import java.io.IOException;

import cluedo.controller.connection.Master;
import cluedo.controller.connection.Slave;

public class Notify extends AbstractAction {

	private Master[] connections;
	private Slave connection;
	
	public Notify(Master[] con) {
		connections = con;
		server = true;
	}
	
	public Notify(Slave slave){
		connection = slave;
		server = false;
	}

	@Override
	public void execute() {
		if(server){
			serverAction();
		}else{
			clientAction();
		}
	}
	
	private void serverAction(){
		for(Master con:connections){
			try {
				con.getOutput().writeInt(AbstractAction.ActionType.NOTIFY.ordinal());
				con.getOutput().writeBytes("data");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void clientAction(){
		
	}

}
