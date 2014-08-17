package cluedo.controller.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import cluedo.controller.ActionHandler;

public abstract class AbstractConnection extends Thread{
	protected final Socket socket;
	protected int boardcastClock;
	protected ActionHandler handler;
	protected DataOutputStream output;
	protected DataInputStream input;
	
	protected int uid;
	
	public AbstractConnection(Socket socket,int clock){
		this.socket = socket;
		boardcastClock = clock;
		
		try{
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
		}catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	
	public void setActionHandler(ActionHandler aHandler){
		handler = aHandler;
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
	
	public int uid(){
		return uid;
	}
	
	public Socket getSocket(){
		return socket;
	}
}
