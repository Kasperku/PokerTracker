package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EventLog class.
 */
public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void loadEvents() {
        e1 = new Event("Poker game added");
        e2 = new Event("Poker game edited");
        e3 = new Event("Poker game deleted");
        EventLog el = EventLog.getInstance();
        el.clear(); 
        el.logEvent(e1);
        el.logEvent(e2);
        el.logEvent(e3);
    }

    @Test
    public void testLogEvent() {
        List<Event> events = new ArrayList<>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            events.add(next);
        }

        assertTrue(events.contains(e1));
        assertTrue(events.contains(e2));
        assertTrue(events.contains(e3));
    }

	@Test
	public void testClear() {
		EventLog el = EventLog.getInstance();
		el.clear();
		Iterator<Event> itr = el.iterator();
		assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
		assertEquals("Event log cleared.", itr.next().getDescription());
		assertFalse(itr.hasNext());
	}
}
