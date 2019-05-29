package com.example.anthony.realcube2_0;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square extends Shape
{
    //private FloatBuffer vertexBuffer;
    private short[] drawList = {0, 1, 2, 0, 2, 3};
    private ShortBuffer drawListBuffer;

    public static int verticesPerSquare = 4;

    public static int coordsPerVertex = 3;

    /*
        Returns a float array that contains the coordinates for a whole face
        @param xDimen       the number of pieces along the "width" of the face.
        @param yDimen       the number of pieces along the "height" of the face.
        @param sideLength   the length of the sides of each piece's colored part.
        @param spacing      the orthogonal distance between adjacent piece's colored parts.
        @param side defines the plane and position of the face
        @param distance     if > 0, generates the face @distance away from the origin
                            else generates the face @xDimen * (@sideLength + @spacing) / 2 units away from the origin
     */
    public static float[] generateFace(int xDimen, int yDimen, float sideLength, float spacing, Cube3x3x3.Side side, float distance)
    {
        int numSquares = xDimen * yDimen;
        int numVertices = numSquares * verticesPerSquare;
        int totalNumFloats = numVertices * coordsPerVertex;

        ByteBuffer bb = ByteBuffer.allocateDirect(totalNumFloats * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer result = bb.asFloatBuffer();

        float[] temp = new float[totalNumFloats];

        float d = distance > 0 ? distance : xDimen * (sideLength + spacing) / 2f;
        float faceWidth = xDimen * (sideLength + spacing);
        float faceHeight = yDimen * (sideLength + spacing);

        float[] orientationMatrix;

        switch (side)
        {
            case Up:

                for (int x = 0; x < xDimen; ++x)
                {
                    for (int y = 0; y < yDimen; ++y)
                    {
                        float xLeft = -(faceWidth / 2) + (x * (sideLength + spacing)) + spacing / 2;
                        float xRight = xLeft + sideLength;
                        float zBack = -(faceHeight / 2) + (y * (sideLength + spacing)) + spacing / 2;
                        float zFront = zBack + sideLength;

                        float[] vertices = {
                            xLeft, d, zBack,
                            xLeft, d, zFront,
                            xRight, d, zFront,
                            xRight, d, zBack
                        };
                        System.arraycopy(vertices, 0, temp, (y * verticesPerSquare * 3) + (x * yDimen * verticesPerSquare * 3), vertices.length);
                    }
                }
                break;
            case Down:
                d = -d;
                for (int x = 0; x < xDimen; ++x)
                {
                    for (int y = 0; y < yDimen; ++y)
                    {
                        float xLeft = -(faceWidth / 2) + (x * (sideLength + spacing)) + spacing / 2;
                        float xRight = xLeft + sideLength;
                        float zFront = (faceHeight / 2) - (y * (sideLength + spacing)) - spacing / 2;
                        float zBack = zFront - sideLength;

                        float[] vertices = {
                                xLeft, d, zFront,
                                xLeft, d, zBack,
                                xRight, d, zBack,
                                xRight, d, zFront
                        };
                        System.arraycopy(vertices, 0, temp, (y * verticesPerSquare * 3) + (x * yDimen * verticesPerSquare * 3), vertices.length);
                    }
                }
                break;
            case Left:
                d = -d;
                for (int x = 0; x < xDimen; ++x)
                {
                    for (int y = 0; y < yDimen; ++y)
                    {
                        float zBack = -(faceWidth / 2) + (x * (sideLength + spacing)) + spacing / 2;
                        float zFront = zBack + sideLength;
                        float yTop = (faceHeight / 2) - (y * (sideLength + spacing)) - spacing / 2;
                        float yBottom = yTop - sideLength;

                        float[] vertices = {
                            d, yTop, zBack,
                            d, yBottom, zBack,
                            d, yBottom, zFront,
                            d, yTop, zFront
                        };
                        System.arraycopy(vertices, 0, temp, (y * verticesPerSquare * 3) + (x * yDimen * verticesPerSquare * 3), vertices.length);
                    }
                }
                break;
            case Right:
                for (int x = 0; x < xDimen; ++x)
                {
                    for (int y = 0; y < yDimen; ++y)
                    {
                        float zFront = (faceWidth / 2) - (x * (sideLength + spacing)) - spacing / 2;
                        float zBack = zFront - sideLength;
                        float yTop = (faceHeight / 2) - (y * (sideLength + spacing)) - spacing / 2;
                        float yBottom = yTop - sideLength;

                        float[] vertices = {
                                d, yTop, zFront,
                                d, yBottom, zFront,
                                d, yBottom, zBack,
                                d, yTop, zBack
                        };
                        System.arraycopy(vertices, 0, temp, (y * verticesPerSquare * 3) + (x * yDimen * verticesPerSquare * 3), vertices.length);
                    }
                }
                break;
            case Front:
                for (int x = 0; x < xDimen; ++x)
                {
                    for (int y = 0; y < yDimen; ++y)
                    {
                        float xLeft = -(faceWidth / 2) + (x * (sideLength + spacing)) + spacing / 2;
                        float xRight = xLeft + sideLength;
                        float yTop = (faceHeight / 2) - (y * (sideLength + spacing)) - spacing / 2;
                        float yBottom = yTop - sideLength;

                        float[] vertices = {
                                xLeft, yTop, d,
                                xLeft, yBottom, d,
                                xRight, yBottom, d,
                                xRight, yTop, d
                        };
                        System.arraycopy(vertices, 0, temp, (y * verticesPerSquare * 3) + (x * yDimen * verticesPerSquare * 3), vertices.length);
                    }
                }
                break;
            case Back:
                d = -d;
                for (int x = 0; x < xDimen; ++x)
                {
                    for (int y = 0; y < yDimen; ++y)
                    {
                        float xLeft = (faceWidth / 2) - (x * (sideLength + spacing)) - spacing / 2;
                        float xRight = xLeft - sideLength;
                        float yTop = (faceHeight / 2) - (y * (sideLength + spacing)) - spacing / 2;
                        float yBottom = yTop - sideLength;

                        float[] vertices = {
                                xLeft, yTop, d,
                                xLeft, yBottom, d,
                                xRight, yBottom, d,
                                xRight, yTop, d
                        };
                        System.arraycopy(vertices, 0, temp, (y * verticesPerSquare * 3) + (x * yDimen * verticesPerSquare * 3), vertices.length);
                    }
                }

                break;
            default:
                orientationMatrix = new float[16];
                Matrix.setIdentityM(orientationMatrix, 0);
                break;
        }


        return temp;
    }

    public Square(float[] altCoords, float[] aColor)
    {
        if (altCoords.length % COORDS_PER_VERTEX == 0)
            coords = altCoords;
        color = aColor;
        init();
    }

    public Square(float[] altCoords)
    {
        if (altCoords.length % (COORDS_PER_VERTEX) == 0)
        {
            coords = altCoords;
        }
        else
        {
            coords = new float[] {
                    -0.5f, 0.5f, 0.0f,
                    -0.5f, -0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f,
                    0.5f, 0.5f, 0.0f
            };
        }

        color = new float[] {1f, 0f, 0f, 1.0f};
        init();
    }

    public Square()
    {
        drawOrder = new short[] {0, 1, 2, 0, 2, 3};

        coords = new float[] {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
        };

        vertexStride = COORDS_PER_VERTEX * 4;

        color = new float[] {1f, 0f, 0f, 1.0f};
        init();
    }

    private void init()
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawList.length * 2);
        dlb.order(ByteOrder.nativeOrder());

        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawList);
        drawListBuffer.position(0);

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
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawList.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
