package cluedo.controller.connection;

import java.io.IOException;
import java.net.Socket;

import cluedo.controller.ActionHandler;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.client.Notify;

public class SlaveConnection extends AbstractConnection{

	public SlaveConnection(Socket socket,int clock){
		super(socket,clock);
		try {
			uid = input.readInt();
			System.out.println("Slave UID: "+uid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(!socket.isClosed()){
			try {
				if(input.available() != 0){
					int index = input.readInt();
					ActionType actionType = ActionType.values()[index];
					
					assert(actionType.equals(ActionType.NOTIFY));
					
					System.out.println("Slave Action offered");
					
					handler.offerAction(new Notify(input));
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
