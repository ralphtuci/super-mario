package com.example.supermarobros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceView;
import java.lang.Math;

public class CustomView extends SurfaceView {
    private static final int SQ_SZ = 100;
    private static final int B_WIDTH = 20;
    private static final int B_HEIGHT = 10;
    private int [][]currBoard;
    private int width;
    private int height;
    private boolean finish;
    private boolean start;
    private static int MarioX, MarioY;

    private int currentBlock;

    private android.graphics.Paint[][] Paint = new Paint[B_HEIGHT][B_WIDTH];
    private Rect[][] Boards = new Rect[B_HEIGHT][B_WIDTH];
    Bitmap  star,
            shroom,
            coin,
            sky,
            brick,
            mystery,
            pipe,
            plant,
            plantOpen,
            plantClose,
            goomba,
            mario,
            supermario,
            naruto,
            flag,
            pole,
            emptygift,
            coingift,
            shroomgift,
            stargift;
    private Rect rect;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        currBoard = new int[10][20];
        finish = false;
        start = true;

        // BitMap Declarations
        /*-5*/ shroomgift = BitmapFactory.decodeResource(getResources(), R.mipmap.shroomgift);
        /*-4*/ stargift = BitmapFactory.decodeResource(getResources(), R.mipmap.stargift);
        /*-3*/ coingift = BitmapFactory.decodeResource(getResources(), R.mipmap.coingift);
        /*-2*/ emptygift = BitmapFactory.decodeResource(getResources(), R.mipmap.empty);
        /*-1*/ sky = BitmapFactory.decodeResource(getResources(), R.mipmap.fluff);
        /*0*/ // no block present
        /*1*/ brick = BitmapFactory.decodeResource(getResources(), R.mipmap.brick);
        /*2*/ mystery = BitmapFactory.decodeResource(getResources(), R.mipmap.mystery);

        /*3*/ coin = BitmapFactory.decodeResource(getResources(), R.mipmap.coin);
        /*4*/ star = BitmapFactory.decodeResource(getResources(), R.mipmap.star);
        /*5*/ shroom = BitmapFactory.decodeResource(getResources(), R.mipmap.mushroom);

        /*6*/ pipe = BitmapFactory.decodeResource(getResources(), R.mipmap.pipe);
        /*7*/ plant = BitmapFactory.decodeResource(getResources(), R.mipmap.marioplant);
        /*8*/ goomba = BitmapFactory.decodeResource(getResources(), R.mipmap.goomba);

        /*9*/ mario = BitmapFactory.decodeResource(getResources(), R.mipmap.mario);
        /*10*/supermario = BitmapFactory.decodeResource(getResources(), R.mipmap.supermario);
        /*11*/naruto = BitmapFactory.decodeResource(getResources(), R.mipmap.naruto);

        /*12*/pole = BitmapFactory.decodeResource(getResources(), R.mipmap.pole);
        /*13*/flag = BitmapFactory.decodeResource(getResources(), R.mipmap.flag);
        // Scaled Bitmap (width, height)
        coingift = Bitmap.createScaledBitmap(coingift, SQ_SZ, SQ_SZ, true);
        shroomgift = Bitmap.createScaledBitmap(shroomgift, SQ_SZ, SQ_SZ, true);
        stargift = Bitmap.createScaledBitmap(stargift, SQ_SZ, SQ_SZ, true);
        emptygift = Bitmap.createScaledBitmap(emptygift, SQ_SZ, SQ_SZ, true);

        sky = Bitmap.createScaledBitmap(sky, SQ_SZ, SQ_SZ, true);
        brick = Bitmap.createScaledBitmap(brick, SQ_SZ, SQ_SZ, true);
        mystery = Bitmap.createScaledBitmap(mystery, SQ_SZ, SQ_SZ, true);

        coin = Bitmap.createScaledBitmap(coin, SQ_SZ, SQ_SZ, true);
        star = Bitmap.createScaledBitmap(star, SQ_SZ, SQ_SZ, true);
        shroom = Bitmap.createScaledBitmap(shroom, SQ_SZ, SQ_SZ, true);

