package cluedo.controller.action;

import java.io.IOException;

import cluedo.controller.connection.Master;
import cluedo.controller.connection.Slave;

public class Notify extends AbstractAction {

	private Master[] connections;
	private Slave connection;
	
	/**
	 * Constructor for server side notify action
	 * @param con
	 */
	public Notify(Master[] con) {
		connections = con;
		server = true;
	}
	
	/**
	 * Constructor for client side notify action
	 * @param con
	 */
	public Notify(Slave slave){
		connection = slave;
		server = false;
	}

	protected void serverAction(){
		for(Master con:connections){
			try {
				con.getOutput().writeInt(AbstractAction.ActionType.NOTIFY.ordinal());
				con.getOutput().writeBytes("data");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void clientAction(){
		
	}

}
