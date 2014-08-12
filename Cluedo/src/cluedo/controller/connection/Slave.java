package cluedo.controller.connection;

import java.io.IOException;
import java.net.Socket;

import cluedo.controller.ActionHandler;
import cluedo.controller.action.AbstractAction;
import cluedo.controller.action.Action;
import cluedo.controller.action.AbstractAction.ActionType;

public class Slave extends AbstractConnection{

	public Slave(Socket socket,int clock){
		super(socket,clock);
	}
	
	@Override
	public void run(){
		while(!socket.isClosed()){
			try {
				if(input.available() != 0){
					int index = input.readInt();
					ActionType actionType = ActionType.values()[index];
					Action action = AbstractAction.actionFromMaster(actionType, input);
					
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
}
