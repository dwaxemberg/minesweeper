package com.minesweeper.GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Cell {
	private int x;
	private int y;
	private Type type;
	private JButton cellButton;
	private Board parent;
	private boolean enabled = true;
	private boolean flag = false;

	public Cell(Board parent, int x, int y, Type type) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.type = type;
		cellButton = new JButton();
		cellButton.setFocusable(false);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Type getType() {
		return type;
	}

	public JButton getButton() {
		return cellButton;
	}

	public void disable() {
		this.enabled = false;
	}
	
	public void toggleFlag() {
		if (flag) {
			cellButton.setText("");
		} else {
			cellButton.setText("F");
		}
		flag = !flag;
	}
	
	public void createListener() {
		cellButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Cell.this.enabled) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (Cell.this.flag) {
							Cell.this.toggleFlag();
						} else {
							Cell.this.parent.cellClicked(Cell.this);
						}
					} else {
						Cell.this.toggleFlag();
					}
				}
				e.consume();
			}
		});
	}

	public enum Type { NORMAL, BOMB };
}
