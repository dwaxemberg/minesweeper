package com.minesweeper.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.minesweeper.GUI.Cell.Type;

public class Board {
	private static final long serialVersionUID = -5642967254185215168L;
	private int size;
	private Cell[][] cells;
	private HashMap<Cell, Coord> cellCoords;
	private static Random rnd = new Random();
	private JFrame mainFrame;
	
	public Board() {
		this(10);
	}
	
	public Board(int size) {
		this.size = size;
		mainFrame = new JFrame("MineSweeper");
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void build() {
		Container panel = mainFrame.getContentPane();
		panel.setLayout(new GridLayout(size, size));
		
		cells = new Cell[size][size];
		cellCoords = new HashMap<Cell, Coord>(size * size);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = null;
				
				int num = rnd.nextInt(10);
				if (num == 3) {
					cell = new Cell(this, i, j, Type.BOMB);
					System.out.println("bomb " + i  + ", " + j);
				} else {
					cell = new Cell(this, i, j, Type.NORMAL);
				}
				
				cell.createListener();
				cells[i][j] = cell;
				cellCoords.put(cell, new Coord(i, j));
				panel.add(cell.getButton());
			}
		}
		
		mainFrame.setSize(800, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	// no error checking, don't give me an invalid cell
	public List<Cell> neighboringCells(Cell cell) {
		List<Cell> neighbors = new LinkedList<Cell>();
		Coord coord = cellCoords.get(cell);
		
		for (int x = Math.max(coord.x - 1, 0); x <= Math.min(coord.x + 1, size-1); x++) {
			for (int y = Math.max(coord.y - 1, 0); y <= Math.min(coord.y + 1, size-1); y++) {
				if (new Coord(x, y).equals(coord)) continue;
				neighbors.add(cells[x][y]);
			}
		}
		
		return neighbors;
	}
	
	public void cellClicked(Cell clickedCell) {
		List<Cell> neighbors = neighboringCells(clickedCell);
		System.out.println("This: X: " + clickedCell.getX() + " Y: " + clickedCell.getY() + " Type: " + clickedCell.getType());
		
		int bombCount = 0; 
		for (Cell c : neighbors) {
			if (Type.BOMB == c.getType()) {
				bombCount++;
			}
		}
		clickedCell.getButton().setText("" + bombCount);
		clickedCell.getButton().setEnabled(false);
		clickedCell.disable();
	}
	
	public static void main(String[] args) {
		Board b = new Board();
		b.build();
	}
	
	private class Coord {
		public int x;
		public int y;
		
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		//no error checking whatsoever, don't be stupid
		public boolean equals(Object o) {
			Coord c = (Coord)o;
			return c.x == this.x && c.y == this.y;
		}
	}
}
