package fr.uge.patchwork.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

import fr.uge.patchwork.Game;
import fr.uge.patchwork.Patch;
import fr.uge.patchwork.Player;
import fr.uge.patchwork.Table;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;

/**
 * Class representing frame during a graphic.
 *
 */
public class Frame {

  /**
   * Constructor for Frame class.
   */
  public Frame() {
  }

  /**
   * displays an arrow at the bottom of the game window in a graphic game.
   * 
   * @param graphics : variable of graphic type.
   * @param context  : variable of ApplicationContext context.
   * @param line     : int representing line in graphic game.
   */
  static void printArrow(Graphics graphics, ApplicationContext context, int line) {
    Objects.requireNonNull(graphics, "graphics is null");
    Objects.requireNonNull(context, "contet is null");
    if (line < 0 && line > (int) context.getScreenInfo().getWidth()) {
      throw new IllegalArgumentException("line < 0 && line > (int)context.getScreenInfo().getWidth()");
    }
    graphics.drawLine(30, (int) context.getScreenInfo().getWidth() / 2 + 100, line,
        (int) context.getScreenInfo().getWidth() / 2 + 100);
    graphics.drawLine(30, (int) context.getScreenInfo().getWidth() / 2 + 80, 30,
        (int) context.getScreenInfo().getWidth() / 2 + 120);
    graphics.drawLine(30, (int) context.getScreenInfo().getWidth() / 2 + 80, 30 - 20,
        (int) context.getScreenInfo().getWidth() / 2 + 100);
    graphics.drawLine(30, (int) context.getScreenInfo().getWidth() / 2 + 120, 30 - 20,
        (int) context.getScreenInfo().getWidth() / 2 + 100);
  }

  /*
   * affiche les cases du plateau de jeu par rapport aux position avec les indice
   * i et j et va mettre la couleur associe aux element
   */
  /**
   * displays the squares of the game board in relation to the position of the
   * indices i, j received in parameter and the color.
   * 
   * @param context   : Variable of ApplicationContext type.
   * @param i         : int representing the ord value.
   * @param j         : int representing the abs value.
   * @param color     : Variable who representing color (Color type)
   * @param rectangle : variable representing a boolean.
   */
  static void paintElementInMap(ApplicationContext context, int i, int j, Color color, boolean rectangle) {
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(color, "color1 is null");
    if (i < 0 || j < 0 || i > 9 || j > 9) {
      throw new IllegalArgumentException("i and j are not between 0 and 9");
    }
    context.renderFrame(graphics -> {
      graphics.setColor(color);
      if (rectangle) {
        graphics.fillRect(j * 75, 125 + i * 75, 75, 75);
      } else {
        graphics.setColor(Color.black);
        graphics.fillRect(j * 75, 125 + i * 75, 75, 75);
        graphics.setColor(color);
        graphics.fillOval(j * 75, 125 + i * 75, 75, 75);
      }
      graphics.setColor(Color.red);
      graphics.drawRect(j * 75, 125 + i * 75, 75, 75);
    });

  }

  /**
   * displays the 81 squares of the game's general boards in relation to the
   * elements contained in the array.
   * 
   * @param context : Variable of ApplicationContext type.
   * @param array   : array who representing the game array (int 2D).
   */
  static void printTableGeneral(ApplicationContext context, int[][] array) {
    Objects.requireNonNull(array, "array is null");
    Objects.requireNonNull(context, "context is null");
    for (var i = 0; i < 9; i++) {
      for (var j = 0; j < 9; j++) {
        if (array[i][j] == 1) {
          paintElementInMap(context, i, j, Color.red, false);
        }
        if (array[i][j] == 2) {
          paintElementInMap(context, i, j, Color.green, false);
        }
        if (array[i][j] == 6) {
          paintElementInMap(context, i, j, Color.yellow, false);
        }
        if (array[i][j] == 3) {
          paintElementInMap(context, i, j, Color.blue, false);
        }
        if (array[i][j] == 4) {
          paintElementInMap(context, i, j, Color.gray, true);
        }
        if (array[i][j] == 0) {
          paintElementInMap(context, i, j, Color.black, true);
        }
        if (array[i][j] == 5) {
          paintElementInMap(context, i, j, Color.white, true);
        }
      }
    }
  }

