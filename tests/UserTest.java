import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class DuplicateIdCheckerTest {
    //Tests for user management logic and validation

@Test
@DisplayName("Should detect duplicate IDs for User Management")
    public void test_duplicate_user_id(){
    ArrayList<User> test_list = new ArrayList<>();
    User u1 = new User("U001");
    User u2 = new User("U002");
    test_list.add(u1);
    test_list.add(u2);
            //assertThrows ensures an exception is thrown when user attributes are invalid
assertThrows(DuplicateIdException.class, () ->{
    User.check_dup_user_id("U001", test_list);
});

}
@Test
@DisplayName("Should not throw an exception when no duplicate IDs exist")
    public void test_no_duplicate_user_id(){
        ArrayList<User> test_list = new ArrayList<>();
        User u1 = new User("U001");
        User u2 = new User("U002");
        test_list.add(u1);
        test_list.add(u2);
                //assertDoesNotThrow ensures no exception happens if user attributes are valid
        assertDoesNotThrow(() ->{
            User.check_dup_user_id("U003", test_list);
        });
}
@Test
    @DisplayName("Should throw an exception when email does not end with '@uoguelph.ca'")
    public void test_email_validator_invalid(){
        User u1 = new User("Tony", "123@gmail.com");
        assertThrows(InvalidEmailException.class, () ->{
            u1.validate_email(u1);
        });

    }
    @Test
    @DisplayName("Should not throw an exception when email ends with '@uoguelph.ca'")
    public void test_email_validator_valid(){
        User u1 = new User("Tony", "123@uoguelph.ca");
        assertDoesNotThrow(() ->{
            u1.validate_email(u1);
        });
    }
    @Test
    @DisplayName("Should throw an exception when name is less than 3 characters")
    public void test_name_validator_invalid(){
        User u1 = new User("To", "123@uoguelph.ca");
        assertThrows(InvalidNameException.class, () ->{
            u1.validate_name(u1);
        });
    }
    @Test
    @DisplayName("Should not throw an exception when name is 3 characters or longer")
    public void test_name_validator_valid(){
        User u1 = new User("Tony", "123@uoguelph.ca");
        assertDoesNotThrow(()->{
            u1.validate_name(u1);
        });
    }
}
