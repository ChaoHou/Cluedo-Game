package cluedo.controller.connection;

import java.io.IOException;
import java.net.Socket;

import cluedo.controller.ActionHandler;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.Action;
import cluedo.model.Board;

/**
 * Connection at slave side
 * Will enqueue action to the handler
 * @author C
 *
 */
public class MasterConnection extends AbstractConnection{
	
	public MasterConnection(Socket socket,int clock, int id) {
		super(socket,clock);
		uid = id;
		try {
			output.writeInt(uid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		if(handler == null) throw new RuntimeException("Require to set an action handler");
		
		while(!socket.isClosed()){
			try {
				if(input.available() != 0){
					int index = input.readInt();
					ActionType actionType = ActionType.values()[index];
					System.out.println("Server ActionType recieved: "+actionType);
					
					if(actionType.equals(ActionType.DISCONNECT)){
						
						int uid = input.readInt();
						if(uid == this.uid){
							System.out.println("Disconnect uid:"+uid);
							socket.close();
							break;
						}
						
					}
					
					Action action = ActionHelper.genServerAction(this,actionType);
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
