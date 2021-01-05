package com.example.isszym.canvasdrawtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    View lastView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner=(Spinner)findViewById(R.id.spinner1);
        final int ids[]={R.id.clip_view,R.id.color_filter_view,
                R.id.draw_text,R.id.line_end_view,R.id.mask_filter_view,R.id.matrix_view,R.id.path_effect_view,
                R.id.path_effect_view2,R.id.path_view,R.id.path_op_view,R.id.porterduff_view,R.id.porterduff_xfer_view,
                R.id.save_layer_view,R.id.shader_view,R.id.shadow_view,R.id.shape_view};
        String views[]={"clip_view","color_filter_view",
                "draw_text","line_end_view","mask_filter_view","matrix_view","path_effect_view",
                "path_effect_view2","path_view","path_op_view","porterduff_view","porterduff_xfer_view",
                "save_layer_view","shader_view","shadow_view","shape_view"};
        ArrayList<String> list=new ArrayList<String>();
        for(String view:views){
            list.add(view);
        }
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(lastView!=null)lastView.setVisibility(View.GONE);
                View vw=findViewById(ids[position]);
                vw.setVisibility(View.VISIBLE);
                lastView=vw;
//                Toast toast=Toast.makeText(MainActivity.this,"选择第" + (position + 1) + "项",
//                        Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,360);
//                toast.show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
