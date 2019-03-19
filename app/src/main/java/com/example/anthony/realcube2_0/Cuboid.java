package com.example.anthony.realcube2_0;

import java.util.ArrayList;

public class Cuboid extends TwistyPuzzle
{
    private int width;
    private int height;
    private int depth;
    private float sideLength;   //the length of the side of one tile (since not all
                                //sides of a cuboid are necessarily the same length
    private float[][] colors;
    private float spacing;

    public Cuboid(int w, int h, int d, float sl, float sp, float[][] mColors)
    {
        width = w;
        height = h;
        depth = d;
        sideLength = sl;
        spacing = sp;
        colors = mColors;
        init();
    }

    private void init()
    {
        pieces = new ArrayList<Piece>();

        //generate pieces of cuboid and add them to @pieces

        float unit = sideLength + spacing;
        float halfUnit = unit / 2;
        float widthRadius = width * halfUnit;
        float heightRadius = height * halfUnit;
        float depthRadius = depth * halfUnit;

        
    }

}
