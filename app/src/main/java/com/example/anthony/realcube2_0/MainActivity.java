package com.example.anthony.realcube2_0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    private NumberPicker npX;
    private NumberPicker npY;
    private NumberPicker npZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button3x3 = findViewById(R.id.button3x3);
        button3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GLActivity.class);
                //intent.putExtra("cubeDimenstions", "3x3x3");
                view.getContext().startActivity(intent);
            }
        });

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        npX = findViewById(R.id.npX);
        npY = findViewById(R.id.npY);
        npZ = findViewById(R.id.npZ);

        npX.setMinValue(1);
        npY.setMinValue(1);
        npZ.setMinValue(1);

        npX.setMaxValue(10);
        npY.setMaxValue(10);
        npZ.setMaxValue(10);

        npX.setValue(3);
        npY.setValue(3);
        npZ.setValue(3);

        Button customButton = findViewById(R.id.cubeCustom);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), GLActivity.class);
                int[] dimensions = new int[3];
                dimensions[0] = npX.getValue();
                dimensions[1] = npY.getValue();
                dimensions[2] = npZ.getValue();
                intent.putExtra("cubeDimensions", dimensions);
                view.getContext().startActivity(intent);
            }
        } );


    }
}
