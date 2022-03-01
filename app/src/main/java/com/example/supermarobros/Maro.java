package com.example.supermarobros;

public class Maro {
  private int locationX;
  private int locationY;
  public static int score = 0;
  public static int lives = 3;

  private String status;
  public Maro (int x, int y){
    initMaro(x, y);
  }
  private void initMaro (int x, int y) {
    locationX = x;
    locationY = y;
    status = "NORMAL";
  }
  public void setX (int newX) {
    locationX = newX;
  }
  public void setY (int newY) {
    locationY = newY;
  }
  public void setScore (int newScore) {
    score = newScore;
  }
  public void setLives (int newLives) {
    lives = newLives;
  }
  public void decrementLives () {
    lives = lives -1;
  }
  public void setStatus (String newStatus) {
    status = newStatus;
  }
  public int getX () {
    return locationX;
  }
  public int getY () {
    return locationY;
  }
  public int getScore () {
    return score;
  }
  public int getLives () {
    return lives;
  }
  public String getStatus () {
    return status;
  }

}
