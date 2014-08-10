package cluedo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cluedo.controller.ActionMaster;
import cluedo.controller.ClockThread;
import cluedo.controller.connection.Master;
import cluedo.controller.connection.Slave;

public class Main {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;

	public static void main(String[] args) {

		boolean server = false;
		int nplayers = 0;
		String url = null;
		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 32768;

		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if (arg.equals("-help")) {
					usage();
					System.exit(0);
				} else if (arg.equals("-server")) {
					server = true;
					nplayers = Integer.parseInt(args[++i]);
				} else if (arg.equals("-connect")) {
					url = args[++i];
				} else if (arg.equals("-clock")) {
					gameClock = Integer.parseInt(args[++i]);
				} else if (arg.equals("-port")) {
					port = Integer.parseInt(args[++i]);
				}
			} else {
				System.out.println("Wrong argument");
			}
		}

		// Sanity checks
		if (url != null && server) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		} else if (url != null && gameClock != DEFAULT_CLK_PERIOD) {
			System.out.println("Cannot overide clock period when connecting to server.");
			System.exit(1);
		} 
		
		// run game
		try{
			if(server){
				runServer(port, nplayers, gameClock, broadcastClock);
			}else{
				runClient(url,port);
			}
		}catch(IOException ioe){
			System.out.println("I/O error: " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(1);
		}
		
		System.exit(0);
	}

	private static void usage() {
		String[][] info = {
				{ "server <n>",
						"Run in server mode, awaiting n client connections" },
				{ "connect <url>", "Connect to server at <url>" },
				{ "clock", "Set clock period (default 20ms)" },
				{ "bclock", "Set broadcast clock period (default 5ms)" },
				{ "port", "Set port for use for connection (default 32768)" },
				{ "player <n>", "Set the number of players" }, };
		System.out.println("Usage: java com.cluedo.Main <options> ");
		System.out.println("Options:");

		// first, work out gap information
		int gap = 0;

		for (String[] p : info) {
			gap = Math.max(gap, p[0].length() + 5);
		}

		// now, print the information
		for (String[] p : info) {
			System.out.print("  -" + p[0]);
			int rest = gap - p[0].length();
			for (int i = 0; i != rest; ++i) {
				System.out.print(" ");
			}
			System.out.println(p[1]);
		}
	}
	
	private static void runClient(String addr, int port) throws IOException {		
		Socket s = new Socket(addr,port);
		System.out.println("PACMAN CLIENT CONNECTED TO " + addr + ":" + port);			
		new Slave(s);
		
	}
	
	private static void runServer(int port, int nplayers, int gameClock, int broadcastClock) {	
		//ClockThread clk = new ClockThread(gameClock);	
		
		// Listen for connections
		System.out.println("CLUEDO SERVER LISTENING ON PORT " + port);
		System.out.println("CLUEDO SERVER AWAITING " + nplayers + " PLAYERS");
		try {
			Master[] connections = new Master[nplayers];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);			
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				//int uid = game.registerPacman();
				int uid = 0;
				connections[--nplayers] = new Master(s,uid);
				//connections[nplayers].start();				
				if(nplayers == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					//multiUserGame(clk,game,connections);
					
					ActionMaster actionMaster = new ActionMaster(connections);
					ClockThread clk = new ClockThread(actionMaster,gameClock);
					clk.start();
					actionMaster.start();;
					
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 
	}
	
}
