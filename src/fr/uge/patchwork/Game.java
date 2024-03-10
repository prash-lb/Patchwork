package fr.uge.patchwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class representing the Game object. class grouping all the parameters needed
 * to start a game.
 */
public class Game {

  private final ArrayList<Patch> arrayPatch;
  private final Money bankGeneral;
  private final Table arrayGeneral;
  private int mode;
  private int pionNeutral;

  /**
   * Constuctor who initialize the Game Object. combines an available patch list,
   * a button bank (Money object), a general game board for both players, a game
   * mode and a variable to representer the neutral pawn.
   * 
   * @param mode : int who representing the selected mode at the game start.
   */
  public Game(int mode) {
    this.arrayPatch = new ArrayList<Patch>();
    this.bankGeneral = new Money();
    this.arrayGeneral = new Table(new int[81], new int[9][9]);
    this.mode = mode;
    getArrayGeneral().createmap();
    this.pionNeutral = 0;
  }

  /**
   * Function that changes the numbers if necessary and puts them in a list to be
   * able to collect them later.
   * 
   * @param word      : int who representing the word used to initialize a patch.
   * @param compteur  : int who representing the counter to different case.
   * @param height    : int who representing a height.
   * @param lenght    : int who representing a length.
   * @param bouton    : int who representing a value of a button.
   * @param mouvement : int who representing a movement (choose) in a game.
   * @param cout      : int who representing a cost a patch.
   * @return ArrayList : ArrayList who contains all new parameters.
   */
  public static ArrayList<Integer> helpInitFile(int word, int compteur, int height, int lenght, int bouton,
      int mouvement, int cout) {
    ArrayList<Integer> list = new ArrayList<Integer>();
    if (compteur == 1) {
      height = Integer.parseInt(Character.toString((char) word));
    }
    if (compteur == 2) {
      lenght = Integer.parseInt(Character.toString((char) word));
    } else if (compteur == height * lenght + 3) {
      bouton = Integer.parseInt(Character.toString((char) word));
    } else if (compteur == height * lenght + 4) {
      cout = Integer.parseInt(Character.toString((char) word));
    } else if (compteur == height * lenght + 5) {
      mouvement = Integer.parseInt(Character.toString((char) word));
    }
    list.add(height);
    list.add(lenght);
    list.add(bouton);
    list.add(mouvement);
    list.add(cout);
    return list;
  }

  /**
   * method that initializes patches from a file received as a parameter, and adds
   * them to the patch list of the game.
   * 
   * @param file : (Path) File who contains the different patch of a game.
   * @throws IOException : Exception for a Path (file).
   */
  public void patchInitialisation2(Path file) throws IOException {
    Objects.requireNonNull(file, "File is null");
    int compteur = 0, height = 0, lenght = 0, bouton = 0, mouvement = 0, cout = 0, line = 0, collumn = 0, word;
    int[][] tab = null;
    try (var reader = Files.newBufferedReader(file)) {
      while ((word = reader.read()) != -1) {
        if (Character.toString((char) word).equals(" ") || Character.toString((char) word).equals("\n")) {
          continue;
        } else if (Character.toString((char) word).equals("-")) {
          arrayPatch.add(new Patch(tab, height, lenght, mouvement, cout, bouton));
          tab = null;
          line = 0;
          collumn = 0;
          compteur = -1;
        }
        height = helpInitFile(word, compteur, height, lenght, bouton, mouvement, cout).get(0);
        lenght = helpInitFile(word, compteur, height, lenght, bouton, mouvement, cout).get(1);
        bouton = helpInitFile(word, compteur, height, lenght, bouton, mouvement, cout).get(2);
        mouvement = helpInitFile(word, compteur, height, lenght, bouton, mouvement, cout).get(3);
        cout = helpInitFile(word, compteur, height, lenght, bouton, mouvement, cout).get(4);
        if (compteur == 2) {
          tab = new int[height][lenght];
        }
        if (compteur > 2 && compteur < height * lenght + 3 && collumn > lenght - 1) {
          collumn = 0;
          line++;
        }
        if (compteur > 2 && compteur < height * lenght + 3) {
          tab[line][collumn] = Integer.parseInt(Character.toString((char) word));
          collumn++;
        }
        compteur++;
      }
    }
  }

