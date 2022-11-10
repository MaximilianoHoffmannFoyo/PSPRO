package org.ccbsoft;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {

  private Image image;
  private double positionX;
  private double positionY;
  private double velocityX;
  private double velocityY;
  private double width;
  private double height;

  public void setPositionX(double positionX) {
    this.positionX = positionX;
  }

  public void setPositionY(double positionY) {
    this.positionY = positionY;
  }

  public double getPositionX() {
    return positionX;
  }

  public double getPositionY() {
    return positionY;
  }
  
  public double getVelocityX() {
    return velocityX;
  }

  public double getVelocityY() {
    return velocityY;
  }

  public Sprite() {
    positionX = 0;
    positionY = 0;
  }

  public void setImage(Image i) {
    image = i;
    width = i.getWidth();
    height = i.getHeight();
  }

  public void setImage(String filename) {
    Image i = new Image(filename);
    setImage(i);
  }

  public void setPosition(double x, double y) {
    setPositionX(x);
    setPositionY(y);
  }
  
    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void updatePosition(double time)
    {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

  public void render(GraphicsContext gc) {
    gc.drawImage(image, positionX-image.getWidth()/2, positionY-image.getHeight()/2);
  }

  public Rectangle2D getBoundary() {
    return new Rectangle2D(positionX-image.getWidth()/2, positionY-image.getHeight()/2, width, height);
  }

  public boolean intersects(Sprite s) {
    return s.getBoundary().intersects(this.getBoundary());
  }

  public String toString() {
    return " Posición: [" + positionX + "," + positionY + "]";
  }
}
