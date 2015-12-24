package com.abs192.snake;

import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by abs on 21/12/15.
 */
public class Snake implements Serializable {

    ArrayList<Point> body;
    SnakeGame.DIRECTION direction;

    public Snake() {
        this.body = new ArrayList<Point>();
        body.add(new Point(5, 6));
        setDirection(SnakeGame.DIRECTION.SOUTH);
    }

    public ArrayList<Point> addPoint() {
        return body;
    }


    public ArrayList<Point> getBody() {
        return body;
    }

    public int getLength() {
        if (body != null) return body.size();
        return 0;
    }

    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    public SnakeGame.DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(SnakeGame.DIRECTION direction) {
        this.direction = direction;
    }
}
