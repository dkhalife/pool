package com.dkhalife.projects;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public abstract class State implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
	private Input input = new Input();

	public Input getInput() {
		return input;
	}

	public abstract void enter(Canvas c);

	public abstract void leave(Canvas c);

	public abstract void draw(Graphics2D g);

	public abstract void update(Input i, float deltaT);

	/**
	 * Keyboard
	 */

	@Override
	public void keyPressed(KeyEvent evt) {
		// Press the button
		input.setPressed(evt.getKeyCode(), evt.getKeyChar());

		// Set modifiers
		input.setAlt(evt.isAltDown());
		input.setCtrl(evt.isControlDown());
		input.setShift(evt.isShiftDown());
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		// Double release using both methods to be sure
		input.setReleased(evt.getKeyCode());
		input.setReleased(evt.getKeyChar());

		// Set modifiers
		input.setAlt(evt.isAltDown());
		input.setCtrl(evt.isControlDown());
		input.setShift(evt.isShiftDown());
	}

	@Override
	public void keyTyped(KeyEvent evt) {
	}

	/**
	 * Mouse
	 */

	@Override
	public void mouseDragged(MouseEvent evt) {
		input.setDragging(true);
		input.moveMouse(evt.getX(), evt.getY());
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		input.moveMouse(evt.getX(), evt.getY());
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		input.setClick(evt);
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
	}

	@Override
	public void mouseExited(MouseEvent evt) {
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		input.setMouse(true);
		input.setClick(evt);
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		input.setClick(new MouseEvent((Component) evt.getSource(), MouseEvent.MOUSE_MOVED, 0, MouseEvent.NOBUTTON, 0, 0, 0, false));
		input.setDragging(false);
		input.setMouse(false);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) {
	}
}