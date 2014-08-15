package cluedo.controller.action.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cluedo.controller.Round;
import cluedo.controller.action.Action;
import cluedo.controller.connection.Master;
import cluedo.controller.connection.Slave;
import cluedo.model.Board;

public class Initialize implements Action{
	
	private Board game;
	private Round round;
	private int broadcastClock;
	private Master[] connections;
	private Slave connection;
	
	public Initialize(Master[] con, Board game, Round round, int clock) {
		connections = con;
		this.game = game;
		this.round = round;
		broadcastClock = clock;
	}
	
	public Initialize(Slave slave){
		connection = slave;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	
}
