package com.soen6441.battleship.model.ships;

public class Carrier extends Ship {
  public Carrier(int row, int col, boolean vertical) {
    super(row, col, 5, vertical);
  }

  public String getType() {
    return "Carrier";
  }
}
