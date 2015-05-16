import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DomainObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	transient int a = 1;
	String b = "String is serializable";
	List<Object> objs = new ArrayList<>(5);
	
	public DomainObject() {
		for(int i=0; i<5; i++) {
			objs.add(new Object());
		}
	}
	
	public void setB(String para) {
		b = para;
	}
	
	public void print() {
		System.out.println("" + a + " " + b +  objs);
	}
}
