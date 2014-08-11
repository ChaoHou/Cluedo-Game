package cluedo.controller;

import cluedo.controller.action.Action;

public class ClockThread extends Thread{
	
	private ActionHandler handler;
	private int clock;
	
	public ClockThread(ActionHandler actionHandler,int clock){
		handler = actionHandler;
		this.clock = clock;
	}
	
	public void run(){
		
		while(!handler.isEmpty()){
			Action action = handler.pollAction();
		}
	}
	
}
