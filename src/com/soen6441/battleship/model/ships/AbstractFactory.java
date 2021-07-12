package com.soen6441.battleship.model.ships;

public interface AbstractFactory<T> {
  T create(String shipType, int row, int col, boolean vertical) ;
}
