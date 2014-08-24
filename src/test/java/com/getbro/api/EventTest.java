package com.getbro.api;

import com.getbro.api.items.Event;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Calendar;

/**
 * Unit test for simple App.
 */
public class EventTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EventTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( EventTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testFromStringTags()
    {
        Event e = Event.fromString(2313213213L, "#hallo #du arsch");
        System.out.println(e.getTags());

        assertTrue( e.getTags().contains("hallo") );
        assertTrue( e.getTags().contains("du") );
    }

    public void testFromStringTagsNot()
    {
        Event e = Event.fromString(2313213213L, "#hallo #du arsch");

        assertFalse( e.getTags().contains("arsch") );
    }

    public void testFromStringDay()
    {
        Event e = Event.fromString(2313213213L, "#hallo #du arsch tomorrow");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);  // number of days to add

        assertEquals(e.getDay(),  c.get(Calendar.DAY_OF_MONTH));
    }

    /*

    public void testFromStringDayTime()
    {
        Event e = Event.fromString(2313213213L, "#hallo #du arsch tomorrow 18:00");

        assertEquals(18, e.getDatetime().get(Calendar.HOUR_OF_DAY));
    }
    */
}
