package fr.uge.patchwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class representing the Tab object. It allows you to group the patch laying
 * board as well as the board on which the players can move.
 **/
public class Table {
  private int distance;
  private boolean reverse;
  private int[] arrayPawn = { 0, 0 };
  private int player1;
  private int player2;
  private final int[] array;
  private final int[][] arrayPlayer;

  /**
   * Constructor for Tab object, who initialize an integer array, a number 0 for
   * player1 and player2, and a state false for reverse (to know which player is
   * in front of the other).
   *
   * @param array       : Integer array of one dimension. (main game board)
   * @param arrayPlayer : Integer array of two dimension. (game board for player
   *                    to put patch)
   */
  public Table(int[] array, int[][] arrayPlayer) {
    this.array = array;
    this.arrayPlayer = arrayPlayer;
    this.player2 = 0;
    this.player1 = 0;
    this.distance = 1;
    this.reverse = false;
  }

  /**
   * Method to initialize game board, by placing the players in their starting
   * position, place the buttons as well as the pieces of fabric.
   * 
   * @return Table : new Table Array.
   **/
  public Table createmap() {
    /* case whose value is 1, 2 = Player 1 and 2 */
    array[1] = 1;
    array[2] = 2;
    /* case whose value is 3 = */
    array[7] = array[14] = array[21] = array[29] = array[37] = array[52] = array[45] = 3;
    array[62] = array[76] = 3;
    /* case whose value is 4 = */
    array[24] = array[34] = array[42] = array[58] = array[67] = 4;
    /* case whose value is 5 = */
    array[77] = array[78] = array[79] = array[80] = 5;
    return new Table(array, new int[9][9]);
  }

  /**
   * Static method to adds to an integer list the different events of the game
   * board.
   * 
   * @param pos        : (int) Position in the board game.
   * @param array      : (int[]) Main board game.
   * @param arrayEvent : (List) arrayEvent which will contains the different event
   *                   of the game board.
   **/
  public static void event(int pos, int[] array, List<Integer> arrayEvent) {
    Objects.requireNonNull(arrayEvent, "arrayEvent is null");
    Objects.requireNonNull(array, "array is null");
    if (pos < 0 || pos > 80) {
      throw new IllegalArgumentException("pos is not between 0 and 80");
    }
    var maprefernce = new Table(new int[81], new int[9][9]);
    maprefernce = maprefernce.createmap();
    switch (maprefernce.array[pos]) {
    case 3:
      arrayEvent.add(3);
      break;
    case 5:
      arrayEvent.add(5);
      break;
    default:
      break;
    }
    if (array[pos] == 4) {
      arrayEvent.add(4);
    }
  }

  /**
   * Static method that puts the element back in place once the player has passed
   * it.
   * 
   * @param pos   : (int) position in the board game.
   * @param array : (int[]) Main board game.
   **/
  public static void Pawnexchanger(int pos, int[] array) {
    Objects.requireNonNull(array, "array is null");
    if (pos < 0 || pos > 80) {
      throw new IllegalArgumentException("pos is not between 0 and 80");
    }
    var maprefernce = new Table(new int[81], new int[9][9]);
    maprefernce = maprefernce.createmap();
    switch (maprefernce.array[pos]) {
    case 0, 1, 2:
      array[pos] = 0;
      break;
    case 3:
      if (maprefernce.array[pos] == 3) {
        array[pos] = 3;
      }
      break;
    case 4:
      if (maprefernce.array[pos] == 4) {
        array[pos] = 0;
      }
      break;
    default:
      System.out.println("Token unrecognised");
      break;
    }
  }

  /**
   * Static method that calculates the distance between the two players pieces.
   * 
   * @param player:  (int) integer representing one of the two players.
   * @param inc      : (boolean) boolean indicating if player 1 and in front or
   *                 behind player 2.
   * @param distance : (int) integer representing the actual distance between the
   *                 two players pieces.
   * 
   * @return int : (int) integer that representing the distance between players.
   **/
  public static int distancePawn(int player, boolean inc, int distance) {
    if (player < 0) {
      throw new IllegalArgumentException("player < 0");
    }
    if (distance < 0) {
      throw new IllegalArgumentException("distance < 0");
    }
    if (inc == true) {
      if (player == 1) {
        distance++;
      } else {
        distance--;
      }
    } else {
      if (player == 1) {
        distance--;
      } else {
        distance++;
      }
    }
    return distance;
  }

