package com.xyygame;

import java.util.*;

public class Gnode {
    private int x;
    private int y;

    private Random r=new Random();

    public  Gnode(int x,int y) {
        this.x=x;
        this.y=y;
    }

    public  Gnode() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void  setX() {
        this.x=x;
    }

    public void setY() {
        this.y=y;
    }

    public  void genrandom() {
        this.x=r.nextInt(40);
        this.y=r.nextInt(40);
    }
}
