package cluedo.tests;

import static org.junit.Assert.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;
import org.junit.Before;

import cluedo.controller.ActionHandler;
import cluedo.controller.SlaveActionHandler;
import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.client.Notify;
import cluedo.controller.action.client.SlaveAction;
import cluedo.controller.connection.MasterConnection;
import cluedo.controller.connection.SlaveConnection;
import cluedo.model.Board;
import cluedo.model.Player;

public class SlaveTest {

	@Before
	public void setUpServer(){
		System.out.println("Set up the socket.");{
			Thread thread = new Thread(){
				public void run(){
					try {
						ServerSocket server = new ServerSocket(32768);
						
						//accept single user
						int uid = 0;
						Socket socket = server.accept();
						System.out.println("Server connection accepted");
						
						MasterConnection[] masters = {new MasterConnection(socket,10,uid)};
						
						ArrayList<Player> players = new ArrayList<Player>();
						players.add(new Player(uid));
						Board game = new Board(players);
						
						//TODO:bug in board is not fixed
						//broadcast the board state
						//ActionHelper.broadcast(masters, game);
						
						//disconnect the slave
						ActionHelper.requestDisconnect(socket);
						
						socket.close();
						
						server.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		}
	}
	
	
	@Test
	public void testInvalidSlave(){
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1",32768);
			SlaveConnection slave = new SlaveConnection(socket,10);
			slave.run();
		} catch (UnknownHostException e) {
			fail();
		} catch (IOException e) {
			fail();
		} catch (RuntimeException e){
			//should throw a runtime exception since there is no action handler
		} finally{
			try {
				System.out.println("Close the socket");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testValidSlaveDisconnect(){
		try {
			Socket socket = new Socket("127.0.0.1",32768);
			SlaveConnection slave = new SlaveConnection(socket,10);
			TestSlaveActionHandler handler = new TestSlaveActionHandler(slave, 10);
			slave.setActionHandler(handler);
			slave.run();

			//Action action1 = handler.pollAction();
			
			//assert(action1 instanceof Notify);
			
			assert(slave.isClosed()):"Socket should be closed";
			
		} catch (UnknownHostException e) {
			fail();
		} catch (IOException e) {
			fail();
		} catch (RuntimeException e){
			fail();
		}
	}
	
	public class TestSlaveActionHandler extends Thread implements ActionHandler{

		private SlaveConnection connection;
		private int clock;
		private Queue<SlaveAction> actionQueue = new ConcurrentLinkedQueue<SlaveAction>();
		
		public TestSlaveActionHandler(SlaveConnection slave,int c){
			connection = slave;
			clock = c;
		}
		
		@Override
		public void run(){

		}
		
		@Override
		public boolean isEmpty() {
			return actionQueue.isEmpty();
		}

		@Override
		public Action pollAction() {
			return actionQueue.poll();
		}

		@Override
		public void offerAction(Action action) {
			actionQueue.offer((SlaveAction) action);
		}
		
	}
	
}
