package com.dkhalife.projects;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;

public class Input {
	/**
	 * Keyboard
	 */
	// Keys down
	private HashMap<Integer, Character> keysDown = new HashMap<>();
	// Ctrl button
	private boolean ctrlDown = false;
	// Shift button
	private boolean shiftDown = false;
	// Alt button
	private boolean altDown = false;

	/**
	 * Mouse
	 */
	// No button
	public static int NONE = 0;
	// Left mouse button
	public static int LEFT = 1;
	// Right mouse button
	public static int RIGHT = 2;
	// Middle mouse button
	public static int MIDDLE = 3;
	
	// Mouse state
	private boolean mouseDown = false;

	// Previous X and Y
	private int prevX, prevY;
	// Delta X and Y
	private int deltaX, deltaY;

	// Which mouse button is down
	private int click = 0;
	// Is mouse dragging
	private boolean dragging = false;

	protected void moveMouse(int x, int y) {
		deltaX = x - prevX;
		deltaY = y - prevY;

		prevX = x;
		prevY = y;
	}

	public int getMouseX() {
		return prevX;
	}
	
	public int getMouseY(){
		return prevY;
	}
	
	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	protected void setMouse(boolean mouseDown) {
		this.mouseDown = mouseDown;
	}

	public boolean isCtrlDown() {
		return ctrlDown;
	}

	protected void setCtrl(boolean ctrlDown) {
		this.ctrlDown = ctrlDown;
	}

	public boolean isAltDown() {
		return altDown;
	}

	protected void setAlt(boolean altDown) {
		this.altDown = altDown;
	}

	public boolean isShiftDown() {
		return shiftDown;
	}

	protected void setShift(boolean shiftDown) {
		this.shiftDown = shiftDown;
	}

	public boolean isDragging() {
		return dragging;
	}

	protected void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	protected void setClick(MouseEvent evt) {
		if (SwingUtilities.isLeftMouseButton(evt))
			click = LEFT;
		else if (SwingUtilities.isRightMouseButton(evt))
			click = RIGHT;
		else if (SwingUtilities.isMiddleMouseButton(evt))
			click = MIDDLE;
		else
			click = NONE;
	}

	public boolean isLeftMouseButton() {
		if(!mouseDown) return false;
		
		return click == LEFT;
	}

	public boolean isRightMouseButton() {
		if(!mouseDown) return false;
		
		return click == RIGHT;
	}

	public boolean isMiddleMouseButton() {
		if(!mouseDown) return false;
		
		return click == MIDDLE;
	}

	public boolean isKeyDown(char key) {
		return keysDown.containsValue(key);
	}

	public boolean isKeyCodeDown(int code) {
		return keysDown.containsKey(code);
	}

	protected void setPressed(int code, char key) {
		// Add only once
		if (keysDown.containsKey(code))
			return;

		keysDown.put(code, key);
	}

	protected void setReleased(int code) {
		keysDown.remove(code);
	}

	protected void setReleased(char key) {
		// Only release when it's there
		if (!isKeyDown(key))
			return;

		Iterator<Entry<Integer, Character>> it = keysDown.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Character> pairs = it.next();
			// avoids a ConcurrentModificationException (SO #1066589)
			it.remove();

			if (pairs.getValue() == key) {
				setReleased(pairs.getKey());
				return;
			}
		}
	}
}
