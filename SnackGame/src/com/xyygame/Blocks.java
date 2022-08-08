package com.xyygame;

import java.util.LinkedList;

public class Blocks {
    private LinkedList<Gnode> blockss;
    
    public  Blocks() {
        initBlocks();
    }

    private void initBlocks()
    {
        blockss=new LinkedList<>();
        addnewBlcok();
    }

    public LinkedList<Gnode> getBlocks() {
        return blockss;
    }

    public void addnewBlcok()
    {
        Gnode block=new Gnode();
        do{
        block.genrandom();
        }while(block.getX()>=38||block.getY()>=38);
        this.blockss.add(block);
    }
}
