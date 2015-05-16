//http://www.rgagnon.com/javadetails/java-0031.html
//http://www.vogella.com/tutorials/JavaAnnotations/article.html
//http://tutorials.jenkov.com/java-reflection/annotations.html

import java.lang.reflect.*;

public class TestReflect {

  public static void main(String[] args) throws Exception {
    TestReflect.invoke("Class1", "say", new Class[] {String.class, String.class},
           new Object[]
             {new String("Hello"), new String("World")});
    /*
      output :
         Hello World
    */
  }

  public static void invoke
     (String aClass, String aMethod, Class[] paramTypes, Object[] args)
     throws Exception {
    Class c = Class.forName(aClass);
    Method m = c.getDeclaredMethod(aMethod, paramTypes);
    Object i = c.newInstance();
    Object r = m.invoke(i, args);
  }
}

class Class1 {
  public void say( String s1, String s2) {
    System.out.println(s1 + " " + s2);
  }
}  