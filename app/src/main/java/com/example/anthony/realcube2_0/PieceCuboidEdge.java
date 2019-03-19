package com.example.anthony.realcube2_0;

import android.util.Log;

public class PieceCuboidEdge extends Piece
{
    public PieceCuboidEdge(float[] coords, float[][] colors)
    {
        try
        {
            int squareStride = Square.coordsPerVertex * Square.verticesPerSquare;
            float[] face0 = new float[squareStride];
            float[] face1 = new float[squareStride];
            System.arraycopy(coords, 0, face0, 0, squareStride);
            System.arraycopy(coords, squareStride, face1, 0, squareStride);
            faces.add(new Square(face0, colors[0]));
            faces.add(new Square(face1, colors[1]));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Log.d("PieceCuboidCorner", e.toString());
        }
    }
}
