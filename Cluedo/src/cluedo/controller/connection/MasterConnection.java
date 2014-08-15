package cluedo.controller.connection;

import java.io.IOException;
import java.net.Socket;

import cluedo.controller.ActionHandler;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.ActionHelper.ActionType;
import cluedo.controller.action.Action;
import cluedo.model.Board;

public class MasterConnection extends AbstractConnection{
	
	private int uid;
	private MasterConnection[] connections;
	private Board game;
	
	public MasterConnection(Socket socket,int clock, int id) {
		super(socket,clock);
		uid = id;
		
	}
	
	public void initialize(MasterConnection[] cons,Board board){
		connections = cons;
		game = board;
	}
	
	@Override
	public void run(){
		while(!socket.isClosed()){
			try {
				if(input.available() != 0){
					int index = input.readInt();
					ActionType actionType = ActionType.values()[index];
					Action action = ActionHelper.genServerAction(connections,this,game,actionType);
					
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
