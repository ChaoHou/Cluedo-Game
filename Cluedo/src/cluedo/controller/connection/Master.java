package cluedo.controller.connection;

import java.io.IOException;
import java.net.Socket;

import cluedo.controller.ActionHandler;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.Action;

public class Master extends AbstractConnection{
	
	private int uid;
	
	public Master(Socket socket,int clock, int id) {
		super(socket,clock);
		uid = id;
		
	}
	
	@Override
	public void run(){
		while(!socket.isClosed()){
			try {
				if(input.available() != 0){
					int index = input.readInt();
					ActionType actionType = ActionType.values()[index];
					Action action = ActionHelper.genServerAction(actionType, output, input);
					
					handler.offerAction(action);
				}
				
				Thread.sleep(boardcastClock);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int uid(){
		return uid;
	}
}
