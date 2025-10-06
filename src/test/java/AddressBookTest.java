import ca.carleton.sysc4806.damon.*;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookTest {
    @Test
    public void addAndRemoveBuddy() {
        AddressBook book = new AddressBook();
        BuddyInfo alice = new BuddyInfo("Alice", "111");
        book.addBuddy(alice);
        assertTrue(book.getBuddies().contains(alice));
        assertTrue(book.removeBuddy(alice));
        assertTrue(!(book.getBuddies().contains(alice)));
    }
}
