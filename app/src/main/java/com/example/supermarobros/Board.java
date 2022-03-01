package com.example.supermarobros;


import java.util.ArrayList;

public class Board {
  public static final int LEVEL_WIDTH = 40;
  public static final int LEVEL_HEIGHT = 10;
  public static final int DISPLAY_WIDTH = 20;
  public static final int DISPLAY_HEIGHT = 10;
  private int[][][] levelStructure;
  public static int currentLevel = 0;
 // private int enemyIndex;
  //private int leftBound;
 // ArrayList<Block> enemies = new ArrayList<Block>();
  private Block[][] level;
  private Maro player;
  public static boolean END = false;
  private String direction = "LEFT";

  public Board() {
    initBoard();
  }

  private void initBoard() {
  // enemyIndex = 0;
    //leftBound = 0;
    level = new Block[LEVEL_HEIGHT][LEVEL_WIDTH];
    levelStructure = new int[][][]{

            // -1 cloud
            // 0 is no block  | 1 is ground block | 2 is gift block
            // 3 is coin      | 4 is star         | 5 is mushroom
            // 6 is pipe      | 7 is piranha plant| 8 is goomba
            // 9 is mario     | 10 is supermario  | 11 is naruto (invincible)
            // 12 is ending flag

            {//level 1
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
                    {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 9, 4, 5, 0, 8, 1, 0, 1, 1, 0, 8, 0, 6, 3, 8, 0, 3, 0, 0, 0, 0, 8, 0, 0, 0, 1, 1, 1, 1, 0, 8, 0, 8, 0, 1, 1, 1, 0, 12},
                    {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12},
                    {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            },
            {//level 2
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13},
                    {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 9, 0, 0, 8, 8, 1, 0, 0, 1, 1, 1, 1, 7, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 6, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            },                                             //^  next 20
            {//level 3
                    {0, 0, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 9, 0, 0, 8, 8, 8, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {1, 1, 1, 1, 1, 1, 1, 7, 7, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12},
                    {1, 1, 1, 1, 1, 1, 1, 6, 6, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            },

    };
    updateLevel();
  }

  public void setCurrentLevel(int newCurrentLevel) {
    currentLevel = newCurrentLevel;
  }

  public void updateLevel() {
    END = false;
   // enemyIndex = 0;
    for (int y = 0; y < LEVEL_HEIGHT; y++) {
      for (int x = 0; x < LEVEL_WIDTH; x++) {
        if (levelStructure[currentLevel][y][x] == 9) {
          player = new Maro(x, y);
          level[y][x] = new Block(0);
        }
        if (levelStructure[currentLevel][y][x] != 8 && levelStructure[currentLevel][y][x] != 7)
          level[y][x] = new Block(levelStructure[currentLevel][y][x]);
        else {
          level[y][x] = new Block(levelStructure[currentLevel][y][x], x, y);
         // enemies.add(level[y][x]);
         // enemyIndex++;
        }
      }
    }
  }

  public void moveEnemy() {
    if (direction.equals("RIGHT"))
      direction = "LEFT";
    else
      direction = "RIGHT";
    for (int i = 0; i < LEVEL_HEIGHT; i++) {
      for (int j = 0; j < LEVEL_WIDTH; j++) {
        if (level[i][j].getType() == 8) {
          if (direction.equals("LEFT")) {
            if (checkEnemyCollision(j - 1, i,j, i)) {
              level[i][j] = new Block(0);
              level[i][j - 1] = new Block(8, j - 1, i);
              if (level[i + 1][j - 1].getType() == 0) {
                level[i + 1][j - 1] = new Block(8, j - 1, i + 1);
                level[i][j - 1] = new Block(0);
              }
            }
          } else {
            if (checkEnemyCollision(j + 1, i ,j,i)) {
              level[i][j] = new Block(0);
              level[i][j + 1] = new Block(8, j + 1, i);
              if (level[i + 1][j + 1].getType() == 0) {
                level[i + 1][j + 1] = new Block(8, j + 1, i + 1);
                level[i][j + 1] = new Block(0);
              }
            }
          }
        }
      }
    }
  }

  // MARIO MOVES //
  public void moveLeft() {
    int newX = player.getX() - 1;
    int newY = player.getY();
    boolean canMove = false;
    if (checkCollision(newX, newY)) {
      canMove = true;
    }
    //need to check collisions before moving
    if (canMove) {
      if (isEnemy(newX, newY)) {
        if (!checkEnemy("LEFT")) {
          if (player.getStatus().equals("NORMAL")) {
            player.setLives(player.getLives() - 1);
            //if lives = 0 then gameover
          } else if (player.getStatus().equals("SUPER")) {
            player.setStatus("NORMAL");
            player.setScore(getPlayerScore()+1);
          } else {
            player.setScore(getPlayerScore()+1);
          }

          //donothing
          //continue;
          removeEnemy(newX, newY);
          
         // level[newY][newX] = new Block(0);
        }
      }

      boolean end = false;
      if (checkCoin(newX, newY))
        player.setScore(player.getScore() + 200);
      if (checkShroom(newX, newY)) {
        player.setStatus("SUPER");
        player.setScore(player.getScore() + 1000);
      }
      checkStar(newX, newY);
      if (checkEnd(newX, newY)) {
        end = true;
      }
      level[player.getY()][player.getX()].setType(0);
      level[newY][newX].setType(9);
      player.setX(newX);
      if (end) {
        if (currentLevel < 2) {
          setCurrentLevel(currentLevel + 1);
          updateLevel();
        } else {
          restartGame();
          END = true;
        }
      }
    }
  }

  public void moveRight() {
    System.out.println(player.getStatus());
    int newX = player.getX() + 1;
    int newY = player.getY();
    boolean canMove = false;
    if (checkCollision(newX, newY)) {
      canMove = true;
    }
    if (canMove) {
      if (isEnemy(newX, newY)) {
        System.out.println("enemy is here");
        if (!checkEnemy("RIGHT")) {
          if (player.getStatus().equals("NORMAL")) {
            player.setLives(player.getLives() - 1);
          }
          //if livs = 0 then gameover
          else if (player.getStatus().equals("SUPER")) {
            player.setStatus("NORMAL");
            player.setScore(getPlayerScore()+1);
          } else {
            player.setScore(getPlayerScore()+1);
          }
          //donothing, continue;
          removeEnemy(newX, newY);
          
         // level[newY][newX] = new Block(0);
        }
      }
      if (checkCoin(newX, newY))
        player.setScore(player.getScore() + 200);
      if (checkShroom(newX, newY)) {
        player.setStatus("SUPER");
        player.setScore(player.getScore() + 1000);
      }
      checkStar(newX, newY);
      boolean end = false;
      if (checkEnd(newX, newY)) {
        end = true;
        System.out.println("this is the end");
      }
      level[player.getY()][player.getX()].setType(0);
      level[newY][newX].setType(9);
      player.setX(newX);
        if (end) {
          if (currentLevel <2) {
            setCurrentLevel(currentLevel + 1);
            updateLevel();
          }
          else {
            restartGame();
            END = true;
          }
        }
    }
  }

  public void moveUp() {
    int newX = player.getX();
    int newY = player.getY() - 1;
    boolean canMove = false;
    if (checkCollision(newX, newY)) {
      canMove = true;
    }
    if (canMove) {
      if (isEnemy(newX, newY)) {
        if (!checkEnemy("UP")) {
          if (player.getStatus().equals("NORMAL")) {
            player.setLives(player.getLives() - 1);
            //if livs = 0 then gameover
          } else if (player.getStatus().equals("SUPER")) {
            player.setStatus("NORMAL");
            player.setScore(getPlayerScore()+1);
          } else {
            player.setScore(getPlayerScore()+1);
          }
          //donothing, continue;
          removeEnemy(newX, newY);
          
          //level[newY][newX] = new Block(0);
        }
      }
      if (checkCoin(newX, newY))
        player.setScore(player.getScore() + 200);
      if (checkShroom(newX, newY)) {
        player.setStatus("SUPER");
        player.setScore(player.getScore() + 1000);
      }
      checkStar(newX, newY);
      boolean end = false;
      if (checkEnd(newX, newY)) {
        end = true;
      }
      level[player.getY()][player.getX()].setType(0);
      level[newY][newX].setType(9);
      player.setY(newY);
      checkGift(newX, newY - 1);
      if (player.getStatus().equals("SUPER")) {
        if (level[newY - 1][newX].getType() == 1) {
          level[newY - 1][newX] = new Block(0);
          player.setScore(getPlayerScore()+10);
        }
      }
      if (end) {
        if (currentLevel <2) {
          setCurrentLevel(currentLevel + 1);
          updateLevel();
        }
        else {
          restartGame();
          END = true;
        }
      }
    } else {
      checkGift(newX, newY);
      if (player.getStatus().equals("SUPER")) {
        if (level[newY][newX].getType() == 1) {
          level[newY][newX] = new Block(0);
          player.setScore(player.getScore() + 10);
        }
      }
    }
  }

  private void returntostart() {
    int x = 0; int y = 0;
    for (int i = 0 ; i < LEVEL_HEIGHT ; i++){
      for (int j=0 ; j < LEVEL_WIDTH ; j++ ){
        if (levelStructure[currentLevel][i][j] == 9){
          x=j;
          y=i;
        }
      }
    }
    level[y][x] = new Block (9);
    level[player.getY()][player.getX()] = new Block (0);
    player.setX(x);
    player.setY(y);
  }

  public void moveDown() {
    if (player.getY() == 9){
      player.decrementLives();
      returntostart();
      player.setStatus("NORMAL");
      return;
    }
    int newX = player.getX();
    int newY = player.getY() + 1;
    boolean canMove = false;
    if (checkCollision(newX, newY)) {
      canMove = true;
    }
    if (canMove) {
      if (isEnemy(newX, newY)) {
        System.out.println("There's an enemy");
        if (checkEnemy("DOWN")) {
          player.setScore(player.getScore() + 1);
          removeEnemy(newX, newY);
          
          //level[newY][newX] = new Block(0);
          //int index = (Block)newX, newY;
        }
      }
      if (checkCoin(newX, newY))
        player.setScore(player.getScore() + 200);
      if (checkShroom(newX, newY)) {
        player.setStatus("SUPER");
        player.setScore(player.getScore() + 1000);
      }
      checkStar(newX, newY);
      boolean end = false;
      if (checkEnd(newX, newY)) {
        end = true;
      }
      level[player.getY()][player.getX()].setType(0);
      level[newY][newX].setType(9);
      player.setY(newY);
      if (end) {
        if (currentLevel <2) {
          setCurrentLevel(currentLevel + 1);
          updateLevel();
        }
        else {
          restartGame();
          END = true;
        }
      }
    }
  }
  private void removeEnemy (int x, int y){
    level[y][x] = null;
    level[y][x]=new Block (0);
  }
  // COLLISION DETECTION //
  private boolean checkCollision(int checkX, int checkY) {
    if (checkX > 39 || checkY > 9 || checkY < 0 || checkX < 0)
      return false;
    int type = level[checkY][checkX].getType();
    // can move
    return type == 0 || type == 7 || type == -1 || type == 3 || type == 4 || type == 5 || type == 8 || type == 12 || type == 13;

  }
  private boolean checkEnemyCollision(int checkX, int checkY, int curX, int curY) {
    if (checkY > 9 )
      removeEnemy(curX,curY);
    if (checkX > 39 || checkY < 0 || checkX < 0)
      return false;
    int type = level[checkY][checkX].getType();
    if (type == 9) {
      removeEnemy(curX, curY);
      if (player.getStatus().equals("NORMAL"))
        player.setLives(player.getLives()-1);
      else if (player.getStatus().equals("SUPER"))
        player.setStatus("NORMAL");
      else
        player.setScore(player.getScore()+1);
    }
    // can move
    return type == 0 || type == -1;

  }

  // ENEMY DETECTION //
  private boolean isEnemy(int checkX, int checkY) {
    int type = level[checkY][checkX].getType();
    return type == 7 || type == 8;
  }

  private boolean checkEnemy(String direction) {
    return direction.equals("DOWN");
  }

  private boolean checkCoin(int checkX, int checkY) {
    return level[checkY][checkX].getType() == 3;
  }

  private boolean checkShroom(int checkX, int checkY) {
    return level[checkY][checkX].getType() == 5;
  }

  private void checkStar(int checkX, int checkY) {
    if (level[checkY][checkX].getType() == 4) {
      player.setStatus("INVINCIBLE");
      player.setScore(getPlayerScore() + 1000);
    }

  }

  private boolean checkEnd(int checkX, int checkY) {
    System.out.println(checkX);
    System.out.println(checkY);
    System.out.println(level[checkY][checkX].getType());
    return level[checkY][checkX].getType() == 12 || level[checkY][checkX].getType() == 13;
  }

  // POWER-UP DETECTION //
  private void checkGift(int checkX, int checkY) {
    if (checkX > 39 || checkX < 0 || checkY < 0 || checkY > 9)
      System.out.println("cant do that");
    else {
      int type = level[checkY][checkX].getType();
      if (level[checkY][checkX].getBonus().equals("NONE"))
        System.out.println("MEW MEW MEW");
        //donothing
      else {
        if (level[checkY][checkX].getBonus().equals("COIN")) {
          player.setScore(player.getScore() + 200);
          level[checkY][checkX] = new Block(-3);
        }
        if (level[checkY][checkX].getBonus().equals("STAR")) {
          player.setStatus("INVINCIBLE");
          player.setScore(player.getScore() + 1000);
          level[checkY][checkX] = new Block(-4);
        }
        if (level[checkY][checkX].getBonus().equals("SHROOM")) {
          player.setStatus("SUPER");
          player.setScore(player.getScore() + 1000);
          level[checkY][checkX] = new Block(-5);
        }
      }
    }
  }

  // return the board
  public int[][] getBoard(int leftBound) {
    //System.out.println("leftbound:" + leftBound);
    int[][] boardArray = new int[DISPLAY_HEIGHT][DISPLAY_WIDTH];
    for (int i = 0; i < DISPLAY_HEIGHT; i++) {
      for (int j = leftBound; j < leftBound + DISPLAY_WIDTH; j++) {
        int type = level[i][j].getType();
        boardArray[i][j - leftBound] = type; // Block.get(int)
        if (type == 9) {
          String bonus = player.getStatus();
          if (bonus.equals("SUPER")) {
            boardArray[i][j - leftBound] = 10;
          } else if (bonus.equals("INVINCIBLE")) {
            boardArray[i][j - leftBound] = 11;

          } else {
            boardArray[i][j - leftBound] = 9;
          }
        }
        //System.out.println("level: "+level[i][j].getType());
      }
    }
    return boardArray;
  }

  public int getPlayerScore() {
    return player.getScore();
  }

  public int getPlayerLives() {
    return player.getLives();
  }

  public int getPlayerX() {
    return player.getX();
  }
  public int getPlayerY() {
    return player.getY();
  }

  public boolean FindInvisible() {
    return player.getStatus().equals("INVINCIBLE");
  }
  public void revert(){
    player.setStatus("NORMAL");
  }
  public void restartGame(){
    setCurrentLevel(0);
    player.setScore(0);
    player.setLives(3);
    player.setStatus("NORMAL");
    updateLevel();
  }

}
