package fr.uge.patchwork;

import java.util.Objects;

/**
 * Record representing the object Patch. Lets you representer an object patch
 * with its parameters such as its price, the value that makes you progress and
 * the buttons that it makes you win.
 * 
 * @param arrayPatch  : (int) integer (2D) array who represent a patch.
 * @param height      : (int) integer representing the height of the patch.
 * @param lenghts     : (int) integer representing the length of the patch.
 * @param numberCase  : (int) integer who represent the number of steps for a
 *                    player.
 * @param numberPiece : (int) integer who represent the price of patch.
 * @param bouton      : (int) integer who represent the number button that a
 *                    player wins.
 */
public record Patch(int[][] arrayPatch, int height, int lenghts, int numberCase, int numberPiece, int bouton) {

  /**
   * Compact constructor for a Patch
   **/
  public Patch {
    Objects.requireNonNull(arrayPatch, "arraypatch is null");
    if (lenghts < 0 || lenghts > 8) {
      throw new IllegalArgumentException("lenghts is not beetwen 0 and 8");
    }
    if (height < 0 || height > 8) {
      throw new IllegalArgumentException("heigh is not beetwen 0 and 8");
    }
    if (numberCase < 0) {
      throw new IllegalArgumentException("numberCase < 0");
    }
    if (numberPiece < 0) {
      throw new IllegalArgumentException("numberPiece < 0");
    }
    if (bouton < 0) {
      throw new IllegalArgumentException("bouton < 0");
    }
    if (verificationPatchArray() == false) {
      throw new IllegalArgumentException("array of your Patch have not only 1 and 0");
    }
  }

  /**
   * Function who checked a patch.
   * 
   * @return boolean: boolean value, false if patch is mistake or else true.
   */
  public boolean verificationPatchArray() {
    for (var i = 0; i < height; i++) {
      for (var j = 0; j < lenghts; j++) {
        if (arrayPatch[i][j] != 0 && arrayPatch[i][j] != 1) {
          return false;
        }
      }
    }
    return true;

  }

  /**
   * Method that performs the right rotation of a patch, and returns it.
   * 
   * @return Patch : Right rotation result.
   */
  public Patch rotateMatrixRight() {
    int[][] mat = new int[lenghts][height];
    for (var i = 0; i < lenghts; ++i) {
      for (var j = 0; j < height; ++j) {
        mat[i][j] = arrayPatch[height - j - 1][i];
      }
    }
    return new Patch(mat, lenghts, height, numberCase, numberPiece, bouton);
  }

  /**
   * Method that performs the left rotation of a patch, and returns it.
   * 
   * @return Patch : Left rotation result.
   */
  public Patch rotateMatrixLeft() {
    int[][] mat = new int[lenghts][height];
    for (var i = 0; i < lenghts; ++i) {
      for (var j = 0; j < height; ++j) {
        mat[i][j] = arrayPatch[j][lenghts - i - 1];
      }
    }
    return new Patch(mat, lenghts, height, numberCase, numberPiece, bouton);
  }

  /**
   * Auxiliary method that performs the reverse of a square matrix.
   * 
   * @param mat : (int[][]) 2D integer array simulating a matrix.
   */
  void reversePair(int[][] mat) {
    Objects.requireNonNull(mat, "mat is null");
    int hight = 0;
    int low = height - 1;
    for (var i = 0; i < height; i++) {
      hight += i;
      low -= i;
      if (hight == height / 2) {
        return;
      }
      for (var j = 0; j < lenghts; j++) {
        mat[hight][j] = arrayPatch[low][j];
        mat[low][j] = arrayPatch[hight][j];
      }
      hight = 0;
      low = height - 1;
    }
  }

  /**
   * Auxiliary method that performs the reverse of a square matrix.
   * 
   * @param mat : (int[][]) integer array that representing the matrix (patch).
   */
  void reverseOdd(int[][] mat) {
    Objects.requireNonNull(mat, "mat is null");
    int hight = 0;
    int low = height - 1;
    for (var i = 0; i < height; i++) {
      hight += i;
      low -= i;
      if (hight == low) {
        for (var t = 0; t < lenghts; t++) {
          mat[hight][t] = arrayPatch[hight][t];
        }
        return;
      }
      for (var j = 0; j < lenghts; j++) {
        mat[hight][j] = arrayPatch[low][j];
        mat[low][j] = arrayPatch[hight][j];
      }
      hight = 0;
      low = height - 1;
    }
  }

  /**
   * Method that performs the reverse rotation of a patch, and returns it.
   * 
   * @return Patch : Reverse rotation result.
   */
  public Patch Patchreverse() {
    int[][] mat = new int[height][lenghts];
    if (height % 2 == 0) {
      reversePair(mat);
    } else {
      reverseOdd(mat);
    }
    return new Patch(mat, height, lenghts, numberCase, numberPiece, bouton);
  }

  /**
   * Method that displays a single patch.
   */
  public void displayPatch() {
    for (var i = 0; i < height; i++) {
      for (var j = 0; j < lenghts; j++) {
        if (arrayPatch[i][j] == 1) {
          System.out.print(1);
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }

  /**
   * Redefining of toString method to fit the display of a patch.
   * 
   * @return String : Containing the patch information to display.
   **/
  @Override
  public String toString() {
    var builder = new StringBuilder();
    builder.append("The form of the patch : \n");
    for (var x = 0; x < height; x++) {
      for (var y = 0; y < lenghts; y++) {
        if (arrayPatch[x][y] == 1) {
          builder.append(arrayPatch[x][y]);
        }
        if (arrayPatch[x][y] == 0) {
          builder.append(" ");
        }
      }
      builder.append("\n");
    }
    builder.append("\n bouton : ").append(bouton).append(" cout : ").append(numberPiece).append(" case forward : ")
        .append(numberCase).append("\n");
    return builder.toString();
  }

}
