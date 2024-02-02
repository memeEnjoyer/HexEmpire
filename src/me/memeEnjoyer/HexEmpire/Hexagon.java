package me.memeEnjoyer.HexEmpire;

import java.awt.*;
import java.awt.geom.Path2D;

public class Hexagon {
  private Point[] vertices;
  private Path2D path;

  public Hexagon(int x, int z, int side) { // side == radius of circumscribed circle
    this.vertices = new Point[6];
    this.path = new Path2D.Double();

    for(int i = 0; i < 6; i++) {
      double angle = Math.toRadians(60*i);
      int xPoint = (int) (x + side * Math.sin(angle));
      int zPoint = (int) (z + side * Math.cos(angle));

      this.vertices[i] = new Point(xPoint, zPoint);
    }
  }

  public boolean isPointInside(Point point) {
    this.path.moveTo(this.vertices[0].getX(), this.vertices[0].getY());
    for(int i = 1; i < this.vertices.length; i++) {
      this.path.lineTo(this.vertices[i].getX(), this.vertices[i].getY());
    }
    this.path.closePath();

    return this.path.contains(point.getX(), point.getY());
  } 
}

