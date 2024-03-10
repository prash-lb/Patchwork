package fr.umlv.zen5;

import java.awt.Color;
import java.util.function.Consumer;

/**
 * Main class of the Zen 4 framework. Starting a GUI application is as simple as
 * calling {@link #run(Color, Consumer)}.
 */
public final class Application {
  private Application() {
    throw null; // no instance
  }

  /**
   * Starts a GUI frame with a drawing area then runs the application code.
   * 
   * @param backgroundColor background color of the drawing area.
   * @param applicationCode code of the application.
   */
  public static void run(Color backgroundColor, Consumer<ApplicationContext> applicationCode) {
    // ...
  }
}