  /**
   * method that initializes patches and adds them to the patch list of the game.
   * 
   **/
  public void patchInitialisation1() {
    var arraypatch = new int[2][2];
    arraypatch[0][0] = 1;
    arraypatch[0][1] = 1;
    arraypatch[1][0] = 1;
    arraypatch[1][1] = 1;
    var patch1 = new Patch(arraypatch, 2, 2, 4, 3, 1);
    var patch2 = new Patch(arraypatch, 2, 2, 2, 2, 0);
    for (var i = 0; i < 40; i++) {
      if (i % 2 == 0) {
        arrayPatch.add(patch1);
      } else {
        arrayPatch.add(patch2);
      }
    }
  }

  /**
   * Method to initialize a neutral pawn in the game, depending on the selected
   * game mode.
   */
  public void initialisationPionNeutral() {
    if (getMode() == 1) {
      pionNeutral = arrayPatch.size() / 2;
    }
    if (getMode() == 2) {
      var min = arrayPatch.get(0).lenghts() * arrayPatch.get(0).height();
      var position = 0;
      for (var i = 0; i < 33; i++) {
        if (min > arrayPatch.get(i).lenghts() * arrayPatch.get(i).height()) {
          min = arrayPatch.get(i).lenghts() * arrayPatch.get(i).height();
          position = i;
        }
      }
      pionNeutral = position;
    }
  }

