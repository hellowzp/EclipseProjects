import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectSerialStream {
	public static void write(Object o, String filename) throws Exception{
        XMLEncoder encoder =
           new XMLEncoder(
              new BufferedOutputStream(
                new FileOutputStream(filename)));
        encoder.writeObject(o);
        encoder.close();
    }

    public static Object read(String filename) throws Exception {
        XMLDecoder decoder =
            new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)));
        Object o = decoder.readObject();
        decoder.close();
        return o;
    }
    
    public static void main(String[] args) {
    	List<DomainObject> objs = new ArrayList<>(3);
    	for(int i=0; i<3; i++) {
    		DomainObject obj = new DomainObject();
    		obj.setB("string"+i);
    		objs.add(obj);
    	}
    	
    	try {
			write(objs, "objs.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	for(int i=0; i<3; i++) {
    		try {
				write(objs.get(i), "obj" + i + ".xml");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	try {
			@SuppressWarnings("unchecked")
			List<DomainObject> objStream = (List<DomainObject>) read("objs.xml");
			for(DomainObject o : objStream)
				o.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
