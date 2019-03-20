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

    public Cuboid()
    {
        width = 3;
        height = 3;
        depth = 3;
        sideLength = 0.5f;
        spacing = 0.1f;

        float[] white = {0xff / 256f, 0xff / 256f, 0xff / 256f};    //up
        float[] yellow = {0xff / 256f, 0xd5 / 256f, 0x00 / 256f};   //down
        float[] red = {0xc4 / 256f, 0x1e / 256f, 0x3a / 256f};      //left
        float[] orange = {0xff / 256f, 0x58 / 256f, 0x00 / 256f};   //right
        float[] blue = {0x00 / 256f, 0x51 / 256f, 0xba / 256f};     //front
        float[] green = {0x00 / 256f, 0x9e / 256f, 0x60 / 256f};    //back

        colors = new float[][] {white, yellow, red, orange, blue, green};
        init();
    }

    private void init()
    {
        pieces = new ArrayList<Piece>();

        //generate pieces of cuboid and add them to @pieces

        float unit = sideLength + spacing;
        float halfUnit = unit / 2f;
        float widthRadius = width * halfUnit;
        float heightRadius = height * halfUnit;
        float depthRadius = depth * halfUnit;

        float[] up = Square.generateFace(width, depth, sideLength, spacing, Cube3x3x3.Side.Up, heightRadius);
        float[] down = Square.generateFace(width, depth, sideLength, spacing, Cube3x3x3.Side.Down, heightRadius);
        float[] left = Square.generateFace(depth, height, sideLength, spacing, Cube3x3x3.Side.Left, widthRadius);
        float[] right = Square.generateFace(depth, height, sideLength, spacing, Cube3x3x3.Side.Right, widthRadius);
        float[] front = Square.generateFace(width, height, sideLength, spacing, Cube3x3x3.Side.Front, depthRadius);
        float[] back = Square.generateFace(width, height, sideLength, spacing, Cube3x3x3.Side.Back, depthRadius);



    }

}
