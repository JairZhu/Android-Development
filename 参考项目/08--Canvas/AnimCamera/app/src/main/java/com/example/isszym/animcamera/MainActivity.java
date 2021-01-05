package com.example.isszym.animcamera;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Camera mCamera;
    private ImageView imgView;
    private SeekBar sb_x, sb_y, sb_z;
    private EditText et_x, et_y, et_z;
    private TextView tv_rotatex, tv_rotatey, tv_rotatez;

    private float rotateX, rotateY, rotateZ;
    private float translateX, translateY, translateZ;

    private Button btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCamera = new Camera();
        initViews();
        registerListeners();

    }

    private void initViews() {
        sb_x = (SeekBar) findViewById(R.id.sb_rotatex);
        sb_x.setMax(100);
        sb_y = (SeekBar) findViewById(R.id.sb_rotatey);
        sb_y.setMax(100);
        sb_z = (SeekBar) findViewById(R.id.sb_rotatez);
        sb_z.setMax(100);

        et_x = (EditText) findViewById(R.id.et_x);
        et_y = (EditText) findViewById(R.id.et_y);
        et_z = (EditText) findViewById(R.id.et_z);

        tv_rotatex = (TextView) findViewById(R.id.tv_rotatex);
        tv_rotatey = (TextView) findViewById(R.id.tv_rotatey);
        tv_rotatez = (TextView) findViewById(R.id.tv_rotatez);

        btn_set = (Button) findViewById(R.id.btn_set);
        imgView = (ImageView) findViewById(R.id.imgView);
    }

    private void registerListeners() {
        sb_x.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sb_y.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sb_z.setOnSeekBarChangeListener(onSeekBarChangeListener);

        btn_set.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            translateX = getFloat(et_x.getText().toString());
            translateY = getFloat(et_y.getText().toString());
            translateZ = getFloat(et_z.getText().toString());

            refreshImage();
        }
    };

    private float getFloat(String txt) {
        try {
            if ("".equals(txt) || txt == null) {
                return 0;
            } else {
                return Float.parseFloat(txt);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar == sb_x) {
                tv_rotatex.setText("rotateX " + progress + "");
                rotateX = progress * 360f / 100f;
            } else if (seekBar == sb_y) {
                tv_rotatey.setText("rotateY " + progress + "");
                rotateY = progress * 360f / 100f;
            } else if (seekBar == sb_z) {
                tv_rotatez.setText("rotateZ " + progress + "");
                rotateZ = progress * 360f / 100f;
            }
            refreshImage();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void refreshImage() {
        Matrix matrix = new Matrix();

        mCamera.save();
        mCamera.rotateX(rotateX);
        mCamera.rotateY(rotateY);
        mCamera.rotateZ(rotateZ);
        mCamera.translate(translateX, translateY, translateZ);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.sprite);
        Bitmap srcBitmap = bitmapDrawable.getBitmap();
        Bitmap dstBitmap = null;
        try {
            // 经过矩阵转换后的图像宽高有可能不大于0，此时会抛出IllegalArgumentException
            dstBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        if (dstBitmap != null) {
            imgView.setImageBitmap(dstBitmap);
        }
    }

}