        pipe = Bitmap.createScaledBitmap(pipe, SQ_SZ, SQ_SZ, true);
        plant = Bitmap.createScaledBitmap(plant, SQ_SZ, SQ_SZ, true);
        
        goomba = Bitmap.createScaledBitmap(goomba, SQ_SZ, SQ_SZ, true);

        mario = Bitmap.createScaledBitmap(mario, 2*SQ_SZ/3, SQ_SZ, true);
        supermario = Bitmap.createScaledBitmap(supermario, 2*SQ_SZ/3, SQ_SZ, true);
        naruto =  Bitmap.createScaledBitmap(naruto, 2*SQ_SZ/3, SQ_SZ, true);

        pole = Bitmap.createScaledBitmap(pole, SQ_SZ, SQ_SZ*2, true);
        flag = Bitmap.createScaledBitmap(flag, SQ_SZ, SQ_SZ*2, true);
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }


    // Need to update board scroll, enemies, and mario
    public void updateBoard(int[][] board) {
        start = false;
        for (int j = 0; j < B_HEIGHT; j++) {
            for (int i = 0; i < B_WIDTH; i++) {
                currBoard[j][i] = board[j][i];
            }
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Screen Dimensions
        width = getWidth() / B_WIDTH;
        height = getHeight() / B_HEIGHT;
        int x = 0, y = 0;
        if (!start)
            for (int j = 0; j < B_HEIGHT; j++) {
                for (int i = 0; i < B_WIDTH; i++) {
                    y = j * height;
                    x = i * width;
                    //System.out.println("Width"+getWidth());
                    //System.out.println("X: " + x + " Y: " + y+" i: "+i+" j: "+j);
                    if (x > getWidth()) {
                        x=0;
                    }
                    if (currBoard[j][i] == -2) {
                        canvas.drawBitmap(emptygift, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == -3) {
                        canvas.drawBitmap(coingift, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == -4) {
                        canvas.drawBitmap(stargift, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == -5) {
                        canvas.drawBitmap(shroomgift, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == -1) {
                        canvas.drawBitmap(sky, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == 1) {
                        canvas.drawBitmap(brick, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == 2) {
                        canvas.drawBitmap(mystery, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == 3) {
                        canvas.drawBitmap(coin, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == 4) {
                        canvas.drawBitmap(star, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == 5) {
                        canvas.drawBitmap(shroom, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == 6) {
                        canvas.drawBitmap(pipe, x, y, null);// Paint[0][0]);
                    } else if (currBoard[j][i] == 7){
                        if (Math.abs(MarioX-i) < 2 ) {
                            canvas.drawBitmap(plant, x, y, null);//Paint[0][0]);
                           // System.out.println("MarioY: "+MarioY+ "J: "+j);
                           // System.out.println("MarioX: "+MarioX+ "I: "+i);
                        }
                        /*else
                          System.out.println (Math.abs(MarioX-i));
                        System.out.println("MarioX: "+MarioX+ "I: "+i);
                        */

                    } else if (currBoard[j][i] == 8) {
                        canvas.drawBitmap(goomba, x, y, null);//Paint[0][0]);
                    } else if (currBoard[j][i] == 9) {
                        canvas.drawBitmap(mario, x, y, null);//Paint[0][0]);
                    }else if (currBoard[j][i] == 10) {
                        canvas.drawBitmap(supermario, x, y, null);//Paint[0][0]);
                    }else if (currBoard[j][i] == 11) {
                        canvas.drawBitmap(naruto, x, y, null);//Paint[0][0]);
                    }else if (currBoard[j][i] == 12 ) {
                        canvas.drawBitmap(pole, x, y, null);//Paint[0][0]);
                    }else if (currBoard[j][i] == 13 ) {
                        canvas.drawBitmap(flag, x, y, null);//Paint[0][0]);
                    }
                }
            }
        if (finish)
            System.out.println("gameover");
    }
    public void GetMarioLocation(int x, int y){
        MarioX = x;
        MarioY = y;
    }

}

