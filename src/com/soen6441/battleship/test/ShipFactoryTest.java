/**
 * @author Urbashi
 * @author Harsh
 * 
 */
package com.soen6441.battleship.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.soen6441.battleship.controller.Utils;
import com.soen6441.battleship.model.ships.Ship;

public class ShipFactoryTest {
  @Test
  public void testCarrier() {
    Ship s = Utils.shipFactory.create("Carrier", 0, 0, true);

    assertEquals("Carrier", s.getType());
  }

  @Test
  public void testBattleship() {
    Ship s = Utils.shipFactory.create("Battleship", 0, 0, true);

    assertEquals("Battleship", s.getType());
  }

  @Test
  public void testCruiser() {
    Ship s = Utils.shipFactory.create("Cruiser", 0, 0, true);

    assertEquals("Cruiser", s.getType());
  }

  @Test
  public void testSubmarine() {
    Ship s = Utils.shipFactory.create("Submarine", 0, 0, true);

    assertEquals("Submarine", s.getType());
  }

  @Test
  public void testDestroyer() {
    Ship s = Utils.shipFactory.create("Destroyer", 0, 0, true);

    assertEquals("Destroyer", s.getType());
  }

  @Test
  public void testInvalidShip() {
    Ship s = Utils.shipFactory.create("Test", 0, 0, true);

    assertNull(s);
  }

}
