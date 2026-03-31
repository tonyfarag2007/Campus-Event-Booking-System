public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String name){
        super(String.format("Name %s is not valid.", name));
    }
}
