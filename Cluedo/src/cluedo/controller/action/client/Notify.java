package cluedo.controller.action.client;

import java.io.IOException;

import cluedo.controller.action.AbstractAction;
import cluedo.controller.action.AbstractAction.ActionType;
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
				//in case the connection is closed
				if(con.isClosed()) continue;
				
				con.getOutput().writeInt(AbstractAction.ActionType.NOTIFY.ordinal());
				byte[] bytes = "data".getBytes("UTF-8");
				con.getOutput().writeInt(bytes.length);
				con.getOutput().write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void clientAction(){
		try {
			if(connection.getInput().available() != 0){
				int length = connection.getInput().readInt();
				byte[] data = new byte[length];
				System.out.println("length:"+length);
				connection.getInput().readFully(data);
				String str = new String(data,"UTF-8");
				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
