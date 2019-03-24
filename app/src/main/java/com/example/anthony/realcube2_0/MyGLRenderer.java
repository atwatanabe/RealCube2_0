package com.example.anthony.realcube2_0;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.Context.SENSOR_SERVICE;
import static android.opengl.GLES20.GL_BACK;

public class MyGLRenderer implements GLSurfaceView.Renderer
{
    private List<Shape> shapes;

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private float[] rotationMatrix = new float[16];

    private Sensor rvs;
    private SensorManager sm;
    private Context context;
    private volatile float mAngle;
    private Iterator<Shape> iter;
    private int vertexCount;

    public MyGLRenderer(Context c)
    {
        context = c;
    }

    public void setRotationMatrix(float[] rotationMatrix)
    {
        this.rotationMatrix = rotationMatrix;
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float mAngle) {
        this.mAngle = mAngle;
    }

    private Triangle tri;
    private Square squ;

    private int vPMatrixHandle;
    private int positionHandle;
    private int colorHandle;
    private int mProgram;
    private int vertexStride;
    private FloatBuffer vertexBuffer;
    private float[][] colors;
    private TwistyPuzzle puzzle;

    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {

        GLES20.glDepthRangef(0f, 1f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        GLES20.glClearDepthf(1.0f);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, Shape.vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, Shape.fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        vertexStride = Shape.COORDS_PER_VERTEX * 4;

        int x = 3;
        int y = 3;
        int z = 5;
        float sideLength = 0.3f;
        float spacing = 0.02f;
        //vertexCount = x * y * z * 6;

        float alpha = 1f;
        float[] white = {0xff / 256f, 0xff / 256f, 0xff / 256f, alpha};    //up
        float[] yellow = {0xff / 256f, 0xd5 / 256f, 0x00 / 256f, alpha};   //down
        float[] red = {0xc4 / 256f, 0x1e / 256f, 0x3a / 256f, alpha};      //left
        float[] orange = {0xff / 256f, 0x58 / 256f, 0x00 / 256f, alpha};   //right
        float[] blue = {0x00 / 256f, 0x51 / 256f, 0xba / 256f, alpha};     //front
        float[] green = {0x00 / 256f, 0x9e / 256f, 0x60 / 256f, alpha};    //back

        colors = new float[][] {white, yellow, red, orange, blue, green};

        puzzle = new Cuboid(x, y, z, 0.5f, 0.1f, colors);
        vertexCount = puzzle.getNumCoords() / Square.COORDS_PER_VERTEX;
        float[] coords = puzzle.getCoords();

//        float unit = sideLength + spacing;
//        float halfUnit = unit / 2f;
//        float widthRadius = x * halfUnit;
//        float heightRadius = y * halfUnit;
//        float depthRadius = z * halfUnit;
//
//        float[] coords0 = Square.generateFace(x, y, sideLength, spacing, Cube3x3x3.Side.Front, depthRadius);
//        float[] coords1 = Square.generateFace(x, y, sideLength, spacing, Cube3x3x3.Side.Back, depthRadius);
//        float[] coords2 = Square.generateFace(x, z, sideLength, spacing, Cube3x3x3.Side.Up, heightRadius);
//        float[] coords3 = Square.generateFace(z, y, sideLength, spacing, Cube3x3x3.Side.Left, widthRadius);
//        float[] coords4 = Square.generateFace(z, y, sideLength, spacing, Cube3x3x3.Side.Right, widthRadius);
//        float[] coords5 = Square.generateFace(x, z, sideLength, spacing, Cube3x3x3.Side.Down, heightRadius);
//
//        float[] coords = new float[coords0.length + coords1.length + coords2.length + coords3.length + coords4.length + coords5.length];
//        System.arraycopy(coords0, 0, coords, 0, coords0.length);
//        System.arraycopy(coords1, 0, coords, coords0.length, coords1.length);
//        System.arraycopy(coords2, 0, coords, coords0.length + coords1.length, coords2.length);
//        System.arraycopy(coords3, 0, coords, coords0.length + coords1.length + coords2.length, coords3.length);
//        System.arraycopy(coords4, 0, coords, coords0.length + coords1.length + coords2.length + coords3.length, coords4.length);
//        System.arraycopy(coords5, 0, coords, coords0.length + coords1.length + coords2.length + coords3.length + coords4.length, coords5.length);

//        vertexCount = coords.length / Square.COORDS_PER_VERTEX;

//        float[] coords = p1.getCoords();
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);


        //vertexBuffer = Square.generateFace(3, 3, 0.5f, 0.1f, Cube3x3x3.Side.Front, 0f);

        //GLES20.glCullFace(GL_BACK);
    }

    @Override
    public void onDrawFrame(GL10 unused)
    {
        float[] temp = new float[16];
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
//        Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1f);
        //Matrix.setRotateM(rotationMatrix, 0, -mAngle, 0, 0, -1f);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMM(temp, 0, vPMatrix, 0, rotationMatrix, 0);

        //tri.draw(temp);
        //squ.draw(temp);

        puzzle.draw(temp);
//        float[] color = {1f, 0f, 0f, 1f};
//        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, temp, 0);
//        GLES20.glUseProgram(mProgram);

//        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//        GLES20.glEnableVertexAttribArray(positionHandle);
//        GLES20.glVertexAttribPointer(positionHandle, Shape.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
//        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
//        GLES20.glUniform4fv(colorHandle, 1, color, 0);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
//        GLES20.glDisableVertexAttribArray(positionHandle);

//        for (Shape s : shapes)
//        {
//            s.draw(temp);
//        }


//        while (iter.hasNext())
//        {
//            iter.next().draw(temp);
//        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 1, 20);
    }

}
