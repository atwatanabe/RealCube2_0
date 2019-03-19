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

    public int getNumCoords()
    {
        int total = 0;
        for (Shape s : faces)
            total += s.getNumCoords();
        return total;
    }

    public float[] getCoords()
    {
        float[] result = new float[getNumCoords()];
        int index = 0;

        for (Shape s : faces)
        {
            int length = s.getNumCoords();
            System.arraycopy(s.getCoords(), 0, result, index, length);
            index += length;
        }

        return result;
    }
}
