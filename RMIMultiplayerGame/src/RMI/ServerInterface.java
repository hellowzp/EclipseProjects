package RMI;

import java.rmi.*;


/**
 * Deze interface klasse verzorgt de interface van de server in multiplayer mode
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public interface ServerInterface extends Remote {
	
	public void login(ClientInterface client) throws RemoteException;
	
	public void broadcastConnected() throws RemoteException;

	public void updatePlayer(ClientInterface client,int x , int y,int hp) throws RemoteException ;
	
	public void playerWins(ClientInterface client) throws RemoteException;
}

	