  /**
   * Static method to change the 'reverse' variable according to the player.
   * 
   * @param player : (int) integer who represent the player.
   * 
   * @return boolean : (boolean) Returns true if the player if the player 1 and
   *         otherwise false.
   **/
  public static boolean changeReverse(int player) {
    return (player == 1) ? true : false;
  }

  /**
   * Method that changes the main game board in case of overlapping players.
   * 
   * @param array       : (int[]) integer array 1D that representing the game
   *                    board.
   * @param arrayPawn   : (int) integer array 1D who contains players.
   * @param pos         : (int) integer that representing the position of a
   *                    player.
   * @param player      : (int) integer representing a player.
   * @param otherPlayer : (int) integer representing the other player.
   * @param distance    : (int) integer representing the actual distance between
   *                    the two players.
   */
  public static void overlay(int[] array, int[] arrayPawn, int pos, int player, int otherPlayer, int distance) {
    Objects.requireNonNull(array, "array is null");
    Objects.requireNonNull(arrayPawn, "arrayPawn is null");
    if (pos < 0 || pos > 80) {
      throw new IllegalArgumentException("pos < 0 || pos > 80");
    }
    if (distance < 0) {
      throw new IllegalArgumentException("distance < 0");
    }
    if (array[pos] == otherPlayer) {
      arrayPawn[1] = otherPlayer;
      arrayPawn[0] = player;
      array[pos] = 6;
    } else {
      array[pos] = player;
    }
  }

  /*
   * public static processCase(boolean get ,int line,int column,int player,
   * List<Integer> arrayOfEvent)
   */
  /**
   * method advancing players' counters within a circular array of integer
   * according to the steps to be taken.
   * 
   * @param player       : (int) integer who represent the player.
   * @param caseforward  : (int) number of steps to be taken.
   * @param otherPlayer  : (int) integer who represent the other player.
   * @param arrayOfEvent : (List) List who represent the list of event.
   **/
  public void pawnCase(int player, int caseforward, int otherPlayer, List<Integer> arrayOfEvent) {
    Objects.requireNonNull(arrayOfEvent, "arrayOfEvent is null");
    var get = false;
    for (var i = 0; i < 81; i++) {
      if (array[i] == player || array[i] == 6) {
        get = true;
        if (array[i] == 6) {
          reverse = changeReverse(player);
          array[i] = arrayPawn[1];
        } else {
          Pawnexchanger(i, array);
        }
      }
      if (get) {
        event(i, array, arrayOfEvent);
        /* if(array[i] == 4 && caseforward==0) {array[i]=player;} */
        if (array[i] == 4 && caseforward != 0) {
          array[i] = 0;
        }
        if (caseforward == 0) {
          overlay(array, arrayPawn, i, player, otherPlayer, distance);
          return;
        }
        if (array[i] == otherPlayer) {
          reverse = changeReverse(player);
        }
        distance = distancePawn(player, isReverse(), distance);
        caseforward--;
      }
    }
  }

  /**
   * Method that calls the move function of the counters in the game board.
   * 
   * @param player      : (int) integer who represent the player.
   * @param caseforward : (int) number of steps to be taken.
   * 
   * @return ArrayList : (ArrayList) list of all event in the game board.
   **/
  public ArrayList<Integer> Pion(int caseforward, int player) {
    if (caseforward < 0) {
      throw new IllegalArgumentException("caseforward < 0");
    }
    if (player < 0) {
      throw new IllegalArgumentException("player < 0");
    }
    var arrayEvent = new ArrayList<Integer>();
    if (player == 1) {
      pawnCase(1, caseforward, 2, arrayEvent);
      return arrayEvent;
    } else {
      pawnCase(2, caseforward, 1, arrayEvent);
      return arrayEvent;
    }
  }

