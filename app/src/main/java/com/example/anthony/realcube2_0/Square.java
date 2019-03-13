package com.example.anthony.realcube2_0;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square extends Shape
{
    //private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    static float[] squareCoords = {
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f,  0.5f, 0.0f
    };

    public static float[] generateFace(int xDimen, int yDimen, float sideLength, float spacing)
    {
        int coordsPerSquare = 6;
        float[] result = new float[xDimen * yDimen * coordsPerSquare];



        return result;
    }

    public static float[] generateSquare(float sideLength)
    {
        float[] result = new float[0];

        return result;
    }


    public Square(float[] altCoords)
    {
        if (altCoords.length % COORDS_PER_VERTEX == 0)
        {
            coords = new float[altCoords.length];
            System.arraycopy(altCoords, 0, coords, 0, coords.length);
        }
        else
        {
            coords = new float[] {
                    -0.5f, 0.5f, 0.0f,
                    -0.5f, -0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f,
                    -0.5f, 0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f,
                    0.5f, 0.5f, 0.0f
            };
        }

        init();
    }

    public Square()
    {
        drawOrder = new short[] {0, 1, 2, 0, 2, 3};

        coords = new float[] {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
        };

//        coords = new float[] {
//            -0.5f, 0f, -0.5f,
//            -0.5f, 0f, 0.5f,
//            0.5f, 0f, 0.5f,
//            -0.5f, 0f, -0.5f,
//            0.5f, 0f, 0.5f,
//            0.5f, 0f, -0.5f
//        };

        init();
    }

    private void init()
    {
        vertexCount = 6;
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

//        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
//        dlb.order(ByteOrder.nativeOrder());
//        drawListBuffer = dlb.asShortBuffer();
//        drawListBuffer.put(drawOrder);
//        drawListBuffer.position(0);

        color = new float[] {1f, 0f, 0f, 1.0f};
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
