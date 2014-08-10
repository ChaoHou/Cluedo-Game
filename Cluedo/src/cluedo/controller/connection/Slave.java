package cluedo.controller.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Slave extends AbstractConnection{

	public Slave(Socket socket){
		super(socket);
	}
}
