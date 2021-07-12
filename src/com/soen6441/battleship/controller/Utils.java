package com.soen6441.battleship.controller;

import com.soen6441.battleship.model.Player;
import com.soen6441.battleship.model.ships.Ship;
import com.soen6441.battleship.model.ships.ShipFactory;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Implements a basic utility class
 * 
 * @author student1
 * @author student2
 */
public class Utils {
  /**
   * The color of a single square
   */
  public static final Color squareFillColor = Color.TRANSPARENT;

  /**
   * The border color of a single square
   */
  public static final Color squareForeColor = Color.DARKBLUE;
  
  /**
   * Represents the sizes from each of the five ships available
   * (0=carrier,1=battleship, 2=cruiser, 3=submarine, 4=destroyer)
   */
  public static int[] shipSizes = { 5, 4, 3, 3, 2 };
  
  /**
   * Stores the ship names in order (see 'shipSizes')
   */
  public enum shipNames
  {
      CAR("Carrier"),
      BAT("Battleship"),
      CRU("Cruiser"),
      SUB("Submarine"),
      DES("Destroyer");
   
      private String name;
   
      shipNames(String name) {
          this.name = name;
      }
   
      public String getName() {
          return name;
      }
  }
  
  /**
   * Factory to build the ships
   */
  public static final ShipFactory shipFactory = new ShipFactory();
  
  
  /**
   * List of pairs (name, score) to hold the top time scores
   */
  public static ObservableList<Pair<StringProperty, StringProperty>> scoreData = FXCollections.observableArrayList();

  /**
   * List of pairs (name, score) to hold the top time scores sorted from lowest to
   * highest time
   */
  public static SortedList<Pair<StringProperty, StringProperty>> sortedData = new SortedList<>(scoreData);
  
  /**
   * Represents an empty space on the board
   */
  public static final String markEmpty = "-";

  /**
   * Represents a hit on a ship on the board
   */
  public static final String markHit = "x";

  /**
   * Represents a miss on a ship on the board
   */
  public static final String markMiss = "o";
  
  /**
   * Return the name of a ship givens its integer index
   * 
   * @param type index
   * @return name of the ship
   */
  public static String getBoatName(int type) {
    switch (type) {
    case 0:
      return "Carrier";
    case 1:
      return "Battleship";
    case 2:
      return "Cruiser";
    case 3:
      return "Submarine";
    case 4:
      return "Destroyer";
    }

    return "";
  }
  
  /**
   * Display a dialog with a message
   * @param msg Message
   * @param showAndWait true=wait, false=no wait
   */
  public static void showInfo(String msg, boolean showAndWait) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("BattleshipFX - Info");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("file:media/BattleshipFX.png"));

    if (showAndWait) {
      alert.showAndWait();
    } else {
      alert.show();
    }
  }
  
  /**
   * Return the color of a ship givens its index
   * 
   * @param type index
   * @return name of the ship
   */
  public static Color getBoatColor(String type) {
    if (type.equals("0")) {
      return Color.SALMON;
    } else if (type.equals("1")) {
      return Color.LIGHTGREEN;
    } else if (type.equals("2")) {
      return Color.LIGHTSKYBLUE;
    } else if (type.equals("3")) {
      return Color.PLUM;
    } else if (type.equals("4")) {
      return Color.SANDYBROWN;
    }

    return Color.BLACK;
  }
  
  /**
   * Checks if all the ships of a Player are destroyed
   * 
   * @param p Player
   * @return true if game over, false otherwise
   */
  public static boolean isGameOver(Player p) {
    for (Ship s : p.getShips()) {
      if (!s.isDestroyed()) {
        return false;
      }
    }

    return true;
  }
  
  /**
   * Returns the number of active ships on a player's board
   * 
   * @param p Player
   * @return the number of active ships
   */
  public static int numberOfShipsOn(Player p) {
    int c = 0;
    for (Ship s : p.getShips()) {
      if (!s.isDestroyed()) {
        c++;
      }
    }

    return c;
  }
  
  /**
   * Prints the board of player
   * 
   * @param p Player
   */
  public static void printBoard(Player p) {
    System.out.println("***" + p.getName() + "***");
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        System.out.print(p.getBoard()[i][j] + " ");
      }
      System.out.println("");
    }
    System.out.println("");
  }
}
