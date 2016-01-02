package com.abs192.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by abs on 21/12/15.
 */
public class SnakeGame extends SurfaceView {


    private Paint paint;
    public static boolean debug_mode = false;
    Snake snake;
    private GameLoopThread gameLoopThread;
    private SurfaceHolder holder;
    private ArrayList<Piece> tempsnakeBody;

    enum State {PAUSED, PLAYING, DONE}

    enum DIRECTION {NORTH, EAST, SOUTH, WEST}

    State currentState;
    private final static int N_W = 16;
    private final static int N_H = 32;

    float x, y;

    public SnakeGame(Context context) {
        super(context);
        init();
    }

    public SnakeGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                try {
                    gameLoopThread.start();
                } catch (IllegalThreadStateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        snake = new Snake();
        tempsnakeBody = new ArrayList<>();
        for (Piece p : snake.getBody()) {
            tempsnakeBody.add(new Piece(p));
        }
        currentState = State.DONE;

        paint = new Paint();
        paint.setColor(Color.parseColor("#7DFF5E"));
        paint.setStyle(Paint.Style.STROKE);
    }

    public void pause() {
        currentState = State.PAUSED;
        gameLoopThread.setRunning(false);
    }

    public void resume() {
        currentState = State.PLAYING;
        gameLoopThread.setRunning(true);
    }

    public void restart() {
        currentState = State.DONE;
        snake = new Snake();
        //randomize
        currentState = State.PLAYING;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = getWidth() / N_W;
        y = getHeight() / N_H;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (debug_mode) {
            drawBlocks(canvas);
        }
        drawSnake(canvas);

        if (currentState == State.PLAYING) {

        }

        Piece p = new Piece(snake.body.get(0));
        Piece p0 = new Piece(snake.body.get(0));
        switch (p.direction) {
            case NORTH:
                if (p.point.y == 0)
                    p.point.y = N_H;
                else {
                    p.point.y -= 1;
                    p.point.y %= N_H;
                }
                break;
            case SOUTH:
                if (p.point.y == N_H)
                    p.point.y = 0;
                else {
                    p.point.y += 1;
                    p.point.y %= N_H;
                }
                snake.body.set(0, p);
                break;
            case WEST:
                if (p.point.x == 0)
                    p.point.x = N_W;
                else {
                    p.point.x -= 1;
                    p.point.x %= N_W;
                }
                break;
            case EAST:
                if (p.point.x == N_W)
                    p.point.x = 0;
                else {
                    p.point.x += 1;
                    p.point.x %= N_W;
                }
                break;
        }

        //had to make use of copy constructors. List work in references and those cause problems
        tempsnakeBody.set(0, new Piece(p));
        for (int i = 0; i < snake.getLength() - 1; i++) {
            tempsnakeBody.set(i + 1, new Piece(snake.getBody().get(i)));
        }

        for (int i = snake.getLength() - 1; i >= 0; i--) {
            snake.body.set(i, new Piece(tempsnakeBody.get(i)));
        }

        System.out.println(snake.body);
    }

    private void drawSnake(Canvas canvas) {

        paint.setStyle(Paint.Style.FILL);

        ArrayList<Piece> body = snake.getBody();
        Piece p = body.get(0);
        int i = p.point.x;
        int j = p.point.y;
        RectF r = new RectF(x * i, y * j, x * (i + 1), y * (j + 1));
        RectF rS = new RectF(r);

        float startAngle = 0;
        switch (snake.getBody().get(0).direction) {
            case NORTH:
                startAngle = 180;
                rS.top += y / 2;
                break;
            case SOUTH:
                startAngle = 0;
                rS.bottom -= y / 2;
                break;
            case WEST:
                startAngle = 90;
                rS.left += x / 2;
                break;
            case EAST:
                startAngle = 270;
                rS.right -= x / 2;
                break;
        }
        canvas.drawArc(r, startAngle, 180, false, paint);
        canvas.drawRect(rS, paint);

        int k = 0;
        for (Piece po : body.subList(1, body.size())) {
            i = po.point.x;
            j = po.point.y;
            r = new RectF(x * i, y * j, x * (i + 1), y * (j + 1));
            canvas.drawRect(r, paint);
            if (debug_mode) {
                k++;
                paint.setColor(Color.parseColor("#000000"));
                canvas.drawText("" + k, x * i + x / 2, y * j + y / 2, paint);
                paint.setColor(Color.parseColor("#7DFF5E"));
            }
        }

        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawBlocks(Canvas canvas) {
        for (int i = 0; i < N_W; i++) {
            for (int j = 0; j < N_H; j++) {
                canvas.drawRect(x * i, y * j, x * (i + 1), y * (j + 1), paint);
//                canvas.drawText(i + "," + j, x * i + x / 2, y * j + y / 2, paint);
            }
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                Piece head = snake.getBody().get(0);
//                Point p = head.po

                if (x > getHeight() / 2 && y < getHeight()) {
                    //north
//                } else if () {
//                    south
//                } else if () {
                    //west
                } else {
                    //east
                }

                break;
        }
        return super.onTouchEvent(event);
    }
}
