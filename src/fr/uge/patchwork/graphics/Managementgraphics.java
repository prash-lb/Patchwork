package fr.uge.patchwork.graphics;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

import fr.uge.patchwork.Game;
import fr.uge.patchwork.Money;
import fr.uge.patchwork.Patch;
import fr.uge.patchwork.Player;
import fr.uge.patchwork.Table;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

/**
 * Class manage several element of a graphical game. And support the events.
 * (eventually make an interface)
 */
public class Managementgraphics {

  /**
   * Constructor for Managementgraphics class.
   */
  public Managementgraphics() {
  }

  /**
   * Graphical event management.
   * 
   * @param action     : Variable of Action type.
   * @param touche     : Event to representing KeyboardKey type.
   * @param arrayEvent : List who representing different event in graphical game.
   * @param context    : variable of ApplicationContext context.
   * @param player     : Variable who representing a player during a game.
   * @param game       : Representing all element during a game, variable of Game
   *                   type.
   * @param pion       : int who representing value of piece (whose player) during
   *                   a game
   */
  static void eventDoGraphics(Action action, KeyboardKey touche, List<Integer> arrayEvent, ApplicationContext context,
      Player player, Game game, int pion) {
    Objects.requireNonNull(arrayEvent, "arrayEvent is null");
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(touche, "touche is null");
    Objects.requireNonNull(action, "action is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion != 2");
    }
    for (var i = 0; i < arrayEvent.size(); i++) {
      switch (arrayEvent.get(i)) {
      case 3:
        Money.piecePatch(player.getArraypatch(), game.getbankGeneral(), player.getMycount());
        break;
      case 4:
        var index = new int[1][1];
        index[0][0] = 1;
        pushPatchGraphics(action, touche, new Patch(index, 1, 1, 0, 0, 0), context, game, player, pion);
        break;
      case 5:
        Game.endGamePlaye(pion, game.getArrayGeneral());
        break;
      default:
        break;
      }
    }
  }

  /**
   * Patch rotation relative to keyboard key events.
   * 
   * @param patch  : Variable who representing a patch during a game.
   * @param action : Variable of Action type.
   * @param touche : Event to representing KeyboardKey type.
   * @param coord  : int array who representing the coord of a patch.
   * @return Patch : new Patch who having suffered a rotation.
   */
  static Patch patchWantedRotationOnTableGraphics(Patch patch, Action action, KeyboardKey touche, int[] coord) {
    Objects.requireNonNull(coord, "coord is null");
    Objects.requireNonNull(patch, "patch is null");
    Objects.requireNonNull(touche, "touche is null");
    Objects.requireNonNull(action, "action is null");
    if (action == Action.KEY_PRESSED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.L) {
      coord[0] = 0;
      coord[1] = 0;
      return patch.rotateMatrixLeft();
    }
    if (action == Action.KEY_PRESSED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.M) {
      coord[0] = 0;
      coord[1] = 0;
      return patch.rotateMatrixRight();
    }
    if (action == Action.KEY_PRESSED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.P) {
      coord[0] = 0;
      coord[1] = 0;
      return patch.Patchreverse();
    } else {
      return patch;
    }
  }

  /**
   * Move the patch with graphic actions during a game.
   * 
   * @param action : Variable of Action type.
   * @param touche : Event to representing KeyboardKey type.
   * @param coord  : int array who representing the coord of a patch.
   * @param tab    : int array (2D) representing a main game board during a game.
   */
  static void patchWantedMoveOnTableGraphics(Action action, KeyboardKey touche, int[] coord, int[][] tab) {
    Objects.requireNonNull(action, "action is null");
    Objects.requireNonNull(touche, "touche is null");
    Objects.requireNonNull(coord, "coord is null");
    Objects.requireNonNull(tab, "tab is null");
    if (action == Action.KEY_PRESSED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.Z
        && Table.verificationLimitTab(0, tab)) {
      coord[0]--;
    } else if (action == Action.KEY_PRESSED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.S
        && Table.verificationLimitTab(1, tab)) {
      coord[0]++;
    } else if (action == Action.KEY_PRESSED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.Q
        && Table.verificationLimitTab(2, tab)) {
      coord[1]--;
    } else if (action == Action.KEY_PRESSED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.D
        && Table.verificationLimitTab(3, tab)) {
      coord[1]++;
    }
  }

  /*
   * va gere toutes les action avec le patch sur le terrai du joeur va verifier si
   * c est bon et si on appui t alors on retourne en arrier
   */
  /**
   * 
   * 
   * @param action  : Variable of Action type.
   * @param touche  : Event to representing KeyboardKey type.
   * @param patch   : Variable who representing a patch during a game.
   * @param context : variable of ApplicationContext context.
   * @param player  : Variable who representing a player during a game.
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param pion    : int who representing value of piece (whose player) during a
   *                game
   * @return boolean : boolean representing if the patch is put in the player
   *         patch board.
   */
  static boolean pushPatchGraphics(Action action, KeyboardKey touche, Patch patch, ApplicationContext context,
      Game game, Player player, int pion) {
    Objects.requireNonNull(action, "action is null");
    Objects.requireNonNull(touche, "touche is null");
    Objects.requireNonNull(patch, "Patch is null");
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(context, "context is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion != 2");
    }
    int[] coordPatch = { 0, 0 };
    int[][] tabReference = new int[9][9];
    Event event = null;
    while (true) {
      event = context.pollOrWaitEvent(50);
      while (event == null) {
        event = context.pollOrWaitEvent(50);
      }
      touche = event.getKey();
      action = event.getAction();
      Table.copie(player.getArrayPlayer().arrayPlayer(), tabReference);
      Frame.reset(context);
      Frame.tablePrint(game, context, player, Table.tab_player_test(tabReference, coordPatch[0], coordPatch[1], patch),
          false, true, pion);
      if (action == Action.KEY_RELEASED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.W) {
        return false;
      }
      patch = patchWantedRotationOnTableGraphics(patch, action, touche, coordPatch);
      patchWantedMoveOnTableGraphics(action, touche, coordPatch, tabReference);
      if (action == Action.KEY_RELEASED && KeyboardKey.valueOf(touche.name()) == KeyboardKey.T) {
        if (Table.verificationofpos(player.getArrayPlayer().arrayPlayer(), patch, coordPatch[0],
            coordPatch[1]) == true) {
          player.getArrayPlayer().posepatch(patch, coordPatch[0], coordPatch[1]);
          return true;
        }
      }
    }
  }

  /**
   * If the player has put on his patch then we will transfer the currency and the
   * necessary patch (as long as possible).
   * 
   * @param action  : Variable of Action type.
   * @param touche  : Event to representing KeyboardKey type.
   * @param patch   : Variable who representing a patch during a game.
   * @param context : variable of ApplicationContext context.
   * @param player  : Variable who representing a player during a game.
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param pion    : int who representing value of piece (whose player) during a
   *                game
   */
  static void buyPatchGraphics(Action action, KeyboardKey touche, Patch patch, Game game, Player player,
      ApplicationContext context, int pion) {
    Objects.requireNonNull(action, "action is null");
    Objects.requireNonNull(touche, "touche is null");
    Objects.requireNonNull(patch, "patch is null");
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(context, "context is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion!=2");
    }
    if (pushPatchGraphics(action, touche, patch, context, game, player, pion) == true) {
      Money.achatPiece(patch, player.getMycount(), game.getbankGeneral());
      player.getArraypatch().add(patch);
      game.setPionNeutral(game.getArrayPatch().indexOf(patch) - 1);
      game.getArrayPatch().remove(patch);
      eventDoGraphics(action, touche, game.getArrayGeneral().Pion(patch.numberCase(), pion), context, player, game,
          pion);
    }
  }

  /**
   * Function offering the choice of patches during a graphic part.
   * 
   * @param action  : Variable of Action type.
   * @param touche  : Event to representing KeyboardKey type.
   * @param context : variable of ApplicationContext context.
   * @param player  : Variable who representing a player during a game.
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param pion    : int who representing value of piece (whose player) during a
   *                game
   */
  static void choicePatchGraphics(Action action, KeyboardKey touche, ApplicationContext context, Game game,
      Player player, int pion) {
    Objects.requireNonNull(action, "action is null");
    Objects.requireNonNull(touche, "touche is null");
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player, "player is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion != 2");
    }
    if (game.getArrayPatch().size() >= 1 && action == Action.KEY_PRESSED
        && KeyboardKey.valueOf(touche.name()) == KeyboardKey.A
        && Money.verificationMoneyTransfert(Frame.PatchPossible(game).get(0).numberPiece(), player.getMycount())) {
      buyPatchGraphics(action, touche, Frame.PatchPossible(game).get(0), game, player, context, pion);
      return;
    }
    if (game.getArrayPatch().size() >= 2 && action == Action.KEY_PRESSED
        && KeyboardKey.valueOf(touche.name()) == KeyboardKey.B
        && Money.verificationMoneyTransfert(Frame.PatchPossible(game).get(1).numberPiece(), player.getMycount())) {
      buyPatchGraphics(action, touche, Frame.PatchPossible(game).get(1), game, player, context, pion);
      return;
    }
    if (game.getArrayPatch().size() >= 3 && action == Action.KEY_PRESSED
        && KeyboardKey.valueOf(touche.name()) == KeyboardKey.C
        && Money.verificationMoneyTransfert(Frame.PatchPossible(game).get(2).numberPiece(), player.getMycount())) {
      buyPatchGraphics(action, touche, Frame.PatchPossible(game).get(2), game, player, context, pion);
    }
  }

  /**
   * Function proposing the confirmation of the choice of a patch during a graphic
   * part and to go back possible.
   * 
   * @param action  : Variable of Action type.
   * @param touche  : Event to representing KeyboardKey type.
   * @param context : variable of ApplicationContext context.
   * @param player  : Variable who representing a player during a game.
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param pion    : int who representing value of piece (whose player) during a
   *                game
   */
  static void choiceGraphicPhase2(Action action, KeyboardKey touche, ApplicationContext context, Game game,
      Player player, int pion) {
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player, "player is null");
    Objects.requireNonNull(action, "action is null");
    Objects.requireNonNull(touche, "touche is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion != 2");
    }
    Frame.reset(context);
    Frame.tablePrint(game, context, player, new int[9][9], true, false, pion);
    Event event = context.pollOrWaitEvent(50);
    event = null;
    while (event == null) {
      event = context.pollOrWaitEvent(50);
    }
    touche = event.getKey();
    action = event.getAction();
    choicePatchGraphics(action, touche, context, game, player, pion);
  }

  /**
   * Function proposing actions on the possible action choices.
   * 
   * @param context : variable of ApplicationContext context.
   * @param player  : Variable who representing a player during a game.
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param pion    : int who representing value of piece (whose player) during a
   *                game
   */
  static void choiceGraphicPhase1(ApplicationContext context, Game game, Player player, int pion) {
    Objects.requireNonNull(context, "context is null");
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player, "player is null");
    if (pion != 1 && pion != 2) {
      throw new IllegalArgumentException("pion != 1 && pion != 2");
    }
    Event event = context.pollOrWaitEvent(50);
    event = null;
    while (event == null) {
      event = context.pollOrWaitEvent(50);
    }
    KeyboardKey touche = event.getKey();
    Action action = event.getAction();
    if ((action == Action.KEY_RELEASED) && KeyboardKey.valueOf(touche.name()) == KeyboardKey.A) {
      Money.pieceCase(game.getArrayGeneral().getDistance() + 1, game.getbankGeneral(), player.getMycount());
      eventDoGraphics(action, touche, game.getArrayGeneral().Pion(game.getArrayGeneral().getDistance() + 1, pion),
          context, player, game, pion);
    }
    if ((action == Action.KEY_RELEASED) && KeyboardKey.valueOf(touche.name()) == KeyboardKey.B) {
      if (Game.verificationIfPatchIsPresent(game.getArrayPatch())) {
        choiceGraphicPhase2(action, touche, context, game, player, pion);
        return;
      }
    }
  }

  /**
   * Displays the winner graphically and waits for something to be pressed to
   * continue (exit the game)
   * 
   * @param player1      : Variable who representing a Player during a graphical
   *                     game.
   * @param player2      : Variable who representing a Player during a graphical
   *                     game.
   * @param context      : variable of ApplicationContext context.
   * @param patchSpecial : int who representing the special patch 7*7
   */
  static void winnergraphics(Player player1, Player player2, ApplicationContext context, int patchSpecial) {
    Objects.requireNonNull(player1, "Player1 is null");
    Objects.requireNonNull(player2, "Player2 is null");
    Objects.requireNonNull(context, "context is null");
    Frame.reset(context);
    Frame.printWinner(player1, player2, context, patchSpecial);
    Event event = context.pollOrWaitEvent(500);
    event = null;
    while (event == null) {
      event = context.pollOrWaitEvent(500);
    }
  }

  /**
   * Function that initializes a graphical part when it is launched.
   * 
   * @param game    : Representing all element during a game, variable of Game
   *                type.
   * @param player1 : Variable who representing a Player during a graphical game.
   * @param player2 : Variable who representing a Player during a graphical game.
   */
  public static void runGraphic(Game game, Player player1, Player player2) {
    Objects.requireNonNull(game, "game is null");
    Objects.requireNonNull(player1, "player1 is null");
    Objects.requireNonNull(player2, "player2 is null");
    Application.run(Color.black, context -> {
      int patchSpecial = 0;
      while (game.getArrayGeneral().getPlayer1() != 1 && game.getArrayGeneral().getPlayer2() != 1) {
        Frame.reset(context);
        if (game.getArrayGeneral().isReverse() == false) {
          Frame.tablePrint(game, context, player1, new int[9][9], false, false, 1);
          choiceGraphicPhase1(context, game, player1, 1);
        }
        if (player1.patchSpecial() && patchSpecial == 0) {
          patchSpecial = 1;
        }
        Frame.reset(context);
        if (game.getArrayGeneral().isReverse() == true) {
          Frame.tablePrint(game, context, player2, new int[9][9], false, false, 2);
          choiceGraphicPhase1(context, game, player2, 2);
        }
        if (player2.patchSpecial() && patchSpecial == 0) {
          patchSpecial = 2;
        }
      }
      winnergraphics(player1, player2, context, patchSpecial);
      context.exit(0);
    });
  }
}
