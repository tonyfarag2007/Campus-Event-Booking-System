public class InvalidCapacityException extends RuntimeException {
    public InvalidCapacityException(String message) {
        super(String.format("Capacity must be greater than 0."));
    }
}
