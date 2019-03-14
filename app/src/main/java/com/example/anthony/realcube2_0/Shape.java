package com.example.anthony.realcube2_0;

import java.nio.FloatBuffer;

public abstract class Shape
{
    protected float[] coords;
    public static final int COORDS_PER_VERTEX = 3;
    protected short[] drawOrder;

    protected FloatBuffer vertexBuffer;

    protected int mProgram;

    protected int positionHandle;
    protected int colorHandle;
    protected int vPMatrixHandle;

    protected int vertexCount;
    protected int vertexStride; //= coords.length * COORDS_PER_VERTEX;

    protected float[] color;

    public static final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "    gl_Position = uMVPMatrix * vPosition;" +
                    "}";
    public static final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "    gl_FragColor = vColor;" +
                    "}";

    //private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;

    //private final int vertexStride = COORDS_PER_VERTEX * 4;



//    public Shape(float[] c, int cpv)
//    {
//        coords = new float[c.length];
//        System.arraycopy(c, 0, coords, 0, coords.length);
//        COORDS_PER_VERTEX = cpv;
//        //mProgram = GLES20.glCreateProgram();
//        vertexStride = coords.length * COORDS_PER_VERTEX;
//    }
//
//    public Shape()
//    {
//        coords = new float[9];
//        COORDS_PER_VERTEX = 3;
//        //mProgram = GLES20.glCreateProgram();
//        vertexStride = coords.length * COORDS_PER_VERTEX;
//    }

    public abstract void draw(float[] mvpMatrix);

}
