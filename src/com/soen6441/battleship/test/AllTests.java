package com.soen6441.battleship.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    UtilsTest.class,
    PlayerTest.class,
    ShipFactoryTest.class,
    GameControllerTest.class
})
public class AllTests {
 
}