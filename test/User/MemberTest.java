package User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void getTotalValue() {
        Member m = new Member("Maria Jensen");
        assertEquals(36608.0, m.getTotalValue());
    }

    @Test
    void calculateGrowth() {
        Member m = new Member("Maria Jensen");
        assertEquals(36.608000000000004, m.calculateGrowth());
    }
}