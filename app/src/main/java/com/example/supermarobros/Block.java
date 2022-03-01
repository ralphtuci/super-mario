package com.example.supermarobros;

import java.util.Random;
public class Block {
  private int type;
  private int x;
  private int y;
 // public int index; // holds index of the enemy
  // -1 cloud
  // 0 is no block  | 1 is ground block | 2 is gift block
  // 3 is coin      | 4 is star         | 5 is mushroom
  // 6 is pipe      | 7 is piranha plant| 8 is goomba
  // 9 is mario     | 10 is supermario  | 11 is naruto (invincible)
  // 12 is ending flag

  private String bonus;

  public Block (int newType) {
    initBlock(newType);
  }
  public Block (int newType, int x, int y/*, int index*/){
    initBlock(newType);
    this.x=x;
    this.y=y;
 //   this.index=index;
  }
  private void initBlock(int newType) {
   // Random r = new Random ();
    type = newType;
    newBonus (newType);
  }
  public void setType (int newType){
    type = newType;
  }
  public void setBonus (String newBonus){
    bonus = newBonus;
  }
  public int getType () {
    return type;
  }
  public String getBonus () {
    return bonus;
  }
  private void newBonus (int type){
    Random r = new Random ();
    int randomGift = Math.abs(r.nextInt() % 1000);
    if (type == 2){
      if (randomGift <= 50)
        bonus = "STAR";
      else if (randomGift <= 150)
        bonus = "SHROOM";
      else 
        bonus = "COIN";
    }
    else
      bonus = "NONE";
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
/*
  public int getIndex() {
    return index;
  }*/
}
