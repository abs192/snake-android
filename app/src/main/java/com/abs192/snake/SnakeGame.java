package com.abs192.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by abs on 21/12/15.
 */
public class SnakeGame extends SurfaceView {


    private Paint paint;
    public static boolean debug_mode = true;
    Snake snake;
    private GameLoopThread gameLoopThread;
    private SurfaceHolder holder;

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
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        snake = new Snake();
        currentState = State.DONE;

        paint = new Paint();
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

        switch (snake.getDirection()) {
            case NORTH:
                break;
            case SOUTH:
                Point p = snake.getBody().get(0);
                p.y += 1;
                p.y %= 32;
                snake.body.set(0, p);
                break;
            case WEST:
                break;
            case EAST:
                break;
        }
    }

    private void drawSnake(Canvas canvas) {

        paint.setStyle(Paint.Style.FILL);

        ArrayList<Point> body = snake.getBody();
        Point p = body.get(0);
        int i = p.x;
        int j = p.y;
        RectF r = new RectF(x * i, y * j, x * (i + 1), y * (j + 1));
        RectF rS = new RectF(r);

        float startAngle = 0;
        switch (snake.getDirection()) {
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

        for (Point po : body.subList(1, body.size())) {
            i = po.x;
            j = po.y;
            r = new RectF(x * i, y * j, x * (i + 1), y * (j + 1));
            canvas.drawRect(r, paint);
        }

        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawBlocks(Canvas canvas) {
        paint.setColor(Color.parseColor("#7DFF5E"));

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

}
