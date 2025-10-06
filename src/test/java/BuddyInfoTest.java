import ca.carleton.sysc4806.damon.BuddyInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuddyInfoTest {
    @Test
    public void toStringFormatsNameAndPhone() {
        BuddyInfo b = new BuddyInfo("Alice", "123");
        assertEquals("Alice -- 123", b.toString());
    }

    @Test
    public void equalityByNameAndPhone() {
        BuddyInfo a = new BuddyInfo("Bob", "456");
        BuddyInfo b = new BuddyInfo("Bob", "456");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