  /**
   * Displays the player's patches on the right of the screen.
   * 
   * @param graphics : variable of graphic type.
   * @param context  : variable of ApplicationContext context.
   * @param player   : Variable who representing a player during a game.
   */
  static void printPatchPlayer(Graphics graphics, ApplicationContext context, Player player) {
    Objects.requireNonNull(graphics, "graphics is null");
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(context, "contet is null");
    int line = (int) context.getScreenInfo().getWidth() / 2 + 100;
    int col = 400;
    graphics.setColor(Color.RED);
    graphics.setFont(new Font("TimesRoman ", Font.BOLD, 20));
    graphics.drawString("Patch of the player : ", (int) context.getScreenInfo().getWidth() / 2 + 100, 300);
    for (var i = 0; i < player.getArraypatch().size(); i++) {
      ;
      if (line >= (int) context.getScreenInfo().getWidth()) {
        line = (int) context.getScreenInfo().getWidth() / 2 + 100;
        col += 100;
      }
      printPatch(graphics, context, line, col, player.getArraypatch().get(i));
      line += player.getArraypatch().get(i).lenghts() * 12 + 50;
    }
  }

  /**
   * Displays the name of the player his money and the title.
   * 
   * @param graphics : variable of graphic type.
   * @param context  : variable of ApplicationContext context.
   * @param pion     : int who representing value of piece (whose player) during a
   *                 game
   * @param player   : Variable who representing a player during a game.
   */
  static void printPlayerInformation(Graphics graphics, int pion, Player player, ApplicationContext context) {
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(graphics, "graphics is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion != 2");
    }
    graphics.setFont(new Font("TimesRoman ", Font.BOLD, 30));
    if (pion == 1) {
      graphics.setColor(Color.red);
    } else {
      graphics.setColor(Color.green);
    }
    graphics.drawString("TURN OF : " + player.getName(), 0, 100);
    graphics.setColor(Color.WHITE);
    graphics.drawString("MAP GENERAL ", (int) context.getScreenInfo().getWidth() / 2 - 150, 50);
    graphics.drawString("BUTTON :  " + player.getMycount().getBank(), (int) context.getScreenInfo().getWidth() - 300,
        100);
  }

  /**
   * Displays the window with all the functions that displays used in relation to
   * the different stages (purchase of patches)(apply patch) (choice of action).
   * 
   * @param game       : Representing all element during a game, variable of Game
   *                   type.
   * @param context    : variable of ApplicationContext context.
   * @param pion       : int who representing value of piece (whose player) during
   *                   a game
   * @param player     : Variable who representing a player during a game.
   * @param patchArray : (int 2D) who representing a patchArrray in the game.
   * @param buy        : boolean representing if a player have bought a patch.
   * @param posePatch  : boolean representing if the patch can be put or not.
   */
  static void tablePrint(Game game, ApplicationContext context, Player player, int[][] patchArray, boolean buy,
      boolean posePatch, int pion) {
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(patchArray, "patchArray is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion != 2");
    }
    context.renderFrame(graphics -> {
      printPlayerInformation(graphics, pion, player, context);
      printTableGeneral(context, Table.displayArray(game.getArrayGeneral().array()));
      printPatchLine(game, context);
      if (buy) {
        printChoicePossiblePhase2(context, PatchPossible(game));
      }
      if (!buy && !posePatch) {
        printChoicePossiblePhase1(context);
        printPatchPlayer(graphics, context, player);
      }
      if (posePatch) {
        printTablePlayer(player.getName(), patchArray, context);
        printTouchMapPlayer(graphics, context);
      }
      if (!posePatch) {
        printTablePlayer(player.getName(), player.getArrayPlayer().arrayPlayer(), context);
      }

    });
  }

  /**
   * Secondary function which displays the patches used in other functions and
   * puts the buttons on them (blue box).
   * 
   * @param graphics : variable of graphic type.
   * @param context  : variable of ApplicationContext context.
   * @param i        : int representing the ord value.
   * @param j        : int representing the abs value.
   * @param patch    : Variable of Patch type who representing a patch during a
   *                 game.
   */
  static void printPatch(Graphics graphics, ApplicationContext context, int i, int j, Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    Objects.requireNonNull(graphics, "graphics is null");
    Objects.requireNonNull(context, "context is null");
    if (i < 0 || j < 0 || i > (int) context.getScreenInfo().getWidth()
        || j > (int) context.getScreenInfo().getHeight()) {
      throw new IllegalArgumentException("i and j are not in the screen");
    }
    int line = i;
    int bouton = patch.bouton();
    for (var height = 0; height < patch.height(); height++) {
      for (var width = 0; width < patch.lenghts(); width++) {
        if (patch.arrayPatch()[height][width] == 1) {
          graphics.setColor(Color.white);
          if (bouton > 0) {
            graphics.setColor(Color.blue);
            bouton--;
          }
          graphics.fillRect(line, j, 12, 12);
          graphics.setColor(Color.red);
          graphics.drawRect(line, j, 12, 12);
        }
        line += 12;
      }
      line = i;
      j += 12;
    }
  }

  /**
   * Function that puts the black screen back before displaying something new.
   * 
   * @param context : variable of ApplicationContext context.
   */
  static void reset(ApplicationContext context) {
    Objects.requireNonNull(context, "context is null");
    ScreenInfo screenInfo = context.getScreenInfo();
    float width = screenInfo.getWidth();
    float height = screenInfo.getHeight();
    context.renderFrame(graphics -> {
      graphics.setColor(Color.BLACK);
      graphics.fill(new Rectangle2D.Float(0, 0, width, height));
    });
  }

  /**
   * Function that gives us a list of possible patches to play.
   * 
   * @param game : Representing all element during a game, variable of Game type.
   * @return ArrayList : ArrayList who representing a patch during a game.
   */
  static ArrayList<Patch> PatchPossible(Game game) {
    Objects.requireNonNull(game, "game is null");
    ArrayList<Patch> listOfPatchPossible = new ArrayList<Patch>();
    var cpt = 0;
    if (game.getArrayPatch().size() < 3) {
      for (var i = 0; i < game.getArrayPatch().size(); i++) {
        listOfPatchPossible.add(game.getArrayPatch().get(i));
      }
      return listOfPatchPossible;
    }
    for (var i = 0; i < 3; i++) {
      if (game.getPionNeutral() - i < 0) {
        listOfPatchPossible.add(game.getArrayPatch().get(game.getArrayPatch().size() - 1 - cpt));
        cpt++;
      } else {
        listOfPatchPossible.add(game.getArrayPatch().get(game.getPionNeutral() - i));
      }
    }
    return listOfPatchPossible;
  }

  /**
   * Display advance and buy in graphic game.
   * 
   * @param context : variable of ApplicationContext context.
   */
  static void printChoicePossiblePhase1(ApplicationContext context) {
    Objects.requireNonNull(context, "context is null");
    context.renderFrame(graphics -> {
      graphics.setColor(Color.green);
      graphics.setFont(new Font("TimesRoman ", Font.BOLD, 20));
      graphics.drawString("A : ADVANCE  ", (int) context.getScreenInfo().getWidth() / 2 - 100, 500);
      graphics.drawString("B : BUY PATCH  ", (int) context.getScreenInfo().getWidth() / 2 - 100, 600);
    });

  }

  /**
   * Display the choice of patches and return back during a graphic game.
   * 
   * @param context : variable of ApplicationContext context.
   * @param array   : array who representing a patch array in the game, ArrayList
   *                type.
   */
  static void printChoicePossiblePhase2(ApplicationContext context, ArrayList<Patch> array) {
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(array, "array is null");
    context.renderFrame(graphics -> {
      graphics.setColor(Color.red);
      graphics.setFont(new Font("TimesRoman ", Font.BOLD, 20));
      graphics.drawString("all keys : Return  ", (int) context.getScreenInfo().getWidth() - 300, 300);
      if (array.size() >= 1) {
        graphics.drawString("A : Patch 0  ", (int) context.getScreenInfo().getWidth() - 300, 400);
      }
      if (array.size() >= 2) {
        graphics.drawString("B : Patch 1  ", (int) context.getScreenInfo().getWidth() - 300, 500);
      }
      if (array.size() >= 3) {
        graphics.drawString("C : Patch 2  ", (int) context.getScreenInfo().getWidth() - 300, 600);
      }
    });
  }

  /**
   * Displays all available patches on the bottom line.
   * 
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param context : variable of ApplicationContext context.
   */
  static void printPatchLine(Game game, ApplicationContext context) {
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(context, "context is null");
    context.renderFrame(graphics -> {
      graphics.setColor(Color.red);
      graphics.setFont(new Font("TimesRoman ", Font.BOLD, 20));
      graphics.drawString("Patch avaible : ", 0, (int) context.getScreenInfo().getHeight() / 2 + 300);
      ArrayList<Patch> possible = PatchPossible(game);
      var line = 10;
      for (var i = 0; i < game.getArrayPatch().size(); i++) {
        if (possible.contains(game.getArrayPatch().get(i))) {
          graphics.setFont(new Font("TimesRoman ", Font.BOLD, 15));
          graphics.drawString("" + possible.indexOf(game.getArrayPatch().get(i)), line,
              (int) context.getScreenInfo().getWidth() / 2 - 20);
        }
        printPatch(graphics, context, line, (int) context.getScreenInfo().getWidth() / 2, game.getArrayPatch().get(i));
        graphics.setFont(new Font("TimesRoman ", Font.BOLD, 10));
        graphics.drawString("Cost:" + game.getArrayPatch().get(i).numberPiece(), line,
            (int) context.getScreenInfo().getWidth() / 2 + game.getArrayPatch().get(i).height() * 12 + 15);
        graphics.drawString("Move:" + game.getArrayPatch().get(i).numberCase(), line,
            (int) context.getScreenInfo().getWidth() / 2 + game.getArrayPatch().get(i).height() * 12 + 30);
        line += game.getArrayPatch().get(i).lenghts() * 12 + 19;
        printArrow(graphics, context, line);
      }
    });
  }

  /**
   * Displays the keys to move the patches on the player's patching field
   * 
   * @param graphics : variable of graphic type.
   * @param context  : variable of ApplicationContext context.
   */
  static void printTouchMapPlayer(Graphics graphics, ApplicationContext context) {
    Objects.requireNonNull(graphics, "graphics is null");
    Objects.requireNonNull(context, "context is null");
    graphics.setColor(Color.white);
    graphics.setFont(new Font("TimesRoman ", Font.BOLD, 12));
    graphics.drawString(
        "Z : up ; S : down ; Q : left; D : right ; L : rotationleft ; M : rotationright ; P : rotationreverse ; W : go back ; T : put ",
        (int) context.getScreenInfo().getWidth() / 2 - 150 + 9 * 30, 175);
  }

  /**
   * Displays the player's patch map to the right of the graphics window.
   * 
   * @param name    : String who representing a name of a player.
   * @param array   : array (2D) who representing patch map of a player.
   * @param context : variable of ApplicationContext context.
   */
  static void printTablePlayer(String name, int[][] array, ApplicationContext context) {
    Objects.requireNonNull(name, "name is null");
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(array, "array is null");
    context.renderFrame(graphics -> {
      graphics.setFont(new Font("TimesRoman ", Font.BOLD, 20));
      graphics.drawString("MAP OF " + name, (int) context.getScreenInfo().getWidth() / 2 - 150, 150);
      for (var i = 0; i < 9; i++) {
        for (var j = 0; j < 9; j++) {
          if (array[i][j] == 0) {
            graphics.setColor(Color.white);
            graphics.fillRect((int) context.getScreenInfo().getWidth() / 2 - 150 + j * 25, 175 + i * 25, 25, 25);
          }
          if (array[i][j] == 1) {
            graphics.setColor(Color.BLUE);
            graphics.fillRect((int) context.getScreenInfo().getWidth() / 2 - 150 + j * 25, 175 + i * 25, 25, 25);
          }
          graphics.setColor(Color.black);
          graphics.drawRect((int) context.getScreenInfo().getWidth() / 2 - 150 + j * 25, 175 + i * 25, 25, 25);
        }
      }
    });
  }

  /**
   * Displays the winner at the end of a graphical game.
   * 
   * @param player1      : Variable who representing a Player during a graphical
   *                     game.
   * @param player2      : Variable who representing a Player during a graphical
   *                     game.
   * @param context      : variable of ApplicationContext context.
   * @param patchSpecial : int who representing the special patch 7*7
   */
  static void printWinner(Player player1, Player player2, ApplicationContext context, int patchSpecial) {
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(player1, "player1 is null");
    Objects.requireNonNull(player2, "player2 is null");
    context.renderFrame(graphics -> {
      graphics.setFont(new Font("TimesRoman ", Font.BOLD, 30));
      graphics.setColor(Color.GREEN);
      int scorePlayer1 = 0, scorePlayer2 = 0;
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
        System.out.println();
        graphics.drawString("EQUALITY with a score of : " + scorePlayer1, 40, 40);
        return;
      }
      var winner = (scorePlayer1 < scorePlayer2 ? "WINNER : " + player2.getName() + " with the score of " + scorePlayer2
          : "WINNER : " + player1.getName() + " with the score of " + scorePlayer1);
      graphics.drawString(winner, (int) context.getScreenInfo().getWidth() / 2 - 300,
          (int) context.getScreenInfo().getHeight() / 2);
    });
  }

}
