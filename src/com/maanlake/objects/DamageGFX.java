package com.maanlake.objects;

import java.awt.Color;
import java.awt.Point;

public class DamageGFX {

	String text="";	
	Point position = new Point();
	Color color = new Color(0, 0, 0);
	Point moveVector = new Point();
	
	public Point getMoveVector() {
		return moveVector;
	}
	public void setMoveVector(Point moveVector) {
		Point newVector = new Point(moveVector.x,moveVector.y);
		this.moveVector = newVector;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		Point newPos = new Point(position.x,position.y);
		this.position = newPos;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
