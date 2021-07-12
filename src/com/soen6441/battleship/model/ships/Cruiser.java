package com.soen6441.battleship.model.ships;

public class Cruiser extends Ship {
  public Cruiser(int row, int col, boolean vertical) {
    super(row, col, 3, vertical);
  }

  public String getType() {
    return "Cruiser";
  }
}
