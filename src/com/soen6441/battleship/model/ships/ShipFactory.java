package com.soen6441.battleship.model.ships;

public class ShipFactory implements AbstractFactory<Ship> {
  @Override
  public Ship create(String shipType, int row, int col, boolean vertical) {
    if ("Carrier".equalsIgnoreCase(shipType)) {
      return new Carrier(row, col, vertical);
    } else if ("Battleship".equalsIgnoreCase(shipType)) {
      return new Battleship(row, col, vertical);
    } else if ("Cruiser".equalsIgnoreCase(shipType)) {
      return new Cruiser(row, col, vertical);
    } else if ("Submarine".equalsIgnoreCase(shipType)) {
      return new Submarine(row, col, vertical);
    } else if ("Destroyer".equalsIgnoreCase(shipType)) {
      return new Destroyer(row, col, vertical);
    }

    return null;
  }
}
