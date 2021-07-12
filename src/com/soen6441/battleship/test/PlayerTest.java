/**
 * @author Urbashi
 * @author Harsh
 */
package com.soen6441.battleship.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.soen6441.battleship.model.Player;

public class PlayerTest {
  private Player player;
  
  @Before
  public void init() {
    player = new Player("TestPlayer");
  }
  
  @Test
  public void testBoard() {
    assertNotNull(player.getBoard());
  }

  @Test
  public void testShips() {
    assertNotNull(player.getShips());
  }
}
