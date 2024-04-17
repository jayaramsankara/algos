package com.jayas;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestMinReFuel 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestMinReFuel( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestMinReFuel.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {

        Map<Float,Float> availableFuellingStations = new HashMap<Float,Float>(5);
        availableFuellingStations.put(60f,100.6f);
        availableFuellingStations.put(70f,90.6f);
        availableFuellingStations.put(90f,116.3f);
        availableFuellingStations.put(40f,103.8f);
        availableFuellingStations.put(150f,101.4f);
        availableFuellingStations.put(180f,103.4f);
        availableFuellingStations.put(200f,116.4f);
        availableFuellingStations.put(320f,99.4f);
        availableFuellingStations.put(400f,111.4f);
        availableFuellingStations.put(470f,98.4f);

        MinReFuel minReFuel = new MinReFuel();

        Map<Float,Float> answer = minReFuel.fuelingPlan(40f,10f,500,availableFuellingStations);
        System.out.println(answer);
        assertTrue( answer.size() == 2 );
        assertEquals(7f, answer.get(70f));
        assertEquals(3f, answer.get(470f));

       
    }
}
