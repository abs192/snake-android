package com.abs192.snake;

import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by abs on 21/12/15.
 */
public class Snake implements Serializable {

    ArrayList<Piece> body;
    SnakeGame.DIRECTION direction;
    public Snake() {
        this.body = new ArrayList<Piece>();
        body.add(new Piece(SnakeGame.DIRECTION.NORTH, new Point(5, 6)));
        body.add(new Piece(SnakeGame.DIRECTION.NORTH, new Point(5, 7)));
        body.add(new Piece(SnakeGame.DIRECTION.WEST, new Point(6, 7)));
        body.add(new Piece(SnakeGame.DIRECTION.WEST, new Point(7, 7)));
        body.add(new Piece(SnakeGame.DIRECTION.WEST, new Point(8, 7)));
        body.add(new Piece(SnakeGame.DIRECTION.WEST, new Point(9, 7)));
        body.add(new Piece(SnakeGame.DIRECTION.WEST, new Point(10, 7)));
        body.add(new Piece(SnakeGame.DIRECTION.WEST, new Point(11, 7)));
        body.add(new Piece(SnakeGame.DIRECTION.NORTH, new Point(11, 8)));
        body.add(new Piece(SnakeGame.DIRECTION.NORTH, new Point(11, 9)));

    }

    public ArrayList<Piece> getBody() {
        return body;
    }

    public int getLength() {
        if (body != null) return body.size();
        return 0;
    }

    public void setBody(ArrayList<Piece> body) {
        this.body = body;
    }


}
