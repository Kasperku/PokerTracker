package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Poker game added"); 
        d = Calendar.getInstance().getTime(); 
    }

    @Test
    public void testEvent() {
        assertEquals("Poker game added", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        String expected = d.toString() + "\n" + "Poker game added";
        assertEquals(expected, e.toString());
    }
}
