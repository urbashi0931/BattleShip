package com.soen6441.battleship.model.ships;

/**
 * Implements the abstract data model for the Ships
 * 
 * @author Urbashi Bhattacharjee
 * @author Harsh patel
 * 
 */
public abstract class Ship {
  /**
   * Size of the ship
   */
  protected int size;

  /**
   * Starting row of the ship
   */
  protected int row;

  /**
   * Starting column of the ship
   */
  protected int col;

  /**
   * Checks whether the ship is vertical or horizontal
   */
  protected boolean vertical;

  /**
   * Number of hits received
   */
  protected int hits;

  /**
   * Checks if the ship was destroyed
   */
  protected boolean destroyed;

  /**
   * Parameterized constructor
   * 
   * @param row
   *          Starting row position
   * @param col
   *          Starting column position
   * @param size
   *          Size of the ship
   * @param vertical
   *          Vertical or horizontal
   */
  public Ship(int row, int col, int size, boolean vertical) {
    setDestroyed(false);
    setHits(0);

    this.setRow(row);
    this.setCol(col);
    this.setSize(size);
    this.setVertical(vertical);
  }

  public abstract String getType();
  
  /**
   * Gets the size
   * 
   * @return The size
   */
  public int getSize() {
    return size;
  }

  /**
   * Sets the size
   * 
   * @param size
   *          Size
   */
  public void setSize(int size) {
    this.size = size;
  }

  /**
   * Gets the row
   * 
   * @return Starting row position
   */
  public int getRow() {
    return row;
  }

  /**
   * Sets the starting row position
   * 
   * @param row Starting row position
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Gets the column
   * 
   * @return Starting column position
   */
  public int getCol() {
    return col;
  }

  /**
   * Sets the starting column position
   * 
   * @param col Starting column position
   */
  public void setCol(int col) {
    this.col = col;
  }

  /**
   * Checks if the ship is in vertical or horizontal position
   * 
   * @return true if vertical, false otherwise
   */
  public boolean isVertical() {
    return vertical;
  }

  /**
   * Sets the ship to vertical or horizontal
   * 
   * @param vertical true for vertical, otherwise horizontal
   */
  public void setVertical(boolean vertical) {
    this.vertical = vertical;
  }

  /**
   * Gets the hits
   * 
   * @return The number of hits
   */
  public int getHits() {
    return hits;
  }

  /**
   * Sets the hits
   * 
   * @param hits
   *          Number of hits
   */
  public void setHits(int hits) {
    this.hits = hits;
  }

  /**
   * Checks if the ship was destroyed
   * 
   * @return true if destroyed, false otherwise
   */
  public boolean isDestroyed() {
    return destroyed;
  }

  /**
   * Sets the ship destroyed status
   * 
   * @param destroyed
   *          true if destroyed, false otherwise
   */
  public void setDestroyed(boolean destroyed) {
    this.destroyed = destroyed;
  }
}
