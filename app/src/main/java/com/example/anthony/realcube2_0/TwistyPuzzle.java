package com.example.anthony.realcube2_0;

import java.util.List;

public abstract class TwistyPuzzle
{
    protected List<Piece> pieces;

    public void draw(float[] mvpMatrix)
    {
        for (Piece p : pieces)
            p.draw(mvpMatrix);
    }
}
