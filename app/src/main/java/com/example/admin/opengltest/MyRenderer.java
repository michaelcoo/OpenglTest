package com.example.admin.opengltest;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by admin on 2016/08/26   .
 */

public class MyRenderer implements GLSurfaceView.Renderer {
    public static final String TAG = "michael";

    float[] triangleData = new float[]{ //三角形顶点数据
            0.1f, 0.6f, 0.0f,
            -0.3f, 0.0f, 0.0f,
            0.3f, 0.1f, 0.0f
    };
    int[] triangleColor = new int[]{ //三角形颜色数据
            65535, 0, 0, 0,
            0, 65535, 0, 0,
            0, 0, 65535, 0
    };
    float[] cubeVertices= new float[]{
            //上面四个点
            0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            //下面四个点
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f
    };
    private byte[] cubeFacets = new byte[]{
            0, 1, 2,
            0, 2, 3,
            2, 3, 7,
            2, 6, 7,
            0, 3, 7,
            0, 4, 7,
            4, 5, 6,
            4, 6, 7,
            0, 1, 4,
            1, 4, 5,
            1, 2, 6,
            1, 5, 6
    };
    private float color[] ={
            0.3f, 0.4f, 0.1f, 0.0f,
            0.3f, 0.7f, 0.2f, 0.0f,
            0.2f, 0.1f, 0.1f, 0.0f,
            0.5f, 0.9f, 0.2f, 0.0f,
            0.7f, 0.4f, 0.4f, 0.0f,
            0.4f, 0.4f, 0.1f, 0.0f,
            0.1f, 0.5f, 0.8f, 0.0f,
            0.8f, 0.2f, 0.2f, 0.0f
    };

//    FloatBuffer triangleDataBuffer; //三角形顶点buffer
//    IntBuffer triangleColorBuffer; // 三角形颜色buffer
    FloatBuffer cubeVerticesBuffer; //正方体
    ByteBuffer cubeFacetsBuffer;
    FloatBuffer floatColorBuffer;
    private float rotate;

    public MyRenderer(){
//        triangleDataBuffer = floatBufferUtil(triangleData);
//        triangleColorBuffer = intBufferUtil(triangleColor);
        cubeVerticesBuffer = floatBufferUtil(cubeVertices);
        cubeFacetsBuffer = ByteBuffer.wrap(cubeFacets);
        floatColorBuffer = floatBufferUtil(color);
    }



    private IntBuffer intBufferUtil(int[] arr){
        IntBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    private FloatBuffer floatBufferUtil(float[] arr){
        FloatBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        gl.glClearColor(0,0,0,0);//清屏
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);
        float ratio = (float)width/height;
        Log.d(TAG," ratio == "+ ratio);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glFrustumf(-ratio,ratio,-1,1,1,10);//设置透视窗口空间大小
        gl.glLoadIdentity(); //必须写在最后，不然图形显示不出来
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY); //如果不用颜色那就不要enable不然编译会报错
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        //画旋转的三角形
//        gl.glTranslatef(-0.32f, 0.35f, -1f); //移动绘图中心点
//        gl.glRotatef(rotate, 0f, 0.2f, 0f);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDataBuffer);
//        gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
////        gl.glColor4f(0.5f, 0.5f, 0.2f, 0.0f);//设置填充颜色
//        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        //画旋转正方体
        gl.glRotatef(rotate, 1.0f, 0f, 0f);
        gl.glRotatef(rotate, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVerticesBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, floatColorBuffer);//设置顶点的颜色数据
        gl.glDrawElements(GL10.GL_TRIANGLES, cubeFacetsBuffer.remaining(), GL10.GL_UNSIGNED_BYTE, cubeFacetsBuffer);

        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        rotate+=1;

    }
}
