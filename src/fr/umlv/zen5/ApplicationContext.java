package fr.umlv.zen5;

import java.awt.Graphics2D;
import java.util.function.Consumer;

/**
 * Provided by the application framework, this object allows to
 * {@link #renderFrame(Consumer) render} a graphic code into the drawing area of
 * the application and to {@link #pollOrWaitEvent(long)
 * wait}/{@link #pollEvent() ask} for a pointer event.
 */
public interface ApplicationContext {
  /**
   * Returns screen information.
   * 
   * @return screen information.
   * @throws IllegalStateException if this method is not called by the application
   *                               thread.
   */
  public ScreenInfo getScreenInfo();

  /**
   * Exit the application.
   * 
   * @param exitStatus the exit status. By convention, a nonzero status code
   *                   indicates abnormal termination.
   * @throws IllegalStateException if this method is not called by the application
   *                               thread.
   */
  public void exit(int exitStatus);

  /**
   * Returns the first event since either the application was started or
   * {@link #pollEvent()}/{@link #pollOrWaitEvent(long)} was called. This method
   * returns immediately.
   * 
   * @return an event or null if no event happens.
   * @throws IllegalStateException if this method is not called by the application
   *                               thread.
   */
  public Event pollEvent();

  /**
   * Returns the first event since either the application was started or
   * {@link #pollEvent()}/{@link #pollOrWaitEvent(long)} was called. This method
   * waits until a event occurs.
   * 
   * @param timeout maximum time to wait (in milliseconds).
   * @return an event or null if the timeout is elapsed.
   * @throws IllegalStateException if this method is not called by the application
   *                               thread.
   */
  public Event pollOrWaitEvent(long timeout);

  /**
   * Render a whole frame of the game in one call.
   * 
   * Because this method tries to use the hardware acceleration, the content of
   * the screen is stored in the graphic card VRAM and may disappear at any
   * moment. If the content of the screen is lost the parameter
   * {@code contentLost} of the consumer taken as argument will be true.
   * 
   * @param consumer a consumer that will be called {@code perhaps several times}
   *                 to render the current frame.
   * @throws IllegalStateException if this method is not called by the application
   *                               thread.
   */
  public void renderFrame(Consumer<Graphics2D> consumer);
}