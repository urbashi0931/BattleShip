package com.soen6441.battleship.controller;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import com.soen6441.battleship.model.Player;
import com.soen6441.battleship.model.ships.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Implements the controller for the game
 * 
 * @author student1
 * @author student2
 */
public class GameController implements Initializable {
  /**
   * Checks if the game is in setup mode (ship placement and setup) or in playing
   * mode
   */
  boolean gameMode = false;

  /**
   * Represents the main player at the bottom screen
   */
  Player player1;

  /**
   * Represents the opponent player (A.I. or secondary) at the top screen
   */
  Player player2;

  /**
   * AnchorPane container of the scene
   */
  @FXML
  private AnchorPane anchor;

  /**
   * Bottom grid container for Player1
   */
  @FXML
  private GridPane gridPlayer1;

  /**
   * Top grid container for Player2
   */
  @FXML
  private GridPane gridPlayer2;

  /**
   * Button to start and reset the game
   */
  @FXML
  private Button bttnStartGame;

  /**
   * Bottom window label to print out status messages
   */
  @FXML
  private Label lblStatus;

  /**
   * Bottom label to hold the timer info for Player1
   */
  @FXML
  private Label lblTimer1;

  /**
   * Top label to hold the timer info for Player2
   */
  @FXML
  private Label lblTimer2;

  /**
   * CheckBox to choose between normal mode or salvo mode for the game
   */
  @FXML
  private CheckBox chkSalvoMode;

  /**
   * Label to indicate the setup area of the AI difficulty levels
   */
  @FXML
  private Label lblAILevel;

  /**
   * ComboBox to choose the level of difficulty of the A.I. (easy, normal and
   * hard)
   */
  @FXML
  private ComboBox<String> cmbAILevel;

  /**
   * Label to indicate the setup area of 2-player mode
   */
  @FXML
  private Label lblSetupPlayer;

  /**
   * ComboBox to choose which ship's player are being set on the board
   */
  @FXML
  private ComboBox<String> cmbSetupPlayer;

  /**
   * Label to indicate the config area
   */
  @FXML
  private Label lblConfig;

  /**
   * Bottom label to indicate the number of shots of Player1 in salvo mode
   */
  @FXML
  private Label lblShotsPlayer1;

  /**
   * Top label to indicate the number of shots of Player2 in salvo mode
   */
  @FXML
  private Label lblShotsPlayer2;

  /**
   * Label to describe the use of the mouse controls when setting the ships on the
   * board
   */
  @FXML
  private Label lblMouseHelp;

  /**
   * Single player mode menu item
   */
  @FXML
  CheckMenuItem chkmenui1Player;

  /**
   * 2-player mode menu item
   */
  @FXML
  CheckMenuItem chkmenui2Player;

  /**
   * Bottom TexField to hold the name of Player1
   */
  @FXML
  TextField txtPlayer1;

  /**
   * Top TexField to hold the name of Player2
   */
  @FXML
  TextField txtPlayer2;

  /**
   * Table to hold the scores
   */
  @FXML
  private TableView<Pair<StringProperty, StringProperty>> tviewScores;

  /**
   * First column of table to hold the scores (player name)
   */
  @FXML
  private TableColumn<Pair<StringProperty, StringProperty>, String> tcolumnPlayer;

  /**
   * Second column of table to hold the scores (score name)
   */
  @FXML
  private TableColumn<Pair<StringProperty, StringProperty>, String> tcolumnScore;


  /**
   * Timer for Player1
   */
  private Timeline timer1;

  /**
   * Timer for Player2
   */
  private Timeline timer2;

  /**
   * Timeline to animate the A.I. plays in salvo mode
   */
  private Timeline playerAISalvoPlays;

  /**
   * Counter for timer of Player1
   */
  private Integer startTime1;

  /**
   * Counter for timer of Player2
   */
  private Integer startTime2;

  /**
   * Checks the turn of either Player1 or Player2
   */
  private boolean turnPlayer1;

  /**
   * Number of shots made by the active player in salvo mode
   */
  private int salvoShots;

  /**
   * Number of current random moves made by the A.I.
   */
  private int aiRandom;

  /**
   * Maximum number of random moves to be made by the A.I.
   */
  private int aiRandomMoves;

  /**
   * Checks if the game is over
   */
  private static boolean gameOver;

  /**
   * Sound played when setting the ships
   */
  Media soundClick;

  /**
   * Sound played when making a play
   */
  Media soundMove;

  /**
   * Sound played when a ship is shot down
   */
  static Media soundExplosion;

  /**
   * Sound played when Player1 wins
   */
  static Media soundWin;

  /**
   * Sound played when Player1 loses
   */
  static Media soundLose;

  /**
   * MediaPlayer to reproduce sounds
   */
  static MediaPlayer mediaPlayerClick;

  /**
   * Checks if the mouse was clicked when placing the ships
   */
  boolean clicked;

  /**
   * Checks if the mouse was dragged when placing the ships
   */
  boolean dragged;

  /**
   * Initial row clicked when placing a ship
   */
  int startRow;

  /**
   * Initial col clicked when placing a ship
   */
  int startCol;
 
 
  /**
   * Initializes the Game stage
   */
  public void initialize(URL url, ResourceBundle rb) {
    init(); 
  }

  /**
   * Initializes the game
   */
  public void init() {
    System.out.println("GameController: init()");
    player1 = new Player("You");
    txtPlayer1.setText(player1.getName());
    //
    player2 = new Player("Opponent");
    txtPlayer2.setText(player2.getName());
    cmbAILevel.getItems().addAll("Easy", "Medium", "Hard");
    cmbAILevel.getSelectionModel().select("Easy");

    cmbSetupPlayer.getItems().addAll(player1.getName(), player2.getName());
    cmbSetupPlayer.getSelectionModel().select(player1.getName());

    lblStatus.setText("Ready");

    lblShotsPlayer1.setVisible(false);
    lblShotsPlayer2.setVisible(false);

    soundClick = new Media(new File("./media/a_click.wav").toURI().toString());
    soundMove = new Media(new File("./media/a_move.wav").toURI().toString());
    soundExplosion = new Media(new File("./media/a_explosion.wav").toURI().toString());
    soundWin = new Media(new File("./media/a_win.mp3").toURI().toString());
    soundLose = new Media(new File("./media/a_lose.wav").toURI().toString());
    
    initTimers();
    initScoreBoard();
    initModes();
  }

