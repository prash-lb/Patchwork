package fr.uge.patchwork;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class representing the Player object. Represents the players and their wallet
 * and their patch board.
 */
public class Player {
  private final Table arrayPlayer;
  private final ArrayList<Patch> arrayPatch;
  private final Money myCount;
  private final String name;

  /**
   * Constructor for Player object. Associate the player with his patch list, his
   * button wallet (from the Money object) and his patch board (from the Tab
   * object).
   * 
   * @param name : String who representing the name of a player.
   */
  public Player(String name) {
    Objects.requireNonNull(name, "name is null");
    this.name = name;
    this.arrayPatch = new ArrayList<Patch>();
    this.myCount = new Money();
    this.arrayPlayer = new Table(new int[81], new int[9][9]);
    myCount.initialiseBankPlayer();

  }

  /**
   * Method that returns a player’s score.
   * 
   * @param patchSpecial : boolean who representing the special patch in the game.
   * @return int : (int) integer who representing the score of a player.
   */
  public int playerScore(boolean patchSpecial) {
    if (patchSpecial) {
      return myCount.getBank() + 49 - (2 * Table.spaceEmpty(arrayPlayer.arrayPlayer()));
    }
    return myCount.getBank() - (2 * Table.spaceEmpty(arrayPlayer.arrayPlayer()));
  }

  /**
   * Check if the player is entitled to have the special patch
   * 
   * @return boolean : true if you can have the patch, else false.
   */
  public boolean patchSpecial() {
    int line = 0;
    int colonne = 0;
    int tour = 0;
    for (var i = 0; i < 9; i++) {
      for (var j = 0; j < 9; j++) {
        if (arrayPlayer.arrayPlayer()[i][j] == 1 && tour == i) {
          line += 1;
          tour += 1;
        }
        if (arrayPlayer.arrayPlayer()[i][j] == 1 && tour > j) {
          line += 1;
        }
        if (arrayPlayer.arrayPlayer()[i][j] == 0) {
          line = 0;
          colonne = 0;
        }
      }
    }
    if (line >= 7 && colonne >= 7) {
      return true;
    }
    return false;
  }

  /**
   * Accessor who return the patch board (arrayPlayer)
   * 
   * @return Tab : (Tab) Object who represent the patch board of a player.
   */
  public Table getArrayPlayer() {
    return arrayPlayer;
  }

  /**
   * Gettors who representing the name of a player during a game.
   * 
   * @return String : String who representing the name of a player in the game.
   */
  public String getName() {
    return name;
  }

  /**
   * gettors who return the patch list (arrayPatch)
   * 
   * @return ArrayList : (ArrayList) Object who represent the patch list of a
   *         player.
   */
  public ArrayList<Patch> getArraypatch() {
    return arrayPatch;
  }

  /**
   * gettors who return the patch list (arrayPatch)
   * 
   * @return Money : (Money) Object who represent the wallet of buttons for a
   *         player.
   */
  public Money getMycount() {
    return myCount;
  }

}
