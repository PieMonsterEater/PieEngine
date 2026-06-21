package net.piescode.PieEngine.InputSystem;

public interface InputListener {
	default void onKeyPressed(InputEvent ie) {}
	default void onKeyReleased(InputEvent ie) {}
	default void onKeyTyped(InputEvent ie) {}
	default void onMouseDragged(int x, int y) {}
	default void onMouseMoved(int x, int y) {}
	default void onMouseClicked(InputEvent ie, int x, int y) {}
}