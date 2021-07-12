package com.soen6441.battleship.model.ships;

public class Submarine extends Ship {
  public Submarine(int row, int col, boolean vertical) {
    super(row, col, 3, vertical);
  }

  public String getType() {
    return "Submarine";
  }
}