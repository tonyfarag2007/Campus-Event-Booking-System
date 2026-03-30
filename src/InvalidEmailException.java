public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super(String.format("Email %s is not valid.", email));
    }
}
