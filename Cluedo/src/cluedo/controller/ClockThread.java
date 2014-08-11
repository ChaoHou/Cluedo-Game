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
		System.out.println("CLOCK RUNNING");
		while(1==1){
			try {
				if(!handler.isEmpty()){
					System.out.println("have action");
					Action action = handler.pollAction();
					action.execute();
				}
			
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
