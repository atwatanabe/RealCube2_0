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
    private ShortBuffer drawListBuffer;

    static float[] squareCoords = {
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f,  0.5f, 0.0f
    };
    public static int verticesPerSquare = 6;

    public static int coordsPerVertex = 3;

    /*
        Returns a FloatBuffer that contains the coordinates for a whole face
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
//        d *= -1;
        float faceWidth = xDimen * (sideLength + spacing);
        float faceHeight = yDimen * (sideLength + spacing);

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
                        xLeft, yTop, d,
                        xRight, yBottom, d,
                        xRight, yTop, d
                };
                System.arraycopy(vertices, 0, temp, (y * verticesPerSquare * 3) + (x * yDimen * verticesPerSquare * 3), vertices.length);
            }
        }

        float[] orientationMatrix;

        switch (side)
        {
            case Up:

                break;
            case Down:

                break;
            case Left:

                break;
            case Right:

                break;
            case Front:

                break;
            case Back:

                break;
            default:
                orientationMatrix = new float[16];
                Matrix.setIdentityM(orientationMatrix, 0);
                break;
        }

        for (int i = 0; i < numSquares; i += verticesPerSquare * coordsPerVertex)
        {

        }

//        result.put(temp);
//        result.position(0);
//        return result;


        return temp;
    }

    public Square(float[] altCoords, float[] aColor)
    {
        this(altCoords);
        color = aColor;
    }

    public Square(float[] altCoords)
    {
        if (altCoords.length % (COORDS_PER_VERTEX) == 0)
        {
//            coords = new float[altCoords.length];
//            System.arraycopy(altCoords, 0, coords, 0, coords.length);
            coords = altCoords;
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

        color = new float[] {1f, 0f, 0f, 1.0f};
        verticesPerSquare = 6;
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

        vertexStride = COORDS_PER_VERTEX * 4;

        verticesPerSquare = 6;
        color = new float[] {1f, 0f, 0f, 1.0f};
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
