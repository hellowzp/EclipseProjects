package be.kuleuven.wang;

public class Star{
	private final double R = 20; 
	private double[] x = new double[10]; 
	private double[] y = new double[10]; 
	
	public Star() {
		double Radians = Math.toRadians(72.0);
		double r = R*(Math.sin(Radians/4))/(Math.sin(Radians/2));
		for(int i=0; i<5; i++) {			
			x[2*i]=-R*(Math.cos(Radians/4+i*Radians));
			y[2*i]=-R*(Math.sin(Radians/4+i*Radians));
		}
		for(int i=0; i<5; i++ ) {			
			x[2*i+1]=-r*(Math.cos(Radians/4+i*Radians+Radians/2));
			y[2*i+1]=-r*(Math.sin(Radians/4+i*Radians+Radians/2));
		}
	}
	
	public double[] getX() {
		return x;
	}
	
	public double[] getY() {
		return y;
	}	
	
}
