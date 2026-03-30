import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class DuplicateIdCheckerTest {
@Test
@DisplayName("Should detect duplicate IDs for User Management")
    public void test_duplicate_user_id(){
    ArrayList<User> test_list = new ArrayList<>();
    User u1 = new User("U001");
    User u2 = new User("U002");
    test_list.add(u1);
    test_list.add(u2);
assertThrows(IllegalArgumentException.class, () ->{
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
        assertDoesNotThrow(() ->{
            User.check_dup_user_id("U003", test_list);
        });
}
}