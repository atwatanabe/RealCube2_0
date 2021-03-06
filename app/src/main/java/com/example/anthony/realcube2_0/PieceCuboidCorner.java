package com.example.anthony.realcube2_0;

import android.util.Log;

import java.util.ArrayList;

public class PieceCuboidCorner extends Piece
{
    public PieceCuboidCorner(float[] coords, float[][] colors)
    {
        try
        {
            faces = new ArrayList<Shape>(3);
            int squareStride = Square.coordsPerVertex * Square.verticesPerSquare;
            float[] face0 = new float[squareStride];
            float[] face1 = new float[squareStride];
            float[] face2 = new float[squareStride];
            System.arraycopy(coords, 0, face0, 0, squareStride);
            System.arraycopy(coords, squareStride, face1, 0, squareStride);
            System.arraycopy(coords, 2 * squareStride, face2, 0, squareStride);
            faces.add(new Square(face0, colors[0]));
            faces.add(new Square(face1, colors[1]));
            faces.add(new Square(face2, colors[2]));
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Log.d("PieceCuboidCorner", e.toString());
        }
    }
}