  /**
   * Method that verifies that the patch is placed in an authorized location.
   * 
   * @param array  : (int[][]) who representing a main board.
   * @param patch  : (Patch) patch the player has purchased.
   * @param line   : (int) integer who represent line of game board.
   * @param column : (int) integer who represent column of game board.
   * 
   * @return boolean : false if your are out of game board and if you are in the
   *         game board.
   **/
  public static boolean verificationofpos(int[][] array, Patch patch, int line, int column) {
    Objects.requireNonNull(patch, "Patch is null");
    if (line < 0 || column < 0 || line > 8 || column > 8) {
      throw new IllegalArgumentException("line < 0 || collumn < 0 ||line > 8 || collumn >8 ");
    }
    var line_cmp = line;
    var column_cmp = column;
    if (line + patch.height() > 9) {
      return false;
    }
    for (var i = 0; i < patch.height(); i++) {
      line_cmp = line_cmp + i;
      for (var j = 0; j < patch.lenghts(); j++) {
        column_cmp = column_cmp + j;
        if (patch.arrayPatch()[i][j] == 1) {
          if (array[line_cmp][column_cmp] == 1) {
            return false;
          }
        }
        column_cmp = column;
      }
      line_cmp = line;
      column_cmp = column;
    }
    return true;
  }

  /**
   * Put the patch on the test playground.
   * 
   * @param tab    : (int[][]) integer 2D who represent the game board.
   * @param line   : (int) integer who represent the line of the game board.
   * @param column : (int) integer who represent the column of the game board.
   * @param patch  : (Patch) patch the player has purchased.
   * 
   * @return int[][] : game board.
   **/
  public static int[][] tab_player_test(int[][] tab, int line, int column, Patch patch) {
    Objects.requireNonNull(patch, "Patch is null");
    Objects.requireNonNull(tab, "tab is null");
    int line_cmp = line;
    int column_cmp = column;
    for (var i = 0; i < patch.height(); i++) {
      line_cmp += i;
      for (var j = 0; j < patch.lenghts(); j++) {
        column_cmp += j;
        if (patch.arrayPatch()[i][j] == 1) {
          tab[line_cmp][column_cmp] = 2;
        }
        column_cmp = column;
      }
      line_cmp = line;
      column_cmp = column;
    }
    return tab;
  }

  /**
   * Method that places the patch on the player’s playing board.
   * 
   * @param patch   : (Patch) patch the player has purchased.
   * @param line    : (int) integer who represent the line of the game board.
   * @param collumn : (int) integer who represent the column of the game board.
   * 
   **/
  public void posepatch(Patch patch, int line, int collumn) {
    Objects.requireNonNull(patch, "Patch is null");
    if (line < 0 || collumn < 0 || line > 8 || collumn > 8) {
      throw new IllegalArgumentException("line < 0 || collumn < 0 ||line > 8 || collumn >8 ");
    }
    int line_cmp = line;
    int column_cmp = collumn;
    for (var i = 0; i < patch.height(); i++) {
      line_cmp += i;
      for (var j = 0; j < patch.lenghts(); j++) {
        column_cmp += j;
        if (patch.arrayPatch()[i][j] == 1) {
          arrayPlayer[line_cmp][column_cmp] = 1;
        }
        column_cmp = collumn;
      }
      line_cmp = line;
      column_cmp = collumn;
    }
    System.out.println("The patch is applied ");
  }

  /**
   * Method that copies the player’s board to its trial game board.
   * 
   * @param source : (int) integer array (2D) who represent the source.
   * @param dest   : (int) integer array (2D) who represent the destination of
   *               source.
   * 
   **/
  public static void copie(int[][] source, int[][] dest) {
    Objects.requireNonNull(source, "source is null");
    Objects.requireNonNull(dest, "dest is null");
    for (var i = 0; i < 9; i++) {
      for (var j = 0; j < 9; j++) {
        dest[i][j] = source[i][j];
      }
    }
  }

