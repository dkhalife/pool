package com.dkhalife.projects.pool.tests;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.dkhalife.projects.Application;
import com.dkhalife.projects.Input;
import com.dkhalife.projects.State;

/**
 * This class launches the tests for the project
 * 
 * @author Dany
 * 
 */
public class Main {
	private static int MAIN_MENU;

	private static Rectangle R = new Rectangle();
	private static int x, y;
	private static int dim = 1;

	/**
	 * @param args Command line arguments
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedAudioFileException, IOException {
		Application app = new Application("Test", 800, 600, 60);

		MAIN_MENU = app.addState(new State() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) {
				dim -= evt.getWheelRotation();

				if (dim < 0)
					dim = 0;
			}

			@Override
			public void update(Input i, float deltaT) {
				if (i.isShiftDown())
					System.out.println("_S");
				if (i.isCtrlDown())
					System.out.println("_C");
				if (i.isAltDown())
					System.out.println("_A");
				if (i.isKeyDown('A'))
					System.out.println("A");

				if (i.isKeyCodeDown(KeyEvent.VK_M)) {
					System.out.println(i.getDeltaX() + "-" + i.getDeltaY());
				}

				if (i.isLeftMouseButton())
					System.out.println("_L");
				if (i.isMiddleMouseButton())
					System.out.println("_M");
				if (i.isRightMouseButton())
					System.out.println("_R");

				if (i.isLeftMouseButton()) {
					x = i.getMouseX();
					y = i.getMouseY();
					R.setSize(dim, dim);
					R.setLocation(x, y);
				}
			}

			@Override
			public void enter(Canvas c) {
				System.out.println("ENTER");
			}

			@Override
			public void leave(Canvas c) {
				System.out.println("LEAVE");
			}

			@Override
			public void draw(Graphics2D g) {
				g.setColor(Color.red);
				g.draw(R);
			}
		});

		app.changeState(MAIN_MENU);
	}
}
