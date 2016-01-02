package com.abs192.snake;

import android.graphics.Point;

/**
 * Created by abs on 24/12/15.
 */
public class Piece {

    Point point;
    SnakeGame.DIRECTION direction;

    public Piece(Piece p) {
        this.direction = p.direction;
        this.point = new Point(p.point);
    }

    public Piece(SnakeGame.DIRECTION direction, Point point) {
        this.direction = direction;
        this.point = point;
    }

    @Override
    public String toString() {
        return point + "-" + direction.toString().charAt(0);
    }
}
