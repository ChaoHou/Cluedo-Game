package cluedo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.omg.CORBA.INITIALIZE;

import cluedo.controller.action.ActionHelper;
import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Card;
import cluedo.model.Player;

public class Round {
	
	public enum State{
		INITIALIZING,
		WAITING,
		STARTED,
		FINISHING,
	}
	
	private Random random = new Random();
	
	private MasterConnection[] connections;
	
	//private MasterConnection playerInTurn;
	
	private State status = State.INITIALIZING;
	
	public Round(MasterConnection[] con){
		connections = con;
	}
	
	public void tick(Board game){
		if(status.equals(State.INITIALIZING)){
			for(MasterConnection connection:connections){
				try {
					Player player = game.getPlayer(connection.uid());
					
					if(player.getStatus().equals(Player.STATUS.INITIALIZING)){
						return;
					}

				} catch (IllegalRequestException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("All the players finished Initialization");
			//all the player finished initialize, 
			//initialize the solution, deal cards, 
			//select one player to start the turn
			//change the state of round to INTURN
			
			initCards(game);
			
			//broadcast the board state in order to start the game
		}else if(status.equals(State.STARTED)){
			//check all player's status check if next to refute is the player in turn,
			//start the next player's turn
			
			//check if all the player is watching or eliminated, means this round is finished
			//if all the player is eliminated, stop the game,
		}
		
		ActionHelper.broadcast(connections, game);
	}
	
	private void initCards(Board game){
		Card.CHARACTER sCharacter = Card.CHARACTER.values()[random.nextInt(Card.CHARACTER.values().length)];
		Card.WEAPON sWeapon = Card.WEAPON.values()[random.nextInt(Card.WEAPON.values().length)];
		Card.ROOM sRoom = Card.ROOM.values()[random.nextInt(Card.ROOM.values().length)];
		
		Card[] solution = {
				new Card(Card.TYPE.CHARCTER,sCharacter.toString()),
				new Card(Card.TYPE.WEAPON,sWeapon.toString()),
				new Card(Card.TYPE.ROOM,sRoom.toString()),
		};
		
		game.setSolution(solution);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card.CHARACTER chara:Card.CHARACTER.values()){
			if(!chara.equals(sCharacter)){
				cards.add(new Card(Card.TYPE.CHARCTER,chara.toString()));
			}
		}
		for(Card.ROOM room:Card.ROOM.values()){
			if(!room.equals(sRoom)){
				cards.add(new Card(Card.TYPE.ROOM,room.toString()));
			}
		}
		for(Card.WEAPON weap:Card.WEAPON.values()){
			if(!weap.equals(sWeapon)){
				cards.add(new Card(Card.TYPE.WEAPON,weap.toString()));
			}
		}
		assert(cards.size() == (Card.ROOM.values().length+Card.CHARACTER.values().length+Card.WEAPON.values().length-3));
		
		Collections.shuffle(cards);
		
		//deal the rest of cards to players
		ArrayList<Player> players = game.getPlayers();
		while(!cards.isEmpty()){
			for(Player player:players){
				player.getCards().add(cards.remove(0));
				if(cards.isEmpty()){
					break;
				}
			}
		
		}
		
		assert(cards.isEmpty());
		
		//select a random player to start
		randomStartPlayer(players);
	}

	private void randomStartPlayer(ArrayList<Player> players){
		int index = random.nextInt(players.size());
		players.get(index).setStatus(Player.STATUS.ROLLING);
	}
}
