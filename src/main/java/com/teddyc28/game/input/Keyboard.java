package com.teddyc28.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean keys[] = new boolean[120];
	public boolean up, down, left, right, su, sd, sl, sr;
	
	public void update() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];

		su = keys[KeyEvent.VK_UP];
		sd = keys[KeyEvent.VK_DOWN];
		sl = keys[KeyEvent.VK_LEFT];
		sr = keys[KeyEvent.VK_RIGHT];
		
		for (int i = 0; i < keys.length; i++) {
			//if (keys[i]) System.out.println("Key: " + i);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent e) {
	}

}
