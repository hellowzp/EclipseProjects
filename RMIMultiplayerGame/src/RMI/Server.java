package RMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Deze klasse zet de server op waar de spelers in multiplayer mode client van worden. Deze
 * verzorgt hier het spel
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject implements ServerInterface {
	
	protected ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
	
	public Server() throws RemoteException {
	}

	
	/**
	 * Deze methode zorgt voor een login van een client op de server
	 * 
	 * @param client de client die gebruik maakt van de server
	 */
	
	
	public void login(ClientInterface client) throws RemoteException {
			broadcastConnected();	
			clients.add(client);
	}
	
	public void broadcastConnected() throws RemoteException {
		
		for (int i = 0; i < clients.size(); i++) {
			ClientInterface c = clients.get(i);
		  	try {
				c.getConnection();
			} catch (RemoteException e) {
				logout(c);
				i = i - 1;
				} 
			}
		}
	
	/**
	 * Deze methode update de speler die een client van deze server is in het spel
	 * 
	 * @param client de client die geupdatete wordt
	 * @param x de xpositie van de speler
	 * @param y de ypositie van de speler
	 * @param hp de health van de speler
	 */
	
	public void updatePlayer(ClientInterface client,int x , int y,int hp) throws RemoteException {
		for (int i = 0; i < clients.size(); i++) {
			ClientInterface c = clients.get(i);
			if(c.equals(client) == false){
			try {
				c.getPlayer(x,y,hp);
			} catch (RemoteException e) {
				logout(c);
			} 
		}
		}
		
	}
	
	
	/**
	 * Deze methode zorgt ervoor dat een speler kan winnen in de multiplayer mode en geeft dit
	 * visueel aan
	 * 
	 * @param client de client die het spel wint
	 */
	
	public void playerWins(ClientInterface client) throws RemoteException{
		for (int i = 0; i < clients.size(); i++) {
			ClientInterface c = clients.get(i);
			if(c.equals(client)){
			try {
				c.winGame("Enemy Wins");
			} catch (RemoteException e) {
				logout(c);
			}  
		} else {
			try {
				c.winGame("You Win");
			} catch (RemoteException e) {
				logout(c);
			}  
			
			}
		}
	}
	
	
	public void logout(ClientInterface client) {
		clients.remove(client);
	}
	
	public static void main(String[] args) {
		try {
			Naming.rebind("Server", new Server());
			System.out.println("Server is ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


