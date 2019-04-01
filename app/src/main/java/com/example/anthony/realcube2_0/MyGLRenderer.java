package com.example.anthony.realcube2_0;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Iterator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer
{
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private float[] rotationVector = new float[4];

    private float eyeZ;

    private int[] dimensions;

    private float[] rvsRotationMatrix = new float[16];

    private Sensor rvs;
    private SensorManager sm;
    private Context context;
    private volatile float mAngle;
    private Iterator<Shape> iter;
    private int vertexCount;
    private float sideLength;
    private float spacing;
    private float angle;
    private float[] rotationMatrix = new float[16];

    public MyGLRenderer(Context c)
    {
        context = c;
        dimensions = new int[] {3, 3, 3};
        eyeZ = -3f;
        sideLength = 0.5f;
        spacing = 0.1f;
    }

    public MyGLRenderer(Context c, int[] dimens)
    {
        context = c;
        dimensions = dimens;
        int max = Math.max(dimensions[0], Math.max(dimensions[1], dimensions[2]));
        sideLength = 0.5f;
        spacing = 0.1f;
        eyeZ = -max * (sideLength + spacing) * 1.5f;
    }

    public void setRvsRotationMatrix(float[] rvsRotationMatrix)
    {
        this.rvsRotationMatrix = rvsRotationMatrix;
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float mAngle) {
        this.mAngle = mAngle;
    }

    private int vPMatrixHandle;
    private int positionHandle;
    private int colorHandle;
    private int mProgram;
    private int vertexStride;
    private FloatBuffer vertexBuffer;
    private float[][] colors;
    private TwistyPuzzle puzzle;
    private boolean isActive;

    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void setRotationVector(float[] rotationVector) {
        System.arraycopy(rotationVector, 0, this.rotationVector, 0, this.rotationVector.length);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        angle = 0;
        isActive = false;
        Matrix.setIdentityM(rotationMatrix, 0);

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

        int x = dimensions[0];
        int y = dimensions[1];
        int z = dimensions[2];
        sideLength = 0.5f;
        spacing = 0.1f;
        //vertexCount = x * y * z * 6;

        float alpha = 1f;
        float[] white = {0xff / 256f, 0xff / 256f, 0xff / 256f, alpha};    //up
        float[] yellow = {0xff / 256f, 0xd5 / 256f, 0x00 / 256f, alpha};   //down
        float[] red = {0xc4 / 256f, 0x1e / 256f, 0x3a / 256f, alpha};      //left
        float[] orange = {0xff / 256f, 0x58 / 256f, 0x00 / 256f, alpha};   //right
        float[] blue = {0x00 / 256f, 0x51 / 256f, 0xba / 256f, alpha};     //front
        float[] green = {0x00 / 256f, 0x9e / 256f, 0x60 / 256f, alpha};    //back

        colors = new float[][] {white, yellow, red, orange, blue, green};

        puzzle = new Cuboid(x, y, z, sideLength, spacing, colors);
        vertexCount = puzzle.getNumCoords() / Square.COORDS_PER_VERTEX;
        float[] coords = puzzle.getCoords();

        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        //GLES20.glCullFace(GL_BACK);
    }

    public void setIsActive(boolean b)
    {
        isActive = b;
    }

    @Override
    public void onDrawFrame(GL10 unused)
    {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        float[] temp = new float[16];

        if (isActive)
        {
            if (angle >= 360)
                angle = 0;
            angle += 1;
        }

        Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, 1f);
//        Matrix.setRotateM(rotationMatrix, 0, angle, rotationVector[0], rotationVector[1], rotationVector[2]);
//        Matrix.rotateM(rotationMatrix, 0, angle, 0, 0, -1f);

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, eyeZ, 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMM(rotationMatrix, 0, vPMatrix, 0, rotationMatrix, 0);
        Matrix.multiplyMM(temp, 0, rotationMatrix, 0, rvsRotationMatrix, 0);

        puzzle.draw(temp);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 1, 20);
    }

}
