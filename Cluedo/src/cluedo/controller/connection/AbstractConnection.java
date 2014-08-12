package cluedo.controller.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class AbstractConnection {
	private final Socket socket;
	protected DataOutputStream output;
	protected DataInputStream input;
	
	public AbstractConnection(Socket socket){
		this.socket = socket;
		
		try{
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
		}catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	
	public DataOutputStream getOutput(){
		return output;
	}
	
	public DataInputStream getInput(){
		return input;
	}
	
	public boolean isClosed(){
		return socket.isClosed();
	}
	
	public void close() throws IOException{
		socket.close();
	}
}
