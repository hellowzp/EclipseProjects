//http://www.journaldev.com/629/catching-multiple-exceptions-in-single-catch-and-rethrowing-exceptions-with-improved-type-checking-java-7-feature
//http://www.journaldev.com/592/try-with-resource-example-java-7-feature-for-automatic-resource-management
//http://www.journaldev.com/1696/java-exception-handling-tutorial-with-examples-and-best-practices

public class MultipleExceptions {
 
    public static void main(String[] args) {
        try{
            rethrow("abc");
        }catch(FirstException | SecondException | ThirdException e){
            //below assignment will throw compile time exception since e is final
            //e = new Exception();
            System.out.println(e.getMessage());
        }
    }
 
    static void rethrow(String s) throws FirstException, SecondException,
            ThirdException {
        try {
            if (s.equals("First"))
                throw new FirstException("First Exception");
            else if (s.equals("Second"))
                throw new SecondException("Second Exception");
            else
                throw new ThirdException("Third Exception");
        } catch (Exception e) {
            //below assignment disables the improved rethrow exception type checking feature of Java 7
            // e=new ThirdException();
            throw e;
        }
    }
 
    static class FirstException extends Exception {
 
        public FirstException(String msg) {
            super(msg);
        }
    }
 
    static class SecondException extends Exception {
 
        public SecondException(String msg) {
            super(msg);
        }
    }
 
    static class ThirdException extends Exception {
 
        public ThirdException(String msg) {
            super(msg);
        }
    }
 
}