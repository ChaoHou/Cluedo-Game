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
					Action action = handler.pollAction();
					action.execute();
					System.out.println("ACTION EXECUTED");
				}
			
				Thread.sleep(clock);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
