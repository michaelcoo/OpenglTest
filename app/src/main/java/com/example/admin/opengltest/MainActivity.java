package com.example.admin.opengltest;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "michael";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glView = new GLSurfaceView(this);
        MyRenderer myRenderer = new MyRenderer();
        glView.setRenderer(myRenderer);
        setContentView(glView);
    }

}
