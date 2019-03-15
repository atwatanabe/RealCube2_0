package com.example.anthony.realcube2_0;

import java.util.List;

public abstract class Piece
{
    protected List<Shape> faces;

    public void draw(float[] mvpMatrix)
    {
        for (Shape s : faces)
            s.draw(mvpMatrix);
    }
}
