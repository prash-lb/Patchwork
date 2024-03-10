package fr.uge.patchwork;

import java.util.List;
import java.util.Objects;

/**
 * Class representing the Money object. Represents the own buttons by the
 * players and by the bank during a game game.
 **/
public class Money {
  private int bank;

  /**
   * Constructor to initialize a bank of Money object.
   **/
  public Money() {
    this.bank = 152;

  }

  /**
   * Method to initialize buttons of a player.
   **/
  public void initialiseBankPlayer() {
    bank = 5;
  }

  /**
   * Static method that retributes the win to a player after buying a patch.
   * 
   * @param bank      : (Money) Object represents the player’s button wallet.
   * @param patchList : (List) player patch list.
   * @param Player    : (Money) Object represents of the bank button.
   **/
  public static void piecePatch(List<Patch> patchList, Money bank, Money Player) {
    Objects.requireNonNull(bank, "bank is null");
    Objects.requireNonNull(patchList, "patchList (piecePatch) is null");
    Objects.requireNonNull(Player, "Player is null");
    var compteur = 0;
    for (var i = 0; i < patchList.size(); i++) {
      compteur += patchList.get(i).bouton();
    }
    if (bank.bank < compteur) {
      System.out.println("The money in the bank is not enought for the amount of " + compteur);
      return;
    }
    bank.bank -= compteur;
    Player.bank += compteur;
  }

  /**
   * Static method which add to the player the number of buttons corresponding to
   * the number of boxes he has more than the other player.
   * 
   * @param distance : (int) integer who represents the number of boxes a player
   *                 has browsed.
   * @param bankPlay : (Money) Object represents the player’s button wallet.
   * @param player   : (Money) Object represents of the bank button.
   */
  public static void pieceCase(int distance, Money bankPlay, Money player) {
    Objects.requireNonNull(bankPlay, "bankplay is null");
    Objects.requireNonNull(player, "bankplay is null");
    if (distance < 0) {
      throw new IllegalArgumentException("distance < 0");
    }
    if (bankPlay.bank < distance) {
      System.out.println("The money in the bank is not enought for the distance ");
      return;
    }
    bankPlay.bank -= distance;
    player.bank += distance;
  }

  /**
   * Accessor method who return the amount in the bank.
   * 
   * @return int : integer representing the amount in the bank.
   */
  public int getBank() {
    return bank;
  }

  /**
   * Static method performs the necessary transactions if the player has enough
   * buttons.
   * 
   * @param patch    : (Patch) Object represents the patch a player wants to buy.
   * @param player   : (Money) Object represents of the bank button.
   * @param bankPlay : (Money) Object represents the player’s button wallet.
   **/
  public static void achatPiece(Patch patch, Money player, Money bankPlay) {
    Objects.requireNonNull(patch, "Patch is null");
    Objects.requireNonNull(player, "Player is null");
    Objects.requireNonNull(bankPlay, "bankplay is null");
    if (player.bank < patch.numberPiece()) {
      System.out.println("You can't  buy this Patch because you don't have enought of money");
      return;
    }
    player.bank -= patch.numberPiece();
    bankPlay.bank += patch.numberPiece();
  }

  /**
   * Static method that checks and return true if a transfert money is possible,
   * otherwise false.
   * 
   * @param moneyPossible : (int) value who represents the value of the
   *                      transaction.
   * @param source        : (Money) Object that representing a money object
   * @return boolean :
   */
  public static boolean verificationMoneyTransfert(int moneyPossible, Money source) {
    if (source.bank < moneyPossible) {
      return false;
    }
    return true;
  }

}