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

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

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

    public abstract void draw(float[] mvpMatrix);

    public float[] getCoords()
    {
        return coords;
    }

    public int getNumCoords()
    {
        return vertexCount * COORDS_PER_VERTEX;
    }
}
