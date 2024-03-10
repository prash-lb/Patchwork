package fr.umlv.zen5;

/**
 * Information about the screen.
 */
public interface ScreenInfo {
  /**
   * returns width of the application screen.
   * 
   * @return width of the application screen.
   * @throws IllegalStateException if this method is not called by the application
   *                               thread.
   */
  public float getWidth();

  /**
   * returns height of the application screen.
   * 
   * @return height of the application screen.
   * @throws IllegalStateException if this method is not called by the application
   *                               thread.
   */
  public float getHeight();
}
