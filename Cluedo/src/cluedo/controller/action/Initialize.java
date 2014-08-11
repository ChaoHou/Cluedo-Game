package cluedo.controller.action;

import cluedo.controller.connection.Master;
import cluedo.controller.connection.Slave;

public class Initialize extends AbstractAction {
	
	private Master[] connections;
	private Slave connection;
	
	public Initialize(Master[] con) {
		connections = con;
		server = true;
	}
	
	public Initialize(Slave slave){
		connection = slave;
		server = false;
	}

	protected void serverAction(){
		
	}
	
	protected void clientAction(){
		
	}
}
