package com.dkhalife.projects;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JFrame;

public class Application {
	protected State currentState;
	private Timer timer = new Timer();
	private TimerTask task;
	private long delay;
	private boolean pause = false;
	private JFrame window;
	private Canvas canvas = new Canvas();
	private boolean enableRepaint = true;
	private Vector<State> states = new Vector<>();

	public Application(String title, int width, int height, long fps) {
		delay = 1000 / fps;

		// Create a new window
		window = new JFrame(title);
		window.setSize(width, height);

		// Add a canvas to it
		canvas.setBackground(Color.BLACK);
		window.add(canvas, BorderLayout.CENTER);

		// Open the window
		window.setVisible(true);

		// Disable the close operation
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Custom close operation
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				changeState(-1);
				System.exit(0);
			}
		});

		/**
		 * Dispatchers
		 */
		canvas.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (currentState == null)
					return;

				currentState.keyTyped(evt);
			}

			@Override
			public void keyReleased(KeyEvent evt) {
				if (currentState == null)
					return;

				currentState.keyReleased(evt);
			}

			@Override
			public void keyPressed(KeyEvent evt) {
				if (currentState == null)
					return;

				currentState.keyPressed(evt);
			}
		});
		canvas.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent evt) {
				if (currentState == null)
					return;

				currentState.mouseReleased(evt);
			}

			@Override
			public void mousePressed(MouseEvent evt) {
				if (currentState == null)
					return;

				currentState.mousePressed(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				if (currentState == null)
					return;

				currentState.mouseExited(evt);
			}

			@Override
			public void mouseEntered(MouseEvent evt) {
				if (currentState == null)
					return;

				currentState.mouseEntered(evt);
			}

			@Override
			public void mouseClicked(MouseEvent evt) {
				if (currentState == null)
					return;

				currentState.mouseClicked(evt);
			}
		});
		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent evt) {
				if (currentState == null)
					return;

				currentState.mouseMoved(evt);
			}
			
			@Override
			public void mouseDragged(MouseEvent evt) {
				if (currentState == null)
					return;

				currentState.mouseDragged(evt);
			}
		});
		
		canvas.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) {
				if (currentState == null)
					return;

				currentState.mouseWheelMoved(evt);
			}
		});

		// Start the main loop
		play();
	}

	protected void pause() {
		task.cancel();
		timer.purge();

		pause = true;
	}

	public void setRepaint(boolean repaint) {
		enableRepaint = repaint;
	}

	public JFrame getWindow() {
		return window;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	protected void play() {
		task = new TimerTask() {
			@Override
			public void run() {
				if (currentState == null)
					return;

				if (enableRepaint){
					window.repaint();
				}
				
				currentState.draw((Graphics2D) canvas.getGraphics());
				currentState.update(currentState != null ? currentState.getInput() : null, delay);
			}
		};
		
		timer.scheduleAtFixedRate(task, 0, delay);
		
		pause = false;
	}

	protected boolean isPaused() {
		return pause;
	}

	public int addState(State s) {
		states.add(s);
		return states.size() - 1;
	}

	public void changeState(int stateId) {
		// Prevent invalid changes
		if (stateId < -1 || stateId >= states.size())
			return;

		State newState = null;
		if (stateId > -1)
			newState = states.get(stateId);

		if (currentState != null) {
			currentState.leave(canvas);
		}

		currentState = newState;

		if (newState != null) {
			newState.enter(canvas);
		}
	}
}
