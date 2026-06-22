package net.piescode.PieEngine.InputSystem;

public class InputEvent {
	private String inputName = "";
	private int keyCode = 0;
	private int mouseCode = 0;
	private String keyChar = "";
	
	public InputEvent(String inputName, int keyCode, String keyChar, int mouseCode) {
		this.inputName = inputName;
		this.keyCode = keyCode;
		this.mouseCode = mouseCode;
		this.keyChar = keyChar;
	}
	
	public String getInputName() {
		return inputName;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public int getMouseCode() {
		return mouseCode;
	}
	
	public String getKeyChar() {
		return keyChar;
	}
}
