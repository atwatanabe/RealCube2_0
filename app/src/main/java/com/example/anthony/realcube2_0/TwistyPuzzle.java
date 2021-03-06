package com.example.anthony.realcube2_0;

import java.util.List;
//import org.apache.commons.*;
public abstract class TwistyPuzzle
{
    protected List<Piece> pieces;

    public void draw(float[] mvpMatrix)
    {
        for (Piece p : pieces)
            p.draw(mvpMatrix);
    }

    public int getNumCoords()
    {
        int total = 0;
        for (Piece p : pieces)
            total += p.getNumCoords();
        return total;
    }

    public float[] getCoords()
    {
        float[] result = new float[getNumCoords()];
        int index = 0;

        for (Piece p : pieces)
        {
            int length = p.getNumCoords();
            System.arraycopy(p.getCoords(), 0, result, index, length);
            index += length;
        }

        return result;
    }
}
