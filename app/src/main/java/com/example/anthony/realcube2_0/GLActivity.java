package com.example.anthony.realcube2_0;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GLActivity extends Activity {

    public MyGLSurfaceView getGlView() {
        return glView;
    }

    private MyGLSurfaceView glView;
    //private String cubeDimensions;

    //private List<UIButton> buttons;

    private int[] dimensions;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            int[] temp = b.getIntArray("cubeDimensions");
            dimensions = temp;
        }
        else
            dimensions = new int[] {3, 3, 3};

        glView = new MyGLSurfaceView(this);
//        setContentView(R.layout.default_ui);
        setContentView(glView);
//        addContentView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        glView.registerListener();

        Button testButton = new Button(this);
        testButton.setText("test button");
        testButton.setBackgroundColor(0x80ffffff);
        testButton.setTextColor(Color.WHITE);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("testButton", "test button clicked");
//            }
//        });

        testButton.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent e)
            {
                if (e.getAction() == MotionEvent.ACTION_DOWN)
                    glView.getRenderer().setIsActive(true);
                else if (e.getAction() == MotionEvent.ACTION_UP)
                    glView.getRenderer().setIsActive(false);

                return false;
            }
        });

        //View ui = findViewById(R.id.default_ui);
        addContentView(testButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //addContentView(ui, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void loadUISettings()
    {
        
    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        glView.registerListener();
//    }

    @Override
    public void onPause()
    {
        super.onPause();
        //glView.unregisterListener();
        //sm.unregisterListener(this);
    }

    class MyGLSurfaceView extends GLSurfaceView implements SensorEventListener
    {

        //private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        //private float previousX, previousY;
        private final MyGLRenderer renderer;
        private SensorManager sm;
        private Sensor rvs;

//        @Override
//        public boolean onTouchEvent(MotionEvent e)
//        {
//            float x = e.getX(), y = e.getY();
//
//            switch(e.getAction())
//            {
//                case MotionEvent.ACTION_MOVE:
//                    float dx = x - previousX, dy = y - previousY;
//                    if (y > getHeight() / 2)
//                    {
//                        dx = dx * -1;
//                    }
//                    if (x < getWidth() / 2)
//                    {
//                        dy = dy * -1;
//                    }
//
//                    //renderer.setAngle(renderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
//                    requestRender();
//            }
//            previousX = x;
//            previousY = y;
//
//            return true;
//        }

        public MyGLRenderer getRenderer()
        {
            return renderer;
        }

        public void unregisterListener()
        {
            sm.unregisterListener(this);
        }

        public void registerListener()
        {
            sm.registerListener(this, rvs, 10000);
        }

        public MyGLSurfaceView(Context context)
        {
            super(context);

            setEGLContextClientVersion(2);
            renderer = new MyGLRenderer(getContext(), dimensions);
            setRenderer(renderer);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            sm = (SensorManager) getSystemService(SENSOR_SERVICE);
            rvs = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            registerListener();
        }



        @Override
        public void onSensorChanged(SensorEvent e)
        {
            if (e.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
            {
                float[] rotationMatrix = new float[16];
                int index = 4;
                //Log.i("sensor vector size", new Integer(e.values.length).toString());
                //e.values[index] = -e.values[index];
                sm.getRotationMatrixFromVector(rotationMatrix, e.values);
                renderer.setRotationMatrix(rotationMatrix);
                requestRender();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i)
        {

        }

    }
}
