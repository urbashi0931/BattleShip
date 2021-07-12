package com.soen6441.battleship.model.ships;

public class Destroyer extends Ship {
  public Destroyer(int row, int col, boolean vertical) {
    super(row, col, 2, vertical);
  }

  public String getType() {
    return "Destroyer";
  }
}