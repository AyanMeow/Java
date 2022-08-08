package com.xyygame;

import java.util.LinkedList;

public class Snake {
    private LinkedList<Gnode> body;
    private Dreiction dreiction = Dreiction.UP;
    private boolean isLiving = true;
    private int score=0;

    public boolean getLiving() {
        return isLiving;
    }

    public void setLiving(boolean isLiving) {
        this.isLiving = isLiving;
    }

    public void eatFood() {
        Gnode last = this.body.getLast();
        switch (this.dreiction) {
        case UP:
            this.body.addLast(new Gnode(last.getX(), last.getY() + 1));
            break;
        case DOWN:
            this.body.addLast(new Gnode(last.getX(), last.getY() - 1));
            break;
        case RIGHT:
            this.body.addLast(new Gnode(last.getX() + 1, last.getY()));
            break;
        case LIFHT:
            this.body.addLast(new Gnode(last.getX() - 1, last.getY()));
            break;
        default:
            break;
        }
    }

    public void setDrt(Dreiction dreiction) {
        this.dreiction = dreiction;
    }

    public Dreiction getDrt() {
        return this.dreiction;
    }

    public Snake() {
        initsnake();
    }

    public void initsnake() {
        body = new LinkedList<>();

        body.add(new Gnode(20, 20));
        body.add(new Gnode(21, 20));
        body.add(new Gnode(22, 20));
        body.add(new Gnode(23, 20));
        body.add(new Gnode(24, 20));
        body.add(new Gnode(25, 20));
        score=0;
    }
    
    public LinkedList<Gnode> getBody() {
        return body;
    }

    // public void setBody(LinkedList<Gnode> body){this.body=body;}
    // the snake will move along the dreiction of the head of snake;
    public void move() {
        Gnode head = body.getFirst();
        if (isLiving) {
            switch (dreiction) {
            case UP:
                body.addFirst(new Gnode(head.getX(), (head.getY() - 1 + 40) % 40));
                body.removeLast();
                break;
            case DOWN:
                body.addFirst(new Gnode(head.getX(), (head.getY() + 1 + 40) % 40));
                body.removeLast();
                break;
            case LIFHT:
                body.addFirst(new Gnode(head.getX() - 1, head.getY()));
                body.removeLast();
                break;
            case RIGHT:
                body.addFirst(new Gnode(head.getX() + 1, head.getY()));
                body.removeLast();
                break;
            default:
                break;
            }
        }

        if (this.body.getFirst().getX() <= 0 || this.body.getFirst().getX() >= 39)
            isLiving = false;
            
        for (var i = 1; i < this.body.size(); i++) {
            Gnode node = this.body.get(i);
            if (this.body.getFirst().getX() == node.getX() && this.body.getFirst().getY() == node.getY())
                isLiving = false;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