  /**
   * Static method who return false if the list of patches is empty, ortherwise
   * false.
   * 
   * @param arrayPatch : (List) patch who contains patches.
   * 
   * @return boolean : boolean who returns state of the List (patch).
   */
  public static boolean verificationIfPatchIsPresent(List<Patch> arrayPatch) {
    Objects.requireNonNull(arrayPatch, "arrayPatch is null");
    if (arrayPatch.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Static method that displays game steps during a game.
   * 
   * @param player    : (Player) Player object who represent a player.
   * @param arrayPlay : (Table) Table object who represent the arrayPatch of a
   *                  Player.
   * @param pion      : (boolean) boolean that represents a player's turn.
   */
  public static void printEarlyGame(Player player, Table arrayPlay, int pion) {
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(arrayPlay, "arrayPlay is null");
    System.out.println("The map : ");
    Table.displayArrayPlayer(Table.displayArray(arrayPlay.array()));
    System.out.println("Player " + pion + " turn : ");
    System.out.println("Your money : " + player.getMycount().getBank());
    System.out.println("Your Patch:");
    for (var i = 0; i < player.getArraypatch().size(); i++) {
      System.out.println(player.getArraypatch().get(i));
    }
    System.out.println("Choose your  action :");
    System.out.println("1 - To advance");
    System.out.println("2 - Buy ");
  }

  /**
   * Method that retrieves a player’s entry about an action to be affected and
   * executes it.
   * 
   * @param player  : (Player) object who represent the player in this game.
   * @param scanner : (Scanner) Browse a stream and retrieve its content.
   * @param pion    : (int) integer who represent the pawn of a player in the
   *                game.
   */
  public void actionPlay(Player player, int pion, Scanner scanner) {
    Objects.requireNonNull(player, "Player is null");
    Objects.requireNonNull(scanner, "scanner is null");
    if (pion < 0) {
      throw new IllegalArgumentException("pion < 0");
    }
    var cpt = 0;
    cpt = printPatch(pionNeutral, arrayPatch);
    while (true) {
      String action = scanner.next();
      switch (action) {
      case "1": {
        Money.pieceCase(getArrayGeneral().getDistance() + 1, bankGeneral, player.getMycount());
        eventDo(getArrayGeneral().Pion(getArrayGeneral().getDistance() + 1, pion), player, pion, scanner);
        return;
      }
      case "2": {
        if (verificationIfPatchIsPresent(arrayPatch)) {
          selectPatch(player, pion, cpt, scanner);
        }
        return;
      }
      default:
        continue;
      }
    }
  }

  /**
   * Static method which allows the installation of patches during a game.
   * 
   * @param player  : (Player) object who represent the player in this game.
   * @param scanner : (Scanner) Browse a stream and retrieve its content.
   */
  public static void posePatch(Player player, Scanner scanner) {
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(scanner, "scanner is null");
    var index = new int[1][1];
    index[0][0] = 1;
    Patch patch = new Patch(index, 1, 1, 0, 0, 0);
    System.out.println("You passed on a cardboard so you can put a cardboard on your land");
    player.getArrayPlayer().pushpatch(patch, scanner);
  }

  /**
   * Method that performs an event according to the position of a player in the
   * game board.
   * 
   * @param arrayEvent : (List) List who represent all event of this game.
   * @param player     : (Player) Objects who represent the player in this game.
   * @param pion       : (int) integer who represent the pawn of a player in the
   *                   game.
   * @param scanner    : (Scanner) Browse a stream and retrieve its content.
   */
  public void eventDo(List<Integer> arrayEvent, Player player, int pion, Scanner scanner) {
    Objects.requireNonNull(scanner, "scanner is null");
    Objects.requireNonNull(arrayEvent, "arrayEvent is null");
    Objects.requireNonNull(player, "player is null");
    if (pion < 0) {
      throw new IllegalArgumentException("pion < 0");
    }
    for (var i = 0; i < arrayEvent.size(); i++) {
      switch (arrayEvent.get(i)) {
      case 3:
        Money.piecePatch(player.getArraypatch(), bankGeneral, player.getMycount());
        break;
      case 4:
        posePatch(player, scanner);
        break;
      case 5:
        endGamePlaye(pion, arrayGeneral);
        break;
      default:
        break;
      }
    }
  }

  /**
   * Method that purchases a patch by a player, and return state of a transaction.
   * 
   * @param Player      : (Player) Objects who represent the player in this game.
   * @param patchWanted : (Patch) Object who represent a patch that the player
   *                    wants to buy.
   * @param pion        : (int) integer who represent the pawn of a player in the
   *                    game.
   * @param scanner     : (Scanner) Browse a stream and retrieve its content.
   * 
   * @return int : (int) Return 0 if the transaction good, and 1 otherwise.
   */
  public int buyPatch(Player Player, Patch patchWanted, int pion, Scanner scanner) {
    Objects.requireNonNull(scanner, "scanner is null");
    Objects.requireNonNull(Player, "Player is null");
    Objects.requireNonNull(patchWanted, " patchWanted is null");
    if (pion < 0) {
      throw new IllegalArgumentException("pion < 0");
    }
    System.out.println("The patch has been purchased you can now put it on");
    if (Player.getArrayPlayer().pushpatch(patchWanted, scanner) == true) {
      Money.achatPiece(patchWanted, Player.getMycount(), bankGeneral);
      Player.getArraypatch().add(patchWanted);
      arrayPatch.remove(patchWanted);
      eventDo(getArrayGeneral().Pion(patchWanted.numberCase(), pion), Player, pion, scanner);
      return 0;
    }
    return 1;
  }

  /**
   * Static method to print all different patch during an purchase.
   * 
   * @param pos        : (int) integer thats represents the position of the
   *                   current patch.
   * @param arrayPatch : (List) List which contains all patches
   * @return int : integer who represents the different patch available.
   */
  public static int printPatch(int pos, List<Patch> arrayPatch) {
    Objects.requireNonNull(arrayPatch, "arrayPatch is null");
    System.out.println("Here are the available patches : ");
    var cpt = 0;
    if (arrayPatch.size() <= 3) {
      for (var i = 0; i < arrayPatch.size(); i++) {
        System.out.println("Patch " + i + 1 + ": ");
        System.out.println(arrayPatch.get(i));
      }
      return cpt;
    }
    for (var i = 1; i < 4; i++) {
      if (pos - i < 0) {
        System.out.println("Patch " + i + ": ");
        System.out.println(arrayPatch.get(arrayPatch.size() - 1 - cpt));
        cpt++;
      } else {
        System.out.println("Patch " + i + ": ");
        System.out.println(arrayPatch.get(pos - i));
      }
    }
    return cpt;
  }

  /**
   * Method that returns the next position index of the received patch as a
   * parameter depending on the choice received.
   * 
   * @param caseWanted : (int) integer representing the index of the next case.
   * @param cpt        : (int) integer representing the number of the neutral
   *                   pawn.
   * @return int : integer who represents the next index position of the patch.
   */
  public int indexOfArrayPatchWanted(int caseWanted, int cpt) {
    switch (caseWanted) {
    case 1:
      if (pionNeutral - 1 < 0) {
        return arrayPatch.size() - 1;
      } else {
        return pionNeutral - 1;
      }
    case 2:
      if (pionNeutral - 2 < 0) {
        if (cpt == 3) {
          return arrayPatch.size() - 2;
        }
        if (cpt == 2) {
          return arrayPatch.size() - 1;
        }
      } else {
        return pionNeutral - 2;
      }
    case 3:
      if (pionNeutral - 3 < 0) {
        if (cpt == 3) {
          return arrayPatch.size() - 3;
        }
        if (cpt == 2) {
          return arrayPatch.size() - 2;
        }
        if (cpt == 1) {
          return arrayPatch.size() - 1;
        }
      } else {
        return pionNeutral - 3;
      }
    default:
      return 0;
    }
  }

  /**
   * Method allowing a player to select a patch from a selection of patches from a
   * list of patches.
   * 
   * @param player  : (Player) Objects who represent the player in this game.
   * @param pion    : (int) integer who represent the pawn of a player in the
   *                game.
   * @param cpt     : (int) integer representing the number of the neutral pawn.
   * @param scanner : (Scanner) Browse a stream and retrieve its content.
   */
  public void selectPatch(Player player, int pion, int cpt, Scanner scanner) {
    Objects.requireNonNull(player, "Player is null");
    Objects.requireNonNull(scanner, "scanner is null");
    if (pion < 0) {
      throw new IllegalArgumentException("pion < 0");
    }
    System.out
        .println("Choose your Patch  1 for the first 2 for the second and 3 for the tird and finally 4 to go back :");
    while (true) {
      String patchWant = scanner.next();
      if (patchWant.equals("1") || patchWant.equals("2") || patchWant.equals("3")) {
        var index = indexOfArrayPatchWanted(Integer.parseInt(patchWant), cpt);
        if (Money.verificationMoneyTransfert(arrayPatch.get(index).numberPiece(), player.getMycount())) {
          if (buyPatch(player, arrayPatch.get(index), pion, scanner) == 0) {
            pionNeutral = index;
            return;
          }
          return;
        }
        System.out.println("You don't have enought money");
      }
      if (patchWant.equals("4")) {
        return;
      }
    }
  }

  /**
   * Static method who convert the button of players in "1" to mark the end of the
   * game.
   * 
   * @param pion  : (int) integer who represent the pawn of a player in the game.
   * @param array : (Table) Objects representing the array game.
   */
  public static void endGamePlaye(int pion, Table array) {
    Objects.requireNonNull(array, "array is null");
    if (pion < 0) {
      throw new IllegalArgumentException("pion < 0");
    }
    if (pion == 1) {
      array.setPlayer1(1);
    } else {
      array.setPlayer2(1);

    }
  }

  /**
   * Method who return the winner of a game.
   * 
   * @param j1           : (Player) Object representing a player 1 during a game.
   * @param j2           : (Player) Object representing a player 2 during a game.
   * @param patchSpecial : (Integer) representing which of the player have th
   *                     patch bonus(7*7) .
   * 
   */
  static void winnerOfGame(Player player1, Player player2, int patchSpecial) {
    Objects.requireNonNull(player1, "player1 is null");
    Objects.requireNonNull(player2, "player2 is null");
    int scorePlayer1 = 0;
    int scorePlayer2 = 0;
    if (patchSpecial == 1) {
      scorePlayer1 = player1.playerScore(true);
      scorePlayer2 = player2.playerScore(false);
    }
    if (patchSpecial == 2) {
      scorePlayer1 = player1.playerScore(false);
      scorePlayer2 = player2.playerScore(true);
    }
    if (patchSpecial == 0) {
      scorePlayer1 = player1.playerScore(false);
      scorePlayer2 = player2.playerScore(false);
    }
    if (scorePlayer1 == scorePlayer2) {
      System.out.println("Egalite");
      return;
    }
    var winner = (scorePlayer1 < scorePlayer2) ? "Player 2" : "Player 1";
    System.out.println("The winner is the " + winner);
  }

  /**
   * Function that initializes a terminal game.
   * 
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param player1 : Variable who representing a Player during a graphical game.
   * @param player2 : Variable who representing a Player during a graphical game.
   * @param scanner : Variable of a Scanner type who contains the value enter by
   *                an user.
   */
  public static void runTerminal(Game game, Player player1, Player player2, Scanner scanner) {
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player1, "player1 is null");
    Objects.requireNonNull(player2, "player2 is null");
    Objects.requireNonNull(scanner, "scanner is null");
    int patchSpecial = 0;
    while (game.getArrayGeneral().getPlayer1() != 1 && game.getArrayGeneral().getPlayer2() != 1) {
      if (game.getArrayGeneral().isReverse() == false) {
        Game.printEarlyGame(player1, game.getArrayGeneral(), 1);
        game.actionPlay(player1, 1, scanner);
        if (player1.patchSpecial() && patchSpecial == 0) {
          patchSpecial = 1;
        }
      }
      if (game.getArrayGeneral().isReverse() == true) {
        Game.printEarlyGame(player2, game.getArrayGeneral(), 2);
        game.actionPlay(player2, 2, scanner);
        if (player2.patchSpecial() && patchSpecial == 0) {
          patchSpecial = 2;
        }
      }
    }
    Game.winnerOfGame(player1, player2, patchSpecial);
  }

  /**
   * Redefining of toString method to fit the display of a Game.
   * 
   * @return String : Containing the Game information to display.
   **/
  @Override
  public String toString() {
    var builder = new StringBuilder();
    for (var l : arrayPatch) {
      builder.append(l);
    }
    return builder.toString();
  }

  /**
   * Accessor method that returns the selected game mode.
   * 
   * @return int : integer representing the selected game mode.
   */
  public int getMode() {
    return mode;
  }

  /**
   * Accessor method to call the main game board.
   * 
   * @return Table : main game board
   */
  public Table getArrayGeneral() {
    return arrayGeneral;
  }

  /**
   * Accessor method to call the patch board.
   * 
   * @return ArrayList : the arrayList of all patch.
   */
  public ArrayList<Patch> getArrayPatch() {
    return arrayPatch;
  }

  /**
   * Assessor method to call the bank general.
   * 
   * @return Money : Money type who representing the main bank.
   */
  public Money getbankGeneral() {
    return bankGeneral;
  }

  /**
   * Gettos who representing the neutral pawn during a game.
   * 
   * @return int : int representing the value of the neutral pawn in the game.
   */
  public int getPionNeutral() {
    return pionNeutral;
  }

  /**
   * Settors who change the value of the neutral pawn during a game.
   * 
   * @param pionNeutral : int who representing the new value of the neutral pawn
   *                    in the game.
   */
  public void setPionNeutral(int pionNeutral) {
    this.pionNeutral = pionNeutral;
  }

}
