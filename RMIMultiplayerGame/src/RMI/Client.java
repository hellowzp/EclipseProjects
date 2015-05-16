package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import GameState.Multiplayer;

/**
 * Deze klasse initieert de client die een speler zal zijn van het multiplayer model via de
 * server
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

@SuppressWarnings("serial")
public class Client extends UnicastRemoteObject implements ClientInterface {

	public Client() throws RemoteException {
	}

	public void getConnection() throws RemoteException {
		Multiplayer.newPlayer();
	}
	
	public void getPlayer(int x , int y,int hp) throws RemoteException {
		Multiplayer.updatePlayer(x,y,hp);
	}
	
	public void winGame(String winner) throws RemoteException {
		Multiplayer.endGame(winner);
	}
}

