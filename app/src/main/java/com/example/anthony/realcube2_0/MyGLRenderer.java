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
        GLES20.glClearColor(1f, 1f, 1f, 1f);
        shapes = new ArrayList<Shape>();
        //shapes.add(new Triangle());
        shapes.add(new Square());
        float[] squCoords = new float[] {
            -0.5f, 1.6f, 0f,
            -0.5f, 0.6f, 0f,
            0.5f, 0.6f, 0f,
            -0.5f, 1.6f, 0f,
            0.5f, 0.6f, 0f,
            0.5f, 1.6f, 0f
        };
        shapes.add(new Square(squCoords));
        //tri = new Triangle();
        squ = new Square();
        iter = shapes.iterator();
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

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMM(temp, 0, vPMatrix, 0, rotationMatrix, 0);

        //tri.draw(temp);
        //squ.draw(temp);

        for (Shape s : shapes)
        {
            s.draw(temp);
        }
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

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 1, 7);
    }

}
