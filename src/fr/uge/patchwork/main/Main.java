package fr.uge.patchwork.main;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

import fr.uge.patchwork.Game;
import fr.uge.patchwork.Player;
import fr.uge.patchwork.graphics.Managementgraphics;

/**
 * Main class that declares the objects needed to launch a patchwork game.
 */
public class Main {

  static String initNamePlayer(Scanner scanner, int nb) {
    Objects.requireNonNull(scanner, "scanner is null");
    System.out.println("Nom du joueur " + nb + " :");
    var nom = scanner.next();
    nb++;
    return nom;

  }

  static int initChoicGame(Scanner scanner) {
    Objects.requireNonNull(scanner, "scanner is null");
    var mode = scanner.next();
    while (mode.compareTo("g") != 0 && mode.compareTo("t") != 0) {
      System.out.println("Choix du mode : (g:graphic | t : terminal)");
      mode = scanner.next();
    }
    if (mode.compareTo("g") == 0) {
      return 0;
    } else {
      return 1;
    }
  }

  static int initPackPatch(Scanner scanner) {
    Objects.requireNonNull(scanner, "scanner is null");
    System.out.println("Choice your pack of patch  : 1 (basic patch 4*4  avaible) | 2 (many differents patch avaible)");
    var patch = scanner.next();
    while (patch.compareTo("1") != 0 && patch.compareTo("2") != 0) {
      System.out
          .println("Choice your pack of patch  : 1 (basic patch 4*4  avaible) | 2 (many differents patch avaible)");
      patch = scanner.next();
    }
    return Integer.parseInt(patch);

  }

  /**
   * Main method to launch the game.
   * 
   * @param args : (String[]) String array representing arguments if it exists.
   * @throws IOException : exception management on files.
   */
  public static void main(String[] args) throws IOException {
    var scanner = new Scanner(System.in);
    boolean terminal = false;
    Player player1 = new Player(initNamePlayer(scanner, 1)), player2 = new Player(initNamePlayer(scanner, 2));
    System.out.println("Choix du mode : (g:graphic | t : terminal)");
    Game game = null;
    int choice = initChoicGame(scanner);
    if (choice == 0) {
      game = new Game(2);
    }
    if (choice == 1) {
      game = new Game(initPackPatch(scanner));
      terminal = true;
    }
    if (game.getMode() == 2) {
      game.patchInitialisation2(Path.of("Patch.txt"));
    } else {
      game.patchInitialisation1();
    }
    game.initialisationPionNeutral();
    if (terminal) {
      Game.runTerminal(game, player1, player2, scanner);
      scanner.close();
      return;
    }
    Managementgraphics.runGraphic(game, player1, player2);
    scanner.close();
  }
}
