package com.teddyc28.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Game.
 */
public class GameTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GameTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testGame()
    {
        assertTrue( true );
    }
}
