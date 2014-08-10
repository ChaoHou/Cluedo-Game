package cluedo.controller.connection;

import java.net.Socket;

public class Master extends AbstractConnection{
	
	private int uid;
	
	public Master(Socket socket, int id) {
		super(socket);
		uid = id;
		
	}

}
