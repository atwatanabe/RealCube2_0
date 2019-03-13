package com.example.anthony.realcube2_0;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Triangle extends Shape
{
//    private FloatBuffer vertexBuffer;
//
//    private final int mProgram;
//
//    private int positionHandle;
//    private int colorHandle;
//
//    private int vPMatrixHandle;
//
//    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
//
//    private final int vertexStride = COORDS_PER_VERTEX * 4;
//
//    private final String vertexShaderCode =
//            "uniform mat4 uMVPMatrix;" +
//            "attribute vec4 vPosition;" +
//            "void main() {" +
//            "    gl_Position = uMVPMatrix * vPosition;" +
//            "}";
//    private final String fragmentShaderCode =
//            "precision mediump float;" +
//            "uniform vec4 vColor;" +
//            "void main() {" +
//            "    gl_FragColor = vColor;" +
//            "}";

    //static final int COORDS_PER_VERTEX = 3;
//    static float[] triangleCoords = {
//            0.0f,  0.622008459f, 0.0f,
//            -0.5f, -0.311004243f, 0.0f,
//            0.5f, -0.311004243f, 0.0f
//    };

    public Triangle()
    {
        //COORDS_PER_VERTEX = 3;
        vertexCount = 3;

        coords = new float[] {
                0.0f,  0.622008459f, 0.0f,
                -0.5f, -0.311004243f, 0.0f,
                0.5f, -0.311004243f, 0.0f
        };

        color = new float[] {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix)
    {
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glUseProgram(mProgram);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
