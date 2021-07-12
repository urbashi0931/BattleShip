package com.soen6441.battleship.model.ships;

public class Battleship extends Ship {
  public Battleship(int row, int col, boolean vertical) {
    super(row, col, 4, vertical);
  }

  public String getType() {
    return "Battleship";
  }
}