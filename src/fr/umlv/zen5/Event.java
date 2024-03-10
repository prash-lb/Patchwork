package fr.umlv.zen5;

import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * An event ; a pointer event or a keyboard event ; an action and its set of
 * modifiers.
 */
public final class Event {
  private final Action action;
  private final Object data;
  private final int modifiers;
  private /* lazy */ Set<Modifier> modifierSet;

  /**
   * Action of the event.
   */
  public enum Action {
    /** screen pointer is down */
    POINTER_DOWN,
    /** screen pointer is up */
    POINTER_UP,
    /** screen pointer is moving */
    POINTER_MOVE,
    /** key is pressed */
    KEY_PRESSED,
    /** key is released */
    KEY_RELEASED
  }

  /**
   * A event modifier.
   */
  public enum Modifier {
    META(InputEvent.META_DOWN_MASK), CTRL(InputEvent.CTRL_DOWN_MASK), ALT(InputEvent.ALT_DOWN_MASK),
    SHIFT(InputEvent.SHIFT_DOWN_MASK), ALT_GR(InputEvent.ALT_GRAPH_DOWN_MASK);

    final int modifier;

    private Modifier(int modifier) {
      this.modifier = modifier;
    }

    private static final Modifier[] MODIFIERS = values();

    static Set<Modifier> modifierSet(int intModifiers) {
      if (intModifiers == 0) {
        return Collections.emptySet();
      }
      EnumSet<Modifier> set = EnumSet.noneOf(Modifier.class);
      for (Modifier modifier : MODIFIERS) {
        if ((intModifiers & modifier.modifier) != 0) {
          set.add(modifier);
        }
      }
      return set;
    }
  }

  Event(Action action, int modifiers, Object data) {
    this.action = action;
    this.modifiers = modifiers;
    this.data = data;
  }

  /**
   * Returns the action of the current event.
   * 
   * @return the action of the current event.
   */
  public Action getAction() {
    return action;
  }

  /**
   * A set of modifier keys that can contain {@link Modifier#META},
   * {@link Modifier#CTRL}, {@link Modifier#ALT}, {@link Modifier#SHIFT} and/or
   * {@link Modifier#ALT_GR}.
   * 
   * @return a set of modifier keys or an empty set if no modifier keys are used.
   */
  public Set<Modifier> getModifiers() {
    if (modifierSet != null) {
      return modifierSet;
    }
    return modifierSet = Modifier.modifierSet(modifiers);
  }

  /**
   * Returns the location of the pointer on the screen.
   * 
   * @return the x, y coordinates of the pointer on the screen or null if the
   *         current event is not a pointer event.
   */
  public Point2D.Float getLocation() {
    if (!(data instanceof Point2D.Float)) {
      return null;
    }
    return (Point2D.Float) data;
  }

  /**
   * Returns the keyboard key.
   * 
   * @return the keyboard key, {@link KeyboardKey#UNDEFINED} if the key is not
   *         recognized or null if the current event is not a keyboard event.
   */
  public KeyboardKey getKey() {
    if (!(data instanceof KeyboardKey)) {
      return null;
    }
    return (KeyboardKey) data;
  }

  @Override
  public String toString() {
    return action + " " + getModifiers() + " (" + data + ')';
  }
}
