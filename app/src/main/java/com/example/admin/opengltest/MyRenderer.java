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
    FloatBuffer cubeVerticesBuffer; //正方体顶点buffer
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
        gl.glDisable(GL10.GL_DITHER); //关闭抗抖动
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置系统对透视进行修正
        gl.glClearColor(0,0,0,0);//清屏
        gl.glShadeModel(GL10.GL_SMOOTH);//设置阴影平滑模式
        gl.glEnable(GL10.GL_DEPTH_TEST);//启用深度测试
        gl.glDepthFunc(GL10.GL_LEQUAL);//设置深度测试类型
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);//设置视窗位置和高度
        float ratio = (float)width/height;//计算视窗宽高比
        Log.d(TAG," ratio == "+ ratio);
        gl.glMatrixMode(GL10.GL_PROJECTION);//将当前矩阵模式设为投影矩阵
        gl.glFrustumf(-ratio,ratio,-1,1,1,10);//设置透视窗口空间大小
        gl.glLoadIdentity(); //初始化单位矩阵，必须写在最后，不然图形显示不出来
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清楚屏幕缓存和深度缓存
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY); //启用顶点颜色数据，如果不用颜色那就不要enable不然编译会报错
        gl.glMatrixMode(GL10.GL_MODELVIEW);//设置当前矩阵模式为模型视图
        gl.glLoadIdentity();//重置当前的模型视图矩阵

        //画旋转的三角形
//        gl.glTranslatef(-0.32f, 0.35f, -1f); //移动绘图中心点
//        gl.glRotatef(rotate, 0f, 0.2f, 0f);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDataBuffer);
//        gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
////        gl.glColor4f(0.5f, 0.5f, 0.2f, 0.0f);//设置填充颜色
//        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        //画旋转正方体
        gl.glRotatef(rotate, 1.0f, 0f, 0f);//已rotate角度，沿着XYZ旋转，那个值大于0就绕着那个轴旋转，此处为围绕X轴旋转
        gl.glRotatef(rotate, 0.0f, 1.0f, 0.0f);//围绕Y轴旋转
        gl.glTranslatef(0.0f, 0.0f, 0.0f);//平移到0，0，0 这个点
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVerticesBuffer);//设置顶点位置数据
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, floatColorBuffer);//设置顶点的颜色数据
        gl.glDrawElements(GL10.GL_TRIANGLES, cubeFacetsBuffer.remaining(), GL10.GL_UNSIGNED_BYTE, cubeFacetsBuffer);//绘制立体图形

        gl.glFinish();//绘制结束
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        rotate+=1;

    }
}
