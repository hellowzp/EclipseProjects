package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Deze inteface klasse verzorgt de interface met bijbehorende methodes voor de client
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public interface ClientInterface extends Remote {
	
	public void getConnection() throws RemoteException;
	
	public void getPlayer(int x , int y,int hp) throws RemoteException ;
	
	public void winGame(String winner) throws RemoteException;
}
