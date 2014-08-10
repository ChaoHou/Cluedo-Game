package cluedo.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Slave extends Thread{
	private final Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	
	public Slave(Socket socket){
		this.socket = socket;
		
		try{
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
		}catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	
}