  /**
   * Method which checks if the direction in which we are going is not outside the
   * table.
   * 
   * @param direction    : (int) integer who represent the direction that the
   *                     player enter.
   * @param tabReference : (int) integer (2D) array who represent the game board.
   * 
   * @return boolean : (boolean) return true if position is incorrect or false if
   *         is it correct position.
   **/
  public static boolean verificationLimitTab(int direction, int[][] tabReference) {
    Objects.requireNonNull(tabReference, "tabReference is null");
    if (direction < 0) {
      throw new IllegalArgumentException("direction < 0");
    }
    if (direction == 0 || direction == 2) { // haut gauche
      for (var j = 0; j < 9; j++) {
        if ((direction == 0 && tabReference[0][j] == 2) || (direction == 2 && tabReference[j][0] == 2)) {
          return false;
        }
      }
    }
    if (direction == 1 || direction == 3) {// bas droite
      for (var j = 0; j < 9; j++) {
        if ((direction == 1 && tabReference[9 - 1][j] == 2) || (direction == 3 && tabReference[j][9 - 1] == 2)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Method that allows you to place a patch on a patch tray during a game.
   * 
   * @param scanner : (Scanner) Browse a stream and retrieve its content.
   * @param coord   : (int[]) array int 1D which representing the coord x, y from
   *                a direction.
   * @param tab     : (tab[][]) array int 2D which representing the game board
   *                patch of a player.
   */
  public static void patchWantedMoveOnTable(String scanner, int[] coord, int[][] tab) {
    Objects.requireNonNull(scanner, "scanner is null");
    Objects.requireNonNull(coord, "coord is null");
    Objects.requireNonNull(tab, "tab is null");
    if (scanner.equals("z") && verificationLimitTab(0, tab)) {
      coord[0]--;
    } else if (scanner.equals("s") && verificationLimitTab(1, tab)) {
      coord[0]++;
    } else if (scanner.equals("q") && verificationLimitTab(2, tab)) {
      coord[1]--;
    } else if (scanner.equals("d") && verificationLimitTab(3, tab)) {
      coord[1]++;
    }
  }

  /**
   * Method that performs actions on a patch (rotation right/left and reverse)
   * 
   * @param patch   : (Patch) Object that representing a Patch.
   * @param scanner : (Scanner) Browse a stream and retrieve its content.
   * @param coord   : (int[]) array int 1D which representing the coord x, y from
   *                a direction.
   * 
   * @return Patch : (Patch) new patch resulting from a rotation or reserve.
   */
  public static Patch patchWantedRotationOnTable(Patch patch, String scanner, int[] coord) {
    Objects.requireNonNull(coord, "coord is null");
    Objects.requireNonNull(patch, "patch is null");
    Objects.requireNonNull(scanner, "scanner is null");
    if (scanner.equals("l")) {
      coord[0] = 0;
      coord[1] = 0;
      return patch.rotateMatrixLeft();
    }
    if (scanner.equals("m")) {
      coord[0] = 0;
      coord[1] = 0;
      return patch.rotateMatrixRight();
    }
    if (scanner.equals("p")) {
      coord[0] = 0;
      coord[1] = 0;
      return patch.Patchreverse();
    } else {
      return patch;
    }
  }

  /**
   * Method to push the patch in the game board of the player. Returns true if we
   * put the patch and false if we go back.
   * 
   * @param patch   : (Patch) patch the player has purchased.
   * @param scanner : (Scanner) Browse a stream and retrieve its content.
   * 
   * @return boolean : (boolean) Returns true if we put the patch and false if we
   *         go back.
   **/
  public boolean pushpatch(Patch patch, Scanner scanner) {
    Objects.requireNonNull(patch, "Patch is null");
    Objects.requireNonNull(scanner, "scanner is null");
    System.out.println(
        "z : up ; s : down ; q : left; d : right ; l : rotationleft ; m : rotationright ; p : rotationreverse ; w : go back ; t : put ");
    int[] coordPatch = { 0, 0 };
    int[][] tabReference = new int[9][9];
    while (true) {
      copie(arrayPlayer, tabReference);
      displayArrayPlayer(tab_player_test(tabReference, coordPatch[0], coordPatch[1], patch));
      String mouvement = scanner.next();
      if (mouvement.equals("w")) {
        return false;
      }
      patch = patchWantedRotationOnTable(patch, mouvement, coordPatch);
      patchWantedMoveOnTable(mouvement, coordPatch, tabReference);
      if (mouvement.equals("t")) {
        if (verificationofpos(arrayPlayer, patch, coordPatch[0], coordPatch[1]) == true) {
          posepatch(patch, coordPatch[0], coordPatch[1]);
          return true;
        }
      }
    }
  }

  /**
   * Static method thats returns the case who contains value 0.
   * 
   * @param array : (int[][]) Array integer that representing the patch board of a
   *              player.
   * @return int : (int) integer who represent the number of empty case in the
   *         game board of a player.
   */
  public static int spaceEmpty(int[][] array) {
    Objects.requireNonNull(array, "array is null");
    int emptyZoneCounter = 0;
    for (var index1 = 0; index1 < 9; index1++) {
      for (var index2 = 0; index2 < 9; index2++) {
        if (array[index1][index2] == 0) {
          emptyZoneCounter++;
        }
      }
    }
    return emptyZoneCounter;
  }

  /**
   * Static method to print a game board.
   * 
   * @param array : (int[][]) Array integer that representing the patch board of a
   *              player.
   **/
  public static void displayArrayPlayer(int[][] array) {
    Objects.requireNonNull(array, "tab is null");
    for (var x = 0; x < 9; x++) {
      for (var y = 0; y < 9; y++) {
        System.out.print(" " + array[x][y] + " ");
      }
      System.out.println("");
    }
  }

  /**
   * Static method who display the main game board received as a parameter.
   * 
   * @param array : (int[]) Main board game.
   */
  public static void display(int[] array) {
    Objects.requireNonNull(array, "array is null");
    for (var i = 0; i < 81; i++) {
      if ((i % 8 == 0) && (i < 0)) {
        System.out.println(array[i]);
      }
      System.out.print(" " + array[i] + " ");

    }
  }

  /**
   * Static method who print an array received in parameter.
   * 
   * @param array : (int[]) Main board game.
   * @return int[][] : int array (2D) who representing a main patch board in the
   *         game.
   */
  public static int[][] displayArray(int[] array) {
    Objects.requireNonNull(array, "tab is null");
    int[][] arrayDisplay = new int[9][9];
    int pos = 0, column = -1, line = 0, inc = 1;
    for (var i = 0; i < 9; i++) {
      for (var j = 0; j < 9 - i; j++) {
        if (column >= 0) {
          arrayDisplay[line][column] = array[pos];
          pos++;
        }
        column += inc;
      }
      for (var l = 1; l < 9 - i; l++) {
        arrayDisplay[line][column] = array[pos];
        line += inc;
        pos++;
      }
      inc = -inc;
    }
    arrayDisplay[line][column] = array[pos];
    return arrayDisplay;
  }

  /**
   * Accessor method who returns the value distance.
   * 
   * @return int : (int) the field distance.
   **/
  public int getDistance() {
    return distance;
  }

  /**
   * Accessor method who returns the game board.
   * 
   * @return int[][] : (int) the field array.
   **/
  public int[] array() {
    return array;
  }

  /**
   * Accesor method who returns the patch board.
   * 
   * @return int : (int[][]) integer array 2D that representing the patch board of
   *         a player.
   */
  public int[][] arrayPlayer() {
    return arrayPlayer;
  }

  /**
   * Accessor method who returns the value player1.
   * 
   * @return int : (int) the field player1.
   **/
  public int getPlayer1() {
    return player1;
  }

  /**
   * Accessor method who returns the value player2.
   * 
   * @return int : (int) the field player2.
   **/
  public int getPlayer2() {
    return player2;
  }

  /**
   * Setters method for field player2.
   * 
   * @param player1 : (int) integer representing the player2 in the game.
   */
  public void setPlayer1(int player1) {
    this.player1 = player1;
  }

  /**
   * Setters method for field player2.
   * 
   * @param player2 : (int) integer representing the player2 in the game.
   */
  public void setPlayer2(int player2) {
    this.player2 = player2;
  }

  /**
   * Accessor method who return the reverse field value.
   * 
   * @return boolean : value of the field reverse.
   */
  public boolean isReverse() {
    return reverse;
  }

}
