package com.example.anthony.realcube2_0;

import java.util.ArrayList;

public class PieceCuboidCenter extends Piece
{

    public PieceCuboidCenter(float[] coords, float[] color)
    {
        faces = new ArrayList<>(1);
        faces.add(new Square(coords, color));
    }

}
