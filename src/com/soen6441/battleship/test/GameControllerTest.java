package com.soen6441.battleship.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.soen6441.battleship.controller.*;
import com.soen6441.battleship.model.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

public class GameControllerTest {
  private Player player1, player2;
  private int squaresEmpty;
  
  @Before
  public void init() {
    player1 = new Player("TestPlayer1");
    player2 = new Player("TestPlayer2");
    squaresEmpty=player1.getBoard()[0].length*player1.getBoard()[1].length;
    int i=0;
    for (Utils.shipNames ship : Utils.shipNames.values()) {
      player1.getShips()[i] =Utils.shipFactory.create(ship.getName(), i, 0, false);
      player2.getShips()[i] =Utils.shipFactory.create(ship.getName(), i, 1, false);
      i++;
    }
  }

  @Test
  public void testPlayer1BoardClear() {
    System.out.println("testPlayer1BoardClear()");

    int countEmpty=0;
    for (int i = 0; i < player1.getBoard()[0].length; i++) {
      for (int j = 0; j < player1.getBoard()[1].length; j++) {
        if(player1.getBoard()[i][j].equals(Utils.markEmpty))
        {
          countEmpty++;
        }
      }
    }
    
    assertEquals(squaresEmpty, countEmpty);    
  }
  
  @Test
  public void testPlayer2BoardClear() {
    System.out.println("testPlayer2BoardClear()");

    int countEmpty=0;
    for (int i = 0; i < player2.getBoard()[0].length; i++) {
      for (int j = 0; j < player2.getBoard()[1].length; j++) {
        if(player2.getBoard()[i][j].equals(Utils.markEmpty))
        {
          countEmpty++;
        }
      }
    }
    
    assertEquals(squaresEmpty, countEmpty);    
  }
  
  @Test
  public void testPositionsPlayer1ValidHit()
  {
    System.out.println("testPositionsValidHit()");
    player1.getBoard()[0][0]="x";
    boolean result=GameController.positionsValid(player1, 0, 0, player1.getShips()[0].getSize(), player1.getShips()[0].isVertical(), "0");
    assertFalse(result);   
  }
  
  @Test
  public void testPositionsPlayer2ValidHit()
  {
    System.out.println("testPositionsValidHit()");
    player2.getBoard()[0][0]="x";
    boolean result=GameController.positionsValid(player2, 0, 0, player2.getShips()[0].getSize(), player2.getShips()[0].isVertical(), "0");
    assertFalse(result);   
  }
  
  @Test
  public void testPositionsPlayer1ValidMiss()
  {
    System.out.println("testPositionsValidMiss()");
    player1.getBoard()[0][0]="o";
    boolean result=GameController.positionsValid(player1, 0, 0, player1.getShips()[0].getSize(), player1.getShips()[0].isVertical(), "0");
    assertFalse(result);   
  }
  
  @Test
  public void testPositionsPlayer2ValidMiss()
  {
    System.out.println("testPositionsValidMiss()");
    player2.getBoard()[0][0]="o";
    boolean result=GameController.positionsValid(player2, 0, 0, player2.getShips()[0].getSize(), player2.getShips()[0].isVertical(), "0");
    assertFalse(result);   
  }
  
  @Test
  public void testPlayer1PlaceShips() {
    System.out.println("testPlayer1PlaceShips()");
    
    GameController.placeShips(player1);
    int countEmpty=0;
    for (int i = 0; i < player1.getBoard()[0].length; i++) {
      for (int j = 0; j < player1.getBoard()[1].length; j++) {
        if(player1.getBoard()[i][j].equals(Utils.markEmpty))
        {
          countEmpty++;
        }
      }
    }    
    int squaresOccupied = Arrays.stream(Utils.shipSizes).sum();
    
    assertEquals(squaresEmpty-countEmpty, squaresOccupied);    
  }
  
  @Test
  public void testPlayer2PlaceShips() {
    System.out.println("testPlayer2PlaceShips()");
    
    GameController.placeShips(player2);
    int countEmpty=0;
    for (int i = 0; i < player2.getBoard()[0].length; i++) {
      for (int j = 0; j < player2.getBoard()[1].length; j++) {
        if(player2.getBoard()[i][j].equals(Utils.markEmpty))
        {
          countEmpty++;
        }
      }
    }    
    int squaresOccupied = Arrays.stream(Utils.shipSizes).sum();
    
    assertEquals(squaresEmpty-countEmpty, squaresOccupied);    
  }
  
  @Test
  public void testPlayPlayer1Hit()
  {
    player2.getBoard()[0][0]="0"; //Ship id 0 is placed on (0,0)
    GameController.play(0, 0, player1, player2);
    assertEquals(Utils.markHit, player2.getBoard()[0][0]);   
  }
  
  @Test
  public void testPlayPlayer1Miss()
  {
    GameController.play(0, 0, player1, player2);
    assertEquals(Utils.markMiss, player2.getBoard()[0][0]);   
  }
   
  @Test
  public void testPlayPlayer2Hit()
  {
    player1.getBoard()[0][0]="0"; //Ship id 0 is placed on (0,0)
    GameController.play(0, 0, player2, player1);
    assertEquals(Utils.markHit, player1.getBoard()[0][0]);   
  }
  
  @Test
  public void testPlayPlayer2Miss()
  {
    GameController.play(0, 0, player2, player1);
    assertEquals(Utils.markMiss, player1.getBoard()[0][0]);   
  }
  
  @Test
  public void testScoreAdd()
  {
    GameController.addScore("TestPlayer", "00:18:18");
    assertEquals(1,Utils.scoreData.size());
  }
  
  @Test
  public void testScoreElementPresent()
  {
    Pair<StringProperty,StringProperty> player=new Pair<StringProperty, StringProperty>(new SimpleStringProperty("TestPlayer"),
        new SimpleStringProperty("00:18:18"));
    Utils.scoreData.add(player);
    assertTrue(Utils.scoreData.contains(player));
  }

}
