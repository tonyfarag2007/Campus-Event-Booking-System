public class DuplicateIdException extends RuntimeException{
    public DuplicateIdException(String new_id){
        super(String.format("ID: %s is already registered!", new_id));
    }
}
