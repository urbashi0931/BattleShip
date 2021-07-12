package com.soen6441.battleship.model;

import com.soen6441.battleship.controller.Utils;
import com.soen6441.battleship.model.ships.Ship;

/**
 * Implements the data model for the Players
 * 
 * @author Urbashi Bhattacharjee
 * @author Harsh Patel
 * 
 */
public class Player {
  /**
   * Player's name
   */
  private String name;
  
  /**
   * Player's board
   */
  private String [][] board;
  
  /**
   * Player's ships
   */
  private Ship [] ships;  
  
  
  /**
   * Empty constructor
   */
  public Player() {

  }

  /**
   * Parameterized constructor
   * 
   * @param name Player's name
   */
  public Player(String name)
  {
    setName(name);    
    resetBoard();
    setShips(new Ship[5]);
  }
  
  /**
   * Resets the Player's board
   */
  public void resetBoard()
  {
    board=new String[10][10];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        board[i][j] = Utils.markEmpty;
      }
    }
  }
  
  /**
   * Gets the player name
   * @return Player's name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the player name
   * @param name Player's name
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Gets the player's board
   * @return Player's board
   */
  public String [][] getBoard() {
    return board;
  }
  
  /** 
   * Sets the player's board
   * @param board Player's board
   */
  public void setBoard(String [][] board) {
    this.board = board;
  }
  
  /**
   * Gets the players ships
   * @return Player's ships
   */
  public Ship [] getShips() {
    return ships;
  }
  
  /**
   * Sets the player's ships
   * @param ships Player's ships
   */
  public void setShips(Ship [] ships) {
    this.ships = ships;
  }
}