  /***
   * Initializes the single player and 2-player modes
   */
  void initModes() {
    System.out.println("GameController: initMode()");
    // show single player mode
    chkmenui1Player.setSelected(true);
    lblAILevel.setVisible(true);
    cmbAILevel.setVisible(true);

    lblSetupPlayer.setVisible(false);
    cmbSetupPlayer.setVisible(false);

    reset();

    // create the events for the menu items
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        boolean b1Player = ((CheckMenuItem) e.getSource()).getText().equals(chkmenui1Player.getText());
        // show/hides the check mark of the menu item
        chkmenui1Player.setSelected(b1Player);
        chkmenui2Player.setSelected(!b1Player);

        lblAILevel.setVisible(b1Player);
        cmbAILevel.setVisible(b1Player);

        lblSetupPlayer.setVisible(!b1Player);
        cmbSetupPlayer.setVisible(!b1Player);

        // reset the game depending on the selection made
        reset();
      }
    };
    chkmenui1Player.setOnAction(event);
    chkmenui2Player.setOnAction(event);
  }

  /**
   * Initializes the table for the top time scores
   */
  @SuppressWarnings("unchecked")
  void initScoreBoard() {
    System.out.println("GameController: initScoreBoard()");

    // add some default scores
    Utils.scoreData.add(new Pair<StringProperty, StringProperty>(new SimpleStringProperty("ben"),
        new SimpleStringProperty("00:66:33")));
    Utils.scoreData.add(new Pair<StringProperty, StringProperty>(new SimpleStringProperty("rey"),
        new SimpleStringProperty("00:30:10")));
    Utils.scoreData.add(new Pair<StringProperty, StringProperty>(new SimpleStringProperty("luke"),
        new SimpleStringProperty("00:10:10")));
    Utils.scoreData.add(new Pair<StringProperty, StringProperty>(new SimpleStringProperty("han"),
        new SimpleStringProperty("00:20:10")));
    Utils.scoreData.add(new Pair<StringProperty, StringProperty>(new SimpleStringProperty("leia"),
        new SimpleStringProperty("00:15:10")));

    tcolumnPlayer.setCellValueFactory(cellData -> cellData.getValue().getKey());
    tcolumnScore.setCellValueFactory(cellData -> cellData.getValue().getValue());

    Utils.sortedData = new SortedList<>(Utils.scoreData);
    Utils.sortedData.comparatorProperty().bind(tviewScores.comparatorProperty());

    tviewScores.setItems(Utils.sortedData);
    tviewScores.getSortOrder().addAll(tcolumnScore);
  }

  /**
   * Initialize the timers for each player
   */
  void initTimers() {
    timer1 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
      int hours = startTime1 / 3600;
      int minutes = (startTime1 % 3600) / 60;
      int seconds = startTime1 % 60;
      lblTimer1.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
      startTime1++;
    }), new KeyFrame(Duration.seconds(1)));
    timer1.setCycleCount(Animation.INDEFINITE);

    timer2 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
      int hours = startTime2 / 3600;
      int minutes = (startTime2 % 3600) / 60;
      int seconds = startTime2 % 60;
      lblTimer2.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
      startTime2++;
    }), new KeyFrame(Duration.seconds(1)));
    timer2.setCycleCount(Animation.INDEFINITE);
  }

  /**
   * Checks if a position in the board is valid to place a ship, taking into
   * account that no ship can be placed next to another
   * 
   * @param p
   *          player to have the board checked
   * @param row
   *          starting row
   * @param col
   *          starting column
   * @param size
   *          size of the ship
   * @param vertical
   *          checks if the ship should be placed vertically or horizontally
   * @param type
   *          type of ship (0=carrier, 1=battleship, 2=cruiser, 3=submarine,
   *          4=destroyer)
   * @return true if valid, false otherwise
   */
  public static boolean positionsValid(Player p, int row, int col, int size, boolean vertical, String type) {
    System.out.print("positionsValid(" + row + "," + col + ");size=" + size + ":");
    if (vertical) {
      System.out.println("vertical;");
      if (row + size > 10) {
        return false;
      }

      // check the row before the ship
      if (row - 1 >= 0) {
        int y = row - 1;
        if (col - 1 >= 0) {
          if (col + 1 <= 9) // checking both sides
          {

            if ((!p.getBoard()[y][col - 1].equals(Utils.markEmpty) && !p.getBoard()[y][col - 1].equals(type))
                || (!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))
                || (!p.getBoard()[y][col + 1].equals(Utils.markEmpty) && !p.getBoard()[y][col + 1].equals(type))) {
              return false;
            }
          } else // checking left
          {
            if ((!p.getBoard()[y][col - 1].equals(Utils.markEmpty) && !p.getBoard()[y][col - 1].equals(type))
                || (!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))) {
              return false;
            }
          }
        } else // checking right
        {
          if ((!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))
              || (!p.getBoard()[y][col + 1].equals(Utils.markEmpty) && !p.getBoard()[y][col + 1].equals(type))) {
            return false;
          }
        }
      }

      // check row after the ship
      if (row + size <= 9) {
        int y = row + size;
        if (col - 1 >= 0) {
          if (col + 1 <= 9) // checking both sides
          {

            if ((!p.getBoard()[y][col - 1].equals(Utils.markEmpty) && !p.getBoard()[y][col - 1].equals(type))
                || (!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))
                || (!p.getBoard()[y][col + 1].equals(Utils.markEmpty) && !p.getBoard()[y][col + 1].equals(type))) {
              return false;
            }
          } else // checking left
          {
            if ((!p.getBoard()[y][col - 1].equals(Utils.markEmpty) && !p.getBoard()[y][col - 1].equals(type))
                || (!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))) {
              return false;
            }
          }
        } else // checking right
        {
          if ((!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))
              || (!p.getBoard()[y][col + 1].equals(Utils.markEmpty) && !p.getBoard()[y][col + 1].equals(type))) {
            return false;
          }
        }
      }

      // check all rows from start to end of the ship
      for (int y = row; y < size + row; y++) {
        if (col - 1 >= 0) {
          if (col + 1 <= 9) {// checking both sides
            if ((!p.getBoard()[y][col - 1].equals(Utils.markEmpty) && !p.getBoard()[y][col - 1].equals(type))
                || (!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))
                || (!p.getBoard()[y][col + 1].equals(Utils.markEmpty) && !p.getBoard()[y][col + 1].equals(type))) {
              return false;
            }
          } else {// checking left
            if ((!p.getBoard()[y][col - 1].equals(Utils.markEmpty) && !p.getBoard()[y][col - 1].equals(type))
                || (!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))) {
              return false;
            }
          }
        } else {// checking right
          if ((!p.getBoard()[y][col].equals(Utils.markEmpty) && !p.getBoard()[y][col].equals(type))
              || (!p.getBoard()[y][col + 1].equals(Utils.markEmpty) && !p.getBoard()[y][col + 1].equals(type))) {
            return false;
          }
        }
      }
    } else {
      System.out.println("horizontal;");

      if (col + size > 10) {
        return false;
      }

      // check the column before ship
      if (col - 1 >= 0) {
        int x = col - 1;
        if (row - 1 >= 0) {
          if (row + 1 <= 9) // checking both sides
          {

            if ((!p.getBoard()[row - 1][x].equals(Utils.markEmpty) && !p.getBoard()[row - 1][x].equals(type))
                || (!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))
                || (!p.getBoard()[row + 1][x].equals(Utils.markEmpty) && !p.getBoard()[row + 1][x].equals(type))) {
              return false;
            }
          } else // checking top
          {
            if ((!p.getBoard()[row - 1][x].equals(Utils.markEmpty) && !p.getBoard()[row - 1][x].equals(type))
                || (!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))) {
              return false;
            }
          }
        } else // checking bottom
        {
          if ((!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))
              || (!p.getBoard()[row + 1][x].equals(Utils.markEmpty) && !p.getBoard()[row + 1][x].equals(type))) {
            return false;
          }
        }
      }

      // check the column after ship
      if (col + size <= 9) {
        int x = col + size;
        if (row - 1 >= 0) {
          if (row + 1 <= 9) // checking both sides
          {

            if ((!p.getBoard()[row - 1][x].equals(Utils.markEmpty) && !p.getBoard()[row - 1][x].equals(type))
                || (!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))
                || (!p.getBoard()[row + 1][x].equals(Utils.markEmpty) && !p.getBoard()[row + 1][x].equals(type))) {
              return false;
            }
          } else // checking top
          {
            if ((!p.getBoard()[row - 1][x].equals(Utils.markEmpty) && !p.getBoard()[row - 1][x].equals(type))
                || (!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))) {
              return false;
            }
          }
        } else // checking bottom
        {
          if ((!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))
              || (!p.getBoard()[row + 1][x].equals(Utils.markEmpty) && !p.getBoard()[row + 1][x].equals(type))) {
            return false;
          }
        }
      }

      // check all columns from start to end of the ship
      for (int x = col; x < size + col; x++) {
        if (row - 1 >= 0) {
          if (row + 1 <= 9) {// checking both sides
            if ((!p.getBoard()[row - 1][x].equals(Utils.markEmpty) && !p.getBoard()[row - 1][x].equals(type))
                || (!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))
                || (!p.getBoard()[row + 1][x].equals(Utils.markEmpty) && !p.getBoard()[row + 1][x].equals(type))) {
              return false;
            }
          } else {// checking top
            if ((!p.getBoard()[row - 1][x].equals(Utils.markEmpty) && !p.getBoard()[row - 1][x].equals(type))
                || (!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))) {
              return false;
            }
          }
        } else {// checking bottom
          if ((!p.getBoard()[row][x].equals(Utils.markEmpty) && !p.getBoard()[row][x].equals(type))
              || (!p.getBoard()[row + 1][x].equals(Utils.markEmpty) && !p.getBoard()[row + 1][x].equals(type))) {
            return false;
          }
        }
      }
    }

    return true;
  }

  /**
   * Refreshes the UI grid of a player
   * 
   * @param p
   *          player
   * @param grid
   *          UI grid pane
   * @param showBoat
   *          check if the boats should be displayed or hidden
   * @param clickSound
   *          type of sound to be reproduced (0=click, 1=move, default=no sound)
   */
  void updateBoardDisplay(Player p, GridPane grid, boolean showBoat, int clickSound) {
    for (Node node : grid.getChildren()) {
      Rectangle rect = (Rectangle) node;
      rect.setFill(Utils.squareFillColor);

      Integer x = GridPane.getColumnIndex(node);
      Integer y = GridPane.getRowIndex(node);

      rect.setStroke(Utils.squareForeColor);
      if (p.getBoard()[y][x] == Utils.markHit) {
        Image img = new Image("file:media/red_100x100.png");
        rect.setFill(new ImagePattern(img));
      } else if (p.getBoard()[y][x] == Utils.markMiss) {
        Image img = new Image("file:media/white_100x100.png");
        rect.setFill(new ImagePattern(img));
      } else if (showBoat && p.getBoard()[y][x] != Utils.markEmpty) {
        rect.setFill(Utils.getBoatColor(p.getBoard()[y][x]));
      }
    }

    switch (clickSound) {
    case 0:
      mediaPlayerClick = new MediaPlayer(soundMove);
      mediaPlayerClick.play();
      break;
    case 1:
      mediaPlayerClick = new MediaPlayer(soundClick);
      mediaPlayerClick.play();
      break;

    default:
    }
  }

  /**
   * Places a ship on the board
   * 
   * @param p
   *          player to have the board set
   * @param row
   *          starting row
   * @param col
   *          starting column
   * @param size
   *          size of the ship
   * @param vertical
   *          checks if the ship should be placed vertically or horizontally
   * @param type
   *          type of ship (0=carrier, 1=battleship, 2=cruiser, 3=submarine,
   *          4=destroyer)
   */
  public static void positionsSet(Player p, int row, int col, int size, boolean vertical, String type) {
    if (vertical) {
      for (int y = row; y < size + row; y++) {
        p.getBoard()[y][col] = type;
      }
    } else {
      for (int x = col; x < size + col; x++) {
        p.getBoard()[row][x] = type;
      }
    }
  }

  /**
   * Places the ships of a player on the board randomly following the restrictions
   * 
   * @param p
   *          Player
   */
  public static void placeShips(Player p) {
    Random rnd = new Random();
    
    // for each boat type
    int b = 0;
    for (Utils.shipNames ship : Utils.shipNames.values()) {
      // try until placed
      while (true) {
        boolean vertical = rnd.nextBoolean();
        int row = rnd.nextInt(10);
        int col = rnd.nextInt(10);
        Ship s = Utils.shipFactory.create(ship.getName(), row, col, vertical);

        if (positionsValid(p, row, col, Utils.shipSizes[b], vertical, String.valueOf(b))) {
          p.getShips()[b] = s;
          positionsSet(p, row, col, Utils.shipSizes[b], vertical, String.valueOf(b));
          break;
        }
      }
      b++;
    }

    Utils.printBoard(p);
  }

  /**
   * Disables all the events of a player's grid
   * 
   * @param grid
   *          Players' grid
   * @param val
   *          true=disable, false=enable
   */
  void disableGrid(GridPane grid, boolean val) {
    for (Node node : grid.getChildren()) {
      Rectangle rect = (Rectangle) node;
      rect.disableProperty().set(val);
    }
  }

  /**
   * Adds a row score to the scores table
   * 
   * @param player
   *          Player
   * @param score
   *          Score
   */
  public static void addScore(String player, String score) {
    Utils.scoreData.add(
        new Pair<StringProperty, StringProperty>(new SimpleStringProperty(player), new SimpleStringProperty(score)));
  }

  /**
   * Disables the UI elements of the config area
   * 
   * @param val
   *          true=disable, false=enable
   */
  void disableConfig(boolean val) {
    lblConfig.setDisable(val);
    lblAILevel.setDisable(val);
    cmbAILevel.setDisable(val);
    lblSetupPlayer.setDisable(val);
    cmbSetupPlayer.setDisable(val);
    chkSalvoMode.setDisable(val);
    lblMouseHelp.setDisable(val);
  }

  /**
   * Resets all the parameters of the game depending on the mode (single player or
   * 2-player)
   */
  void reset() {
    System.out.println("GameController: reset()");
    aiRandom = 0;
    gameMode = false;
    gameOver = false;

    clicked = false;
    dragged = false;

    player1.resetBoard();
    player2.resetBoard();
    placeShips(player1);
    placeShips(player2);

    if (chkmenui1Player.isSelected()) {
      setGridPlayer2AIListeners();
      setGridPlayer1HumanListeners();
    } else {
      setGridPlayer2HumanListeners();
      setGridPlayer1HumanListeners();
    }
    updateBoardDisplay(player1, gridPlayer1, true, -1);
    updateBoardDisplay(player2, gridPlayer2, false, -1);
    disableGrid(gridPlayer1, false);
    disableGrid(gridPlayer2, true);

    disableConfig(false);

    timer1.stop();
    timer2.stop();
    lblTimer1.setText("00:00:00");
    lblTimer2.setText("00:00:00");

    // salvo
    salvoShots = 0;
    lblShotsPlayer1.setText("Shots: 5");
    lblShotsPlayer2.setText("Shots: 5");

    bttnStartGame.setText("Start Game");
    lblStatus.setText("Ready");
  }

  /**
   * Starts/Resets the game
   */
  @FXML
  void onStartGame() {
    if (!gameMode) {
      gameMode = true;
      lblStatus.setText("Turn: " + player1.getName());
      turnPlayer1 = true;
      disableConfig(true);

      startTime1 = 0;
      startTime2 = 0;
      timer1.play();

      bttnStartGame.setText("Reset Game");

      if (chkmenui1Player.isSelected()) {
        disableGrid(gridPlayer1, true);
        disableGrid(gridPlayer2, false);
        updateBoardDisplay(player2, gridPlayer2, false, -1);
        updateBoardDisplay(player1, gridPlayer1, true, -1);
      } else {
        disableGrid(gridPlayer1, false);
        disableGrid(gridPlayer2, false);
        updateBoardDisplay(player2, gridPlayer2, false, -1);
        updateBoardDisplay(player1, gridPlayer1, false, -1);
      }

    } else {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("BattleshipFX - Reset");
      alert.setHeaderText("The game will be reset");
      Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
      stage.getIcons().add(new Image("file:media/BattleshipFX.png"));
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        reset();
      }
    }
  }

  /**
   * Executed when the mouse enters a square of the board
   * 
   * @param player
   *          Player
   * @param gridPlayer
   *          Player's grid
   * @param mouseEvent
   *          Mouse event
   */
  void onMouseEntered(Player player, GridPane gridPlayer, MouseEvent mouseEvent) {
    Rectangle r = (Rectangle) mouseEvent.getSource();

    int col = GridPane.getColumnIndex(r);
    int row = GridPane.getRowIndex(r);

    if (dragged) {
      dragged = false;
      System.out.print("to (" + row + "," + col + "): ");
      String sId = player.getBoard()[startRow][startCol];
      int iId = Integer.valueOf(sId);
      Ship oldS = player.getShips()[iId];
      if (positionsValid(player, row, col, player.getShips()[iId].getSize(), player.getShips()[iId].isVertical(),
          sId)) {
        System.out.println("valid");
        // delete old pos
        positionsSet(player, oldS.getRow(), oldS.getCol(), oldS.getSize(), oldS.isVertical(), Utils.markEmpty);
        Utils.printBoard(player);

        // set new pos
        positionsSet(player, row, col, Utils.shipSizes[iId], oldS.isVertical(), sId);
        Utils.printBoard(player);
        Ship newS =  Utils.shipFactory.create(oldS.getType(), row, col, oldS.isVertical());
        player.getShips()[iId] = newS;
        updateBoardDisplay(player, gridPlayer, true, 0);
      } else {
        System.out.println("invalid");
        lblStatus.setText(Utils.getBoatName(iId) + " cannot be moved to (" + (char) (col + 65) + "," + (row + 1) + ") "
            + (oldS.isVertical() ? "vertically" : "horizontally"));
      }
    }

    if (!player.getBoard()[row][col].equals(Utils.markEmpty)) {
      r.setCursor(Cursor.HAND);
    }
  }

  /**
   * Executed when the mouse drags a square of the board
   * 
   * @param player
   *          Player
   * @param gridPlayer
   *          Player's grid
   * @param mouseEvent
   *          Mouse event
   */
  void onMouseDragged(Player player, GridPane gridPlayer, MouseEvent mouseEvent) {
    MouseButton button = mouseEvent.getButton();
    if (button == MouseButton.PRIMARY) {
      Rectangle r = (Rectangle) mouseEvent.getSource();
      int col = GridPane.getColumnIndex(r);
      int row = GridPane.getRowIndex(r);

      if (!player.getBoard()[row][col].equals(Utils.markEmpty) && !dragged) {
        r.setCursor(Cursor.CLOSED_HAND);
        dragged = true;
        startCol = col;
        startRow = row;
        System.out.println("moving from (" + startRow + "," + startCol + ")");
      }
    }
  }

  /**
   * Executed when the mouse releases a square of the board
   * 
   * @param player
   *          Player
   * @param gridPlayer
   *          Player's grid
   * @param mouseEvent
   *          Mouse event
   */
  void onMouseReleased(Player player, GridPane gridPlayer, MouseEvent mouseEvent) {
    Rectangle r = (Rectangle) mouseEvent.getSource();

    int col = GridPane.getColumnIndex(r);
    int row = GridPane.getRowIndex(r);
    System.out.println("setOnMouseReleased: " + row + "," + col);
    for (Node node : gridPlayer.getChildren()) {
      Rectangle rect = (Rectangle) node;
      rect.setCursor(Cursor.DEFAULT);
    }
  }

  /**
   * Executed when there's a mouse right-click on a square of the board
   * 
   * @param player
   *          Player
   * @param gridPlayer
   *          Player's grid
   * @param mouseEvent
   *          Mouse event
   */
  void onMouseClickedRight(Player player, GridPane gridPlayer, MouseEvent mouseEvent) {

    Rectangle r = (Rectangle) mouseEvent.getSource();

    int col = GridPane.getColumnIndex(r);
    int row = GridPane.getRowIndex(r);

    if (player.getBoard()[row][col] != Utils.markEmpty) {
      System.out.println("setOnMouseClicked: (" + row + "," + col + "): ");

      String sId = player.getBoard()[row][col];
      int iId = Integer.valueOf(sId);
      Ship oldS = player.getShips()[iId];

      int shift = oldS.getSize() - 1;
      System.out.println("shift:" + shift);
      int cStart = Math.max(oldS.isVertical() ? oldS.getCol() - shift : oldS.getCol(), 0);
      int rStart = Math.max(oldS.isVertical() ? oldS.getRow() : oldS.getRow() - shift, 0);
      //
      int cEnd = Math.min(oldS.isVertical() ? oldS.getCol() + shift : oldS.getCol() + oldS.getSize() - 1, 9);
      int rEnd = Math.min(oldS.isVertical() ? oldS.getRow() + oldS.getSize() - 1 : oldS.getRow() + shift, 9);
      System.out.println("start: (" + rStart + "," + cStart + "); end: (" + rEnd + "," + cEnd + ")");

      boolean found = false;
      loopExit: for (int rr = rStart; rr <= rEnd; rr++) {
        for (int cc = cStart; cc <= cEnd; cc++) {
          if (positionsValid(player, rr, cc, oldS.getSize(), !oldS.isVertical(), sId)) {
            // delete old pos
            positionsSet(player, oldS.getRow(), oldS.getCol(), oldS.getSize(), oldS.isVertical(), Utils.markEmpty);
            Utils.printBoard(player);

            // set new pos
            positionsSet(player, rr, cc, Utils.shipSizes[iId], !oldS.isVertical(), sId);
            Utils.printBoard(player);
            Ship newS =  Utils.shipFactory.create(oldS.getType(), rr, cc, !oldS.isVertical());
            player.getShips()[iId] = newS;
            updateBoardDisplay(player, gridPlayer, true, 0);
            break loopExit;
          }
        }
      }
      if (!found) {
        System.out.println(
            "onMouseClickedRight(): couldn't find a position to rotate; try moving the ship somewhere else first");
      }
    }
  }

  /**
   * Sets the initial appearance and mouse events for each square of Player1
   */
  void setGridPlayer1HumanListeners() {
    for (Node node : gridPlayer1.getChildren()) {
      Rectangle rect = (Rectangle) node;

      Integer x = GridPane.getColumnIndex(node);
      Integer y = GridPane.getRowIndex(node);

      // first rows and cols need to be initialized
      if (x == null) {
        x = 0;
        GridPane.setColumnIndex(node, x);
      }

      if (y == null) {
        y = 0;
        GridPane.setRowIndex(node, y);
      }

      rect.setStroke(Utils.squareForeColor);
      if (!player1.getBoard()[y][x].equals(Utils.markEmpty)) {
        rect.setFill(Utils.getBoatColor(player1.getBoard()[y][x]));
      } else {
        rect.setFill(Utils.squareFillColor);
      }

      rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          if (!gameMode) {
            onMouseEntered(player1, gridPlayer1, mouseEvent);
          }
        }
      });
      rect.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          if (!gameMode) {
            onMouseDragged(player1, gridPlayer1, mouseEvent);
          }
        }
      });
      rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          if (!gameMode) {
            onMouseReleased(player1, gridPlayer1, mouseEvent);
          }
        }
      });
      rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
          MouseButton button = mouseEvent.getButton();
          if (button == MouseButton.SECONDARY) {
            if (!gameMode) {
              onMouseClickedRight(player1, gridPlayer1, mouseEvent);
            }
          } else if (button == MouseButton.PRIMARY) {
            Rectangle r = (Rectangle) mouseEvent.getSource();
            int col = GridPane.getColumnIndex(r);
            int row = GridPane.getRowIndex(r);
            System.out.println(col + "," + row + " at player1's");
            if (gameMode) {
              // Player2's turn
              if (!turnPlayer1) {
                if (chkSalvoMode.isSelected()) {
                  if (player1.getBoard()[row][col] != Utils.markHit && player1.getBoard()[row][col] != Utils.markMiss) {
                    if (salvoShots++ < Utils.numberOfShipsOn(player2)) {
                      playerHumanSinglePlay(row, col, player2, gridPlayer2, player1, gridPlayer1);
                      if (gameOver) {
                        timer2.stop();
                        disableGrid(gridPlayer1, true);
                        mediaPlayerClick = new MediaPlayer(soundWin);
                        mediaPlayerClick.play();
                        Utils.showInfo("Winner: " + player2.getName(), true);
                        lblStatus.setText("Winner: " + player2.getName());
                        addScore(player2.getName(), lblTimer2.getText());
                        return;
                      }

                      if (salvoShots == Utils.numberOfShipsOn(player2)) {
                        lblShotsPlayer1.setText("Shots: " + Utils.numberOfShipsOn(player1));
                        salvoShots = 0;
                        timer2.pause();
                        timer1.play();
                        turnPlayer1 = true;
                        lblStatus.setText("Turn: " + player1.getName());
                      }
                    }
                  } else {
                    System.out.println("Position already tried");
                  }
                } else {
                  if (player1.getBoard()[row][col] != Utils.markHit && player1.getBoard()[row][col] != Utils.markMiss) {
                    playerHumanSinglePlay(row, col, player2, gridPlayer2, player1, gridPlayer1);
                    if (gameOver) {
                      timer2.stop();
                      disableGrid(gridPlayer1, true);
                      mediaPlayerClick = new MediaPlayer(soundWin);
                      mediaPlayerClick.play();
                      Utils.showInfo("Winner: " + player2.getName(), true);
                      lblStatus.setText("Winner: " + player2.getName());
                      addScore(player2.getName(), lblTimer2.getText());
                      return;
                    }
                    timer2.pause();
                    timer1.play();
                    turnPlayer1 = true;
                    lblStatus.setText("Turn: " + player1.getName());
                  }
                }
              } else {
                System.out.println("Position already tried");
              }
            }
          }

        }
      });
    }
  }

  /**
   * Sets the initial appearance and mouse events for each square of Player2
   * (human)
   */
  void setGridPlayer2HumanListeners() {
    for (Node node : gridPlayer2.getChildren()) {
      Rectangle rect = (Rectangle) node;

      Integer x = GridPane.getColumnIndex(node);
      Integer y = GridPane.getRowIndex(node);

      // first rows and cols need to be initialized
      if (x == null) {
        x = 0;
        GridPane.setColumnIndex(node, x);
      }

      if (y == null) {
        y = 0;
        GridPane.setRowIndex(node, y);
      }

      rect.setStroke(Utils.squareForeColor);
      if (!player2.getBoard()[y][x].equals(Utils.markEmpty)) {
        rect.setFill(Utils.getBoatColor(player2.getBoard()[y][x]));
      } else {
        rect.setFill(Utils.squareFillColor);
      }

      rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          if (!gameMode) {
            onMouseEntered(player2, gridPlayer2, mouseEvent);
          }
        }
      });
      rect.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          if (!gameMode) {
            onMouseDragged(player2, gridPlayer2, mouseEvent);
          }
        }
      });
      rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          if (!gameMode) {
            onMouseReleased(player2, gridPlayer2, mouseEvent);
          }
        }
      });
      rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
          MouseButton button = mouseEvent.getButton();
          if (button == MouseButton.SECONDARY) {
            if (!gameMode) {
              onMouseClickedRight(player2, gridPlayer2, mouseEvent);
            }
          } else if (button == MouseButton.PRIMARY) {
            Rectangle r = (Rectangle) mouseEvent.getSource();
            int col = GridPane.getColumnIndex(r);
            int row = GridPane.getRowIndex(r);
            System.out.println(col + "," + row + " at player2's");
            if (gameMode) {
              // Player1's turn
              if (turnPlayer1) {
                if (chkSalvoMode.isSelected()) {
                  if (player2.getBoard()[row][col] != Utils.markHit && player2.getBoard()[row][col] != Utils.markMiss) {
                    if (salvoShots++ < Utils.numberOfShipsOn(player1)) {
                      playerHumanSinglePlay(row, col, player1, gridPlayer1, player2, gridPlayer2);
                      if (gameOver) {
                        timer1.stop();
                        disableGrid(gridPlayer2, true);
                        mediaPlayerClick = new MediaPlayer(soundWin);
                        mediaPlayerClick.play();
                        Utils.showInfo("Winner: " + player1.getName(), true);
                        lblStatus.setText("Winner: " + player1.getName());
                        addScore(player1.getName(), lblTimer1.getText());
                        return;
                      }

                      if (salvoShots == Utils.numberOfShipsOn(player1)) {
                        lblShotsPlayer2.setText("Shots: " + Utils.numberOfShipsOn(player2));
                        salvoShots = 0;
                        timer1.pause();
                        timer2.play();
                        turnPlayer1 = false;
                        lblStatus.setText("Turn: " + player2.getName());
                      }
                    }
                  } else {
                    System.out.println("Position already tried");
                  }
                } else {
                  if (player2.getBoard()[row][col] != Utils.markHit && player2.getBoard()[row][col] != Utils.markMiss) {
                    playerHumanSinglePlay(row, col, player1, gridPlayer1, player2, gridPlayer2);
                    if (gameOver) {
                      timer1.stop();
                      disableGrid(gridPlayer2, true);
                      mediaPlayerClick = new MediaPlayer(soundWin);
                      mediaPlayerClick.play();
                      Utils.showInfo("Winner: " + player1.getName(), true);
                      lblStatus.setText("Winner: " + player1.getName());
                      addScore(player1.getName(), lblTimer1.getText());
                      return;
                    }
                    timer1.pause();
                    timer2.play();
                    turnPlayer1 = false;
                    lblStatus.setText("Turn: " + player2.getName());
                  }
                }
              } else {
                System.out.println("Position already tried");
              }
            }
          }
        }
      });
    }
  }

  /**
   * Executes a random play of the A.I.
   * 
   * @return true=game over, false otherwise
   */
  boolean doRandom() {
    Random rnd = new Random();
    while (true) { // stay here until hit or miss
      int rr = rnd.nextInt(10);
      int cc = rnd.nextInt(10);
      if (player1.getBoard()[rr][cc] != Utils.markHit && player1.getBoard()[rr][cc] != Utils.markMiss) {
        if (player1.getBoard()[rr][cc] == Utils.markEmpty) {
          // miss
          player1.getBoard()[rr][cc] = Utils.markMiss;
          break;
        } else {
          // hit
          int index = Integer.valueOf(player1.getBoard()[rr][cc]);
          player1.getBoard()[rr][cc] = Utils.markHit;
          player1.getShips()[index].setHits(player1.getShips()[index].getHits() + 1);
          if (player1.getShips()[index].getHits() == player1.getShips()[index].getSize()) {
            player1.getShips()[index].setDestroyed(true);
            // check if Gameover
            if (Utils.isGameOver(player1)) {
              return true;
            } else {
              mediaPlayerClick = new MediaPlayer(soundExplosion);
              mediaPlayerClick.play();
              Utils.showInfo("Ship down: " + player1.getName(), false);
            }
          }
          break;
        }
      } else {
        System.out.println("Position already tried");
      }
    }

    return false;
  }

  /**
   * Executes a play for the A.I.
   * 
   * @param level
   *          easy, hard or medium
   * @return true=game over, false otherwise
   */
  boolean aiPlay(String level) {
    if (level.equalsIgnoreCase("easy")) {
      return doRandom();
    } else {
      // do only 4 random moves in 'medium' and 2 in 'hard'
      aiRandomMoves = level.equalsIgnoreCase("medium") ? 4 : 2;
      if (aiRandom++ < aiRandomMoves) {
        return doRandom();
      } else {
        for (Ship s : player1.getShips()) {
          if (s.isVertical()) {
            int cc = s.getCol();
            for (int y = s.getRow(); y < s.getRow() + s.getSize(); y++) {
              int rr = y;
              if (player1.getBoard()[rr][cc] != Utils.markHit && player1.getBoard()[rr][cc] != Utils.markMiss
                  && player1.getBoard()[rr][cc] != Utils.markEmpty) {
                // hit
                int index = Integer.valueOf(player1.getBoard()[rr][cc]);
                player1.getBoard()[rr][cc] = Utils.markHit;
                player1.getShips()[index].setHits(player1.getShips()[index].getHits() + 1);
                if (player1.getShips()[index].getHits() == player1.getShips()[index].getSize()) {
                  player1.getShips()[index].setDestroyed(true);
                  aiRandom = 0;
                  // check if Gameover
                  if (Utils.isGameOver(player1)) {
                    return true;
                  } else {
                    mediaPlayerClick = new MediaPlayer(soundExplosion);
                    mediaPlayerClick.play();
                    Utils.showInfo("Ship down: " + player1.getName(), false);
                  }
                }
                return false;
              }
            }
          } else {
            int rr = s.getRow();
            for (int x = s.getCol(); x < s.getCol() + s.getSize(); x++) {
              int cc = x;
              if (player1.getBoard()[rr][cc] != Utils.markHit && player1.getBoard()[rr][cc] != Utils.markMiss
                  && player1.getBoard()[rr][cc] != Utils.markEmpty) {
                // hit
                int index = Integer.valueOf(player1.getBoard()[rr][cc]);
                player1.getBoard()[rr][cc] = Utils.markHit;
                player1.getShips()[index].setHits(player1.getShips()[index].getHits() + 1);
                if (player1.getShips()[index].getHits() == player1.getShips()[index].getSize()) {
                  player1.getShips()[index].setDestroyed(true);
                  aiRandom = 0;
                  // Check if Gameover
                  if (Utils.isGameOver(player1)) {
                    return true;
                  } else {
                    mediaPlayerClick = new MediaPlayer(soundExplosion);
                    mediaPlayerClick.play();
                    Utils.showInfo("Ship down: " + player1.getName(), false);
                  }
                }
                return false;
              }
            }
          }
        }
      }
    }

    return false;
  }

  /***
   * Play move (precondition: position has not been previously hit or missed)
   * 
   * @param row Row
   * @param col Column
   * @param player1 Player to make the move
   * @param player2 Player to receive the move
   */
  public static void play(int row, int col, Player player1, Player player2)
  {
    if (player2.getBoard()[row][col] == Utils.markEmpty) {
      // miss
      player2.getBoard()[row][col] = Utils.markMiss;
    } else {
      // hit
      int index = Integer.valueOf(player2.getBoard()[row][col]);
      player2.getBoard()[row][col] = Utils.markHit;
      player2.getShips()[index].setHits(player2.getShips()[index].getHits() + 1);
      if (player2.getShips()[index].getHits() == player2.getShips()[index].getSize()) {
        player2.getShips()[index].setDestroyed(true);
        // Check if Gameover
        if (Utils.isGameOver(player2)) {
          gameOver = true;
        } else {
          mediaPlayerClick = new MediaPlayer(soundExplosion);
          mediaPlayerClick.play();
          Utils.showInfo("Ship down: " + player2.getName(), false);
        }
      }
    }
  }
  
  
  /**
   * Executes a single play for any human player
   * 
   * @param row
   *          Row selected
   * @param col
   *          Column selected
   * @param player1
   *          Player doing the move
   * @param gridPlayer1
   *          Grid of player doing the move
   * @param player2
   *          Player receiving the move
   * @param gridPlayer2
   *          Grid of player receiving te move
   */
  void playerHumanSinglePlay(int row, int col, Player player1, GridPane gridPlayer1, Player player2,
      GridPane gridPlayer2) {
    play(row,col,player1,player2);
    if (chkSalvoMode.isSelected()) {
      if (turnPlayer1) {
        System.out
            .println("playerHumanSinglePlay()>>player1: " + salvoShots + "," + Utils.numberOfShipsOn(this.player1));
        lblShotsPlayer1.setText("Shots: " + (Utils.numberOfShipsOn(this.player1) - salvoShots));
      } else if (chkmenui2Player.isSelected()) {
        // if(chkmenui2Player.isSelected()) {
        System.out
            .println("playerHumanSinglePlay()>>player2: " + salvoShots + "," + Utils.numberOfShipsOn(this.player2));
        lblShotsPlayer2.setText("Shots: " + (Utils.numberOfShipsOn(this.player2) - salvoShots));
        // }
      }
    }
    updateBoardDisplay(player2, gridPlayer2, false, 1);
    Utils.printBoard(player2);
  }

  /**
   * Executes one play for the A.I.
   */
  void playerAISinglePlay() {
    turnPlayer1 = false;
    lblStatus.setText("Turn: " + player2.getName());
    double timeToThink = new Random().nextDouble() * 3 + 0.5;
    System.out.println("Thinking for " + timeToThink + " secs");
    PauseTransition pause = new PauseTransition(Duration.seconds(timeToThink));
    pause.setOnFinished(event -> {
      if (chkmenui1Player.isSelected()) {
        lblStatus.setText("Turn: " + player1.getName());
        aiPlay(cmbAILevel.getValue());
        updateBoardDisplay(player1, gridPlayer1, true, 1);
        Utils.printBoard(player1);

        if (Utils.isGameOver(player1)) {
          timer1.stop();
          timer2.stop();
          disableGrid(gridPlayer2, true);
          mediaPlayerClick = new MediaPlayer(soundLose);
          mediaPlayerClick.play();
          Utils.showInfo("Winner: " + player2.getName(), false);
          lblStatus.setText("Winner: " + player2.getName());
          addScore(player2.getName(), lblTimer2.getText());
        } else {
          timer2.pause();
          timer1.play();
          turnPlayer1 = true;
        }
      }
    });
    pause.play();

  }

  /**
   * Executes one of the plays of the A.I. in salvo mode
   */
  void playerAISalvoTask() {
    if (chkmenui1Player.isSelected()) {
      if (!gameOver) {
        salvoShots++;
        System.out.println("salvoTask()>>" + salvoShots + "," + Utils.numberOfShipsOn(player2));
        lblShotsPlayer2.setText("Shots: " + Math.max((Utils.numberOfShipsOn(player2) - salvoShots), 0));
        gameOver = aiPlay(cmbAILevel.getValue());
        updateBoardDisplay(player1, gridPlayer1, true, 1);
        Utils.printBoard(player1);
      } else {
        timer1.stop();
        timer2.stop();
        playerAISalvoPlays.stop();

        disableGrid(gridPlayer2, true);
        mediaPlayerClick = new MediaPlayer(soundLose);
        mediaPlayerClick.play();
        Utils.showInfo("Winner: " + player2.getName(), false);
        lblStatus.setText("Winner: " + player2.getName());
        addScore(player2.getName(), lblTimer2.getText());
      }
    }
  }

  /**
   * Sets all the plays of the A.I. for salvo mode
   * 
   * @param shots
   *          Number of maximum shots available for the A.I.
   */
  void playerAISalvoPlay(int shots) {
    playerAISalvoPlays = new Timeline();
    Random rnd = new Random();
    System.out.print(">>Thinking for: ");
    for (int i = 0; i < shots; i++) {
      double timeToThink = rnd.nextDouble() * 3 + 1;
      System.out.print(timeToThink + "; ");
      playerAISalvoPlays.getKeyFrames().add(new KeyFrame(Duration.seconds(timeToThink), e -> playerAISalvoTask()));
    }
    System.out.println(" seconds");
    playerAISalvoPlays.setOnFinished(event -> {
      if (chkmenui1Player.isSelected()) {
        timer2.pause();
        timer1.play();
        turnPlayer1 = true;
        lblStatus.setText("Turn: " + player1.getName());
        lblShotsPlayer1.setText("Shots: " + Utils.numberOfShipsOn(player1));
        salvoShots = 0;
        playerAISalvoPlays.stop();
      }
    });
    playerAISalvoPlays.play();
  }

  /**
   * Sets the initial appearance and mouse events for each square of Player2
   * (A.I.)
   */
  void setGridPlayer2AIListeners() {
    for (Node node : gridPlayer2.getChildren()) {
      Rectangle rect = (Rectangle) node;

      Integer x = GridPane.getColumnIndex(node);
      Integer y = GridPane.getRowIndex(node);

      // first rows and cols need to be initialized
      if (x == null) {
        x = 0;
        GridPane.setColumnIndex(node, x);
      }

      if (y == null) {
        y = 0;
        GridPane.setRowIndex(node, y);
      }

      rect.setFill(Utils.squareFillColor);
      rect.setStroke(Utils.squareForeColor);

      rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
          if (turnPlayer1) {
            Rectangle r = (Rectangle) event.getSource();

            int col = GridPane.getColumnIndex(r);
            int row = GridPane.getRowIndex(r);

            System.out.println(col + "," + row + " at opponent's");
            if (gameMode) {
              if (chkSalvoMode.isSelected()) {
                if (player2.getBoard()[row][col] != Utils.markHit && player2.getBoard()[row][col] != Utils.markMiss) {
                  if (salvoShots++ < Utils.numberOfShipsOn(player1)) {
                    // Player1's turn
                    playerHumanSinglePlay(row, col, player1, gridPlayer1, player2, gridPlayer2);
                    if (gameOver) {
                      timer1.stop();
                      disableGrid(gridPlayer2, true);
                      mediaPlayerClick = new MediaPlayer(soundWin);
                      mediaPlayerClick.play();
                      Utils.showInfo("Winner: " + player1.getName(), true);
                      lblStatus.setText("Winner: " + player1.getName());
                      addScore(player1.getName(), lblTimer1.getText());
                      return;
                    }

                    if (salvoShots == Utils.numberOfShipsOn(player1)) {
                      lblShotsPlayer2.setText("Shots: " + Utils.numberOfShipsOn(player2));
                      salvoShots = 0;
                      timer1.pause();
                      timer2.play();
                      turnPlayer1 = false;
                      lblStatus.setText("Turn: " + player2.getName());
                      playerAISalvoPlay(Utils.numberOfShipsOn(player2));
                    }
                  }
                } else {
                  System.out.println("Position already tried");
                }
              } else {
                if (player2.getBoard()[row][col] != Utils.markHit && player2.getBoard()[row][col] != Utils.markMiss) {
                  // Player1's turn
                  playerHumanSinglePlay(row, col, player1, gridPlayer1, player2, gridPlayer2);
                  if (gameOver) {
                    timer1.stop();
                    disableGrid(gridPlayer2, true);
                    mediaPlayerClick = new MediaPlayer(soundWin);
                    mediaPlayerClick.play();
                    Utils.showInfo("Winner: " + player1.getName(), true);
                    lblStatus.setText("Winner: " + player1.getName());
                    addScore(player1.getName(), lblTimer1.getText());
                  } else {
                    // Player2's turn
                    timer1.pause();
                    timer2.play();
                    playerAISinglePlay();
                  }
                } else {
                  System.out.println("Position already tried");
                }
              }
            }
          }
        }
      });
    }
  }

  /**
   * Displays the About info
   */
  @FXML
  void onMenuItemAbout() {
    Utils.showInfo("BattleshipFX  - Copyright July 2019", true);
  }

  /**
   * Sets the game between normal and salvo mode
   * 
   * @param event
   *          Event triggered when a selection is made
   */
  @FXML
  void onSalvoChanged(ActionEvent event) {
    lblShotsPlayer1.setVisible(chkSalvoMode.isSelected());
    lblShotsPlayer2.setVisible(chkSalvoMode.isSelected());
  }

  /**
   * Switches between Player1 and Player2 in 2-Player mode
   */
  @FXML
  void onSetupPlayerChange() {
    System.out.println(cmbSetupPlayer.getValue());
    if (cmbSetupPlayer.getValue().equals(player1.getName())) {
      updateBoardDisplay(player1, gridPlayer1, true, -1);
      updateBoardDisplay(player2, gridPlayer2, false, -1);
      disableGrid(gridPlayer1, false);
      disableGrid(gridPlayer2, true);
    } else {
      updateBoardDisplay(player1, gridPlayer1, false, -1);
      updateBoardDisplay(player2, gridPlayer2, true, -1);
      disableGrid(gridPlayer1, true);
      disableGrid(gridPlayer2, false);
    }
  }

  /**
   * Changes the mouse cursor and enables the edit mode when hovering over
   * Player1's name
   */
  @FXML
  void onPlayer1NameMouseClicked() {
    txtPlayer1.setEditable(true);
    txtPlayer1.setCursor(Cursor.DEFAULT);
  }

  /**
   * Changes the mouse cursor when hovering over Player1's name
   */
  @FXML
  void onPlayer1NameMouseEntered() {
    txtPlayer1.setCursor(Cursor.HAND);
  }

  /**
   * Disables the edit mode when hovering off Player1's name
   */
  @FXML
  void onPlayer1NameMouseExited() {
    txtPlayer1.setEditable(false);
  }

  /**
   * Edits the name of Player1
   */
  @FXML
  void onPlayer1NameEntered() {
    player1.setName(txtPlayer1.getText());
    txtPlayer1.setEditable(false);
  }

  /**
   * Changes the mouse cursor and edit mode when hovering over Player2's name
   */
  @FXML
  void onPlayer2NameMouseClicked() {
    txtPlayer2.setEditable(true);
    txtPlayer2.setCursor(Cursor.DEFAULT);
  }

  /**
   * Changes the mouse cursor when hovering over Player2's name
   */
  @FXML
  void onPlayer2NameMouseEntered() {
    txtPlayer2.setCursor(Cursor.HAND);
  }

  /**
   * Disables the edit mode when hovering off Player2's name
   */
  @FXML
  void onPlayer2NameMouseExited() {
    txtPlayer2.setEditable(false);
  }

  /**
   * Edits the name of Player2
   */
  @FXML
  void onPlayer2NameEntered() {
    player2.setName(txtPlayer2.getText());
    txtPlayer2.setEditable(false);
  }
}
