package com.soen6441.battleship.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.soen6441.battleship.controller.Utils;
import com.soen6441.battleship.model.*;

public class UtilsTest {
  private final int shipSize = 5;
  private Player player;
  
  @Before
  public void init() {
    player = new Player("TestPlayer");
    int i=0;
    for (Utils.shipNames ship : Utils.shipNames.values()) {
      player.getShips()[i] =Utils.shipFactory.create(ship.getName(), i, 0, false);
      i++;
    }
  }

  @Test
  public void testIsGameOver() {
    System.out.println("testIsGameOver()");
    for (int i = 0; i < player.getShips().length; i++) {
      player.getShips()[i].setHits(shipSize);
      player.getShips()[i].setDestroyed(true);
    }
    assertTrue(Utils.isGameOver(player));
  }
  
  @Test
  public void testIsGameNotOver() {
    System.out.println("testIsGameNotOver()");
    assertFalse(Utils.isGameOver(player));
  }

  @Test
  public void testNumberOfShipsOn() {
    System.out.println("testNumberOfShipsOn()");
    assertEquals(player.getShips().length, Utils.numberOfShipsOn(player));
  }
}
