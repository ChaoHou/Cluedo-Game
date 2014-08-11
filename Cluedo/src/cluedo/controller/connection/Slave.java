package cluedo.controller.connection;

import java.net.Socket;

public class Slave extends AbstractConnection{

	public Slave(Socket socket){
		super(socket);
	}
}
