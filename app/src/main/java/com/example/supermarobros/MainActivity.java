package com.example.supermarobros;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean isTouch = false; // control touch events
    private MediaPlayer music, jumpsound;/*narutoSong*/
    private ImageView mLogo;
    private Button mPlayButton;
    private TextView mDevelopers;
    private TextView mGameLevels;
    private TextView mScore;
    private ImageView mHeart1;
    private ImageView mHeart2;
    private ImageView mHeart3;
    private int moveupcount = 0;
    private boolean moveupbool =  true; //newadd
    // Mario move set
    private boolean moveRight;
    private boolean moveLeft;
    private boolean moveUp;
    // Detect invisible Mario
    private static int invisibleCounter;
    // Jump Detection //
    private boolean didJump;
    // TIMER CONTROL //
    private CountDownTimer mPressTimer, mScoreTimer;
    private static final long START_TIME_IN_MILLIS = 120000;
    private long mTimeLeftinMilli = START_TIME_IN_MILLIS;
    private boolean mTimerRunnning;
    // Board Declaration //
    private Board gameBoard = new Board();
    private int leftBound = 0;
    private int[][] board = gameBoard.getBoard(leftBound);
    private int playerScore = gameBoard.getPlayerScore();
    private String displayScore = Integer.toString(playerScore); // display player score
    private String displayLevel;
    private int playerLives = gameBoard.getPlayerLives(); // display player lives



    private int ThreadCount = 0;
    // UI //
    private com.example.supermarobros.CustomView mCustomView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mCustomView = (com.example.supermarobros.CustomView) findViewById(R.id.CustomView);

        // MUSIC //
        music = MediaPlayer.create(MainActivity.this, R.raw.music);
        jumpsound = MediaPlayer.create(MainActivity.this, R.raw.jumping);
        //narutoSong = MediaPlayer.create(MainActivity.this, R.raw.flute);
        // sound effects: https://themushroomkingdom.net/media/smb/wav

        // MOVE CONTROLS //
        moveRight = false;
        moveLeft = false;
        moveUp = false;



        // PLAY GAME //
        mLogo = findViewById(R.id.logoView);
        mDevelopers = findViewById(R.id.developerView);
        mPlayButton = findViewById(R.id.btn_play);
        mGameLevels = findViewById(R.id.levelView);
        mScore = findViewById(R.id.scoreView);
        mHeart1 = findViewById(R.id.heart1);
        mHeart2 = findViewById(R.id.heart2);
        mHeart3 = findViewById(R.id.heart3);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogo.setVisibility(View.GONE);
                mDevelopers.setVisibility(View.GONE);
                mPlayButton.setVisibility(View.GONE);
                mGameLevels.setVisibility(View.VISIBLE);
                mScore.setVisibility(View.VISIBLE);
                // Keep track of points and level
                mGameLevels.setText(mGameLevels.getText().toString() + "1");
                mScore.setText(mScore.getText().toString() + "0");
                // Change visibility to display change how many lives
                mHeart1.setVisibility(View.VISIBLE);
                mHeart2.setVisibility(View.VISIBLE);
                mHeart3.setVisibility(View.VISIBLE);
                //mPlayButton.setVisibility(View.GONE);
                music.start();
                music.setLooping(true);
                mCustomView.updateBoard(board);
                gameBoard.updateLevel(); // initial level
                invisibleCounter = 0;
                startTimer();
                ScoreTimer();

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int X = (int) event.getX() / (int) getResources().getDisplayMetrics().density;
        int Y = (int) event.getY() / (int) getResources().getDisplayMetrics().density;
        int move = event.getAction();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int Width = metrics.widthPixels / (int) getResources().getDisplayMetrics().density;   // PIXEL 1794  DP actual (897) used (720)
        int Height = metrics.heightPixels / (int) getResources().getDisplayMetrics().density; // PIXEL 1080 DP actual (540) used (400)


        switch (move) {

            // START THREAD //
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                runNewThread("down");
                if (X > Width / 2 && Y >= Height / 2) {
                    // move right
                    //Toast.makeText(this, "RIGHT\n" + "X: " + X + " Y: " + Y + "\nScreen Dimension W: " + Width + "\nScreen Dimension H: " + Height, Toast.LENGTH_SHORT).show();
                    moveRight = true;

                } else if (X <= Width / 2 && Y >= Height / 2) {
                    // move left
                    //Toast.makeText(this, "LEFT\n" + "X: " + X + " Y: " + Y + "\nScreen Dimension W: " + Width + "\nScreen Dimension H: " + Height, Toast.LENGTH_SHORT).show();
                    moveLeft = true;
                } else if (Y < Height / 2) {
                    // jump
                    //Toast.makeText(this, "JUMP\n" + "X: " + X + " Y: " + Y + "\nScreen Dimension W: " + Width + "\nScreen Dimension H: " + Height, Toast.LENGTH_SHORT).show();
                    moveUp = true;
                }
                //startTimer();
                break;

            // STOP THREAD (when touch is released) //
            case MotionEvent.ACTION_UP:
                // Toast.makeText(this, "CONTROL RELEASED\n " + "X: " + X + " Y: " + Y, Toast.LENGTH_SHORT).show();
                moveRight = false;
                moveLeft = false;
                moveUp = false;
                System.out.println("move reset");
                //pauseTimer();
                //ScoreTimer();
                break;

            // CONTROLS OUTofBOUNDS //
            case (MotionEvent.ACTION_OUTSIDE):
                //Toast.makeText(this, "Movement occurred outside bounds " + "X: " + X + " Y: " + Y, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    // Update UI method
    private void updateUI(){

        // Update Level
        calculateLeftBound(gameBoard.getPlayerX());
        displayLevel = "Level: " + Integer.toString(Board.currentLevel + 1);
        mGameLevels.setText(displayLevel);
        displayLevel = "";
        // Update Score
        playerScore = gameBoard.getPlayerScore();
        displayScore = "Score: " + Integer.toString(playerScore);
        mScore.setText(displayScore);
        displayScore = "";

        // Update Lives
        playerLives = gameBoard.getPlayerLives();
        if (playerLives == 2) {
            mHeart3.setVisibility(View.GONE);
        } else if (playerLives == 1) {
            mHeart1.setVisibility(View.GONE);
        } else if (playerLives == 0) {
            mHeart2.setVisibility(View.GONE);
            System.out.println("Player Life: " + playerLives);
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
            gameBoard.restartGame();
            leftBound = 0;
            board = gameBoard.getBoard(leftBound);
            mCustomView.updateBoard(board);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            invisibleCounter = 0;
        }
        // Update Board UI
        mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
        board = gameBoard.getBoard(leftBound);
        //gameBoard.moveEnemy();
        mCustomView.updateBoard(board);

        if (Board.END){
            Toast.makeText(this, "CONGRATULATIONS YOU WIN", Toast.LENGTH_SHORT).show();
            Board.END = false;
            mHeart3.setVisibility(View.VISIBLE);
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
        }
    }

    // Enemy Control
    private void startTimer() {
        mPressTimer = new CountDownTimer(mTimeLeftinMilli, 200/*interval*/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftinMilli = START_TIME_IN_MILLIS;
                System.out.println("Declared in Start Timer " + mTimeLeftinMilli);

                updateUI();

                // EXECUTE MOVE CONTROLS //
                if (moveRight) {
                    calculateLeftBound(gameBoard.getPlayerX());
                    gameBoard.moveRight();
                    //runNewThread("right");
                    System.out.println("mario moving right");

                } else if (moveLeft) {
                    calculateLeftBound(gameBoard.getPlayerX());
                    gameBoard.moveLeft();
                    //runNewThread("left");
                    System.out.println("mario moving left");

                } else if (moveUp) {
                    if (moveupcount > 1) {
                        moveupbool = false;
                    }
                    if (moveupbool){
                        gameBoard.moveUp();
                        moveupcount++;
                    }

                    //runNewThread("up");
                    jumpsound.start();
                    System.out.println("mario moving up");
                }
                if (gameBoard.FindInvisible()){
                    //music.stop();
                    //narutoSong.start();
                    invisibleCounter++;
                    System.out.println("counter: "+invisibleCounter);
                    if (invisibleCounter >100){
                        //narutoSong.stop();
                        gameBoard.revert();
                        invisibleCounter = 0;
                        System.out.println("counter: "+invisibleCounter);
                    }
                }
            }

            @Override
            public void onFinish() {
                mTimerRunnning = false;
            }
        }.start();
        mTimerRunnning = true;
        System.out.println("Declared when running " + mTimeLeftinMilli);
        mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
        board = gameBoard.getBoard(leftBound);
        //gameBoard.moveEnemy();

        mCustomView.updateBoard(board);
    }

    private void ScoreTimer() {
        mScoreTimer = new CountDownTimer(mTimeLeftinMilli, 200/*interval*/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftinMilli = START_TIME_IN_MILLIS;
                updateUI();
                if (gameBoard.FindInvisible()){
                    //music.stop();
                    //narutoSong.start();
                    invisibleCounter++;
                    if (invisibleCounter >100){
                        //narutoSong.stop();
                        gameBoard.revert();
                        invisibleCounter = 0;
                        //music = MediaPlayer.create(MainActivity.this, R.raw.music);
                        //music.start();
                    }
                }
            }
            @Override
            public void onFinish() {
                mTimerRunnning = false;
            }
        }.start();
        mTimerRunnning = true;
        System.out.println("Declared when running " + mTimeLeftinMilli);
        mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
        board = gameBoard.getBoard(leftBound);
        //gameBoard.moveEnemy();
        mCustomView.updateBoard(board);
    }

    private void pauseTimer() {
        updateUI();
        mPressTimer.cancel();
        resetTimer();
        System.out.println("Paused: " + mTimeLeftinMilli);
        mTimerRunnning = false;

    }

    private void resetTimer() {
        mTimeLeftinMilli = START_TIME_IN_MILLIS;
    }

    // Thread (Handle Mario Move-set)
    private void runNewThread(String name) {

        if (name.equals("up")) {
            JumpUp up = new JumpUp("Up Thread" + ThreadCount);
            ThreadCount++;
            up.start();
        }
        if (name.equals("right")) {
            MoveRight right = new MoveRight("Right Thread" + ThreadCount);
            ThreadCount++;
            right.start();
        }
        if (name.equals("left")) {
            MoveLeft left = new MoveLeft("Left Thread" + ThreadCount);
            ThreadCount++;
            left.start();
        }
        if (name.equals("down") && ThreadCount<2) {
            MoveDown down = new MoveDown("Down Thread" + ThreadCount);
            ThreadCount++;
            down.start();
        }

    }

    class JumpUp implements Runnable {
        private Thread t;
        private String name;

        JumpUp(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("JumpUp " + name + "Number of threads: " + ThreadCount);
            gameBoard.moveUp();
            mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
            board = gameBoard.getBoard(leftBound);
            mCustomView.updateBoard(board);
            try {
                Thread.sleep(2000); // jump delay
                gameBoard.moveDown();
                //gameBoard.moveEnemy();
                mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
                board = gameBoard.getBoard(leftBound);
                mCustomView.updateBoard(board);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }
            if (!moveUp) { // multi-jump
                moveUp = true;
            }
        }

        private void start() {
            if (t == null) {
                moveUp = false;
                t = new Thread(this, name);
                t.start();
            }
        }
    }

    class MoveRight implements Runnable {
        private Thread t;
        private String name;

        MoveRight(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            //System.out.println("MoveRight " + name + "Number of threads: "+ThreadCount);
            gameBoard.moveRight();
            calculateLeftBound(gameBoard.getPlayerX());
            mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
            board = gameBoard.getBoard(leftBound);
            mCustomView.updateBoard(board);
        }

        private void start() {
            if (t == null) {
                t = new Thread(this, name);
                t.start();
            }
        }
    }

    class MoveLeft implements Runnable {
        private Thread t;
        private String name;

        MoveLeft(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            //System.out.println("MoveLeft " + name + "Number of threads: "+ThreadCount);
            gameBoard.moveLeft();
            calculateLeftBound(gameBoard.getPlayerX());
            mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
            board = gameBoard.getBoard(leftBound);
            mCustomView.updateBoard(board);
        }

        private void start() {
            if (t == null) {
                t = new Thread(this, name);
                t.start();
            }
        }
    }

    class MoveDown implements Runnable {
        private Thread t;
        private String name;

        MoveDown(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            //System.out.println("MoveDown " + name + "Number of threads: " + ThreadCount);
            while (true) {
                gameBoard.getPlayerX();
                mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
                board = gameBoard.getBoard(leftBound);
                mCustomView.updateBoard(board);
                try {
                    Thread.sleep(2000); // jump delay
                    moveupcount--;
                    if (moveupcount <= 0){
                        moveupcount = 0;
                        moveupbool = true;
                    }
                    gameBoard.moveDown();
                    gameBoard.moveEnemy();
                    mCustomView.GetMarioLocation(gameBoard.getPlayerX()-leftBound, gameBoard.getPlayerY());
                    board = gameBoard.getBoard(leftBound);
                    mCustomView.updateBoard(board);
                } catch (InterruptedException e) {
                    System.out.println("got interrupted!");
                }
            }
        }

        private void start() {
            t = new Thread(this, name);
            t.start();
        }
    }
    private void calculateLeftBound (int xlocation) {
        if (xlocation < 10) {
            leftBound = 0;
        } else if (xlocation < 30) {
            leftBound = xlocation - 10;
        } else
            leftBound = 20;
    }

}
