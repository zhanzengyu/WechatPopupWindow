package com.nesger.wechatpopupwindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void rightTop(View v){
        Log.e(TAG, "rightTop");
        showPopWindows(v);
    }

    public void middle(View v){
        Log.e(TAG, "middle");
        showPopWindows(v);
    }

    public void leftBottom(View v){
        Log.e(TAG, "leftBottom");
        showPopWindows(v);
    }

    private PopupWindowList mPopupWindowList;
    private void showPopWindows(View view){
        List<String> dataList = new ArrayList<>();
        for(int i = 0; i < 13; i++){
            dataList.add(String.valueOf(i));
        }
        if (mPopupWindowList == null){
            mPopupWindowList = new PopupWindowList(view.getContext());
        }
        mPopupWindowList.setAnchorView(view);
        mPopupWindowList.setItemData(dataList);
        mPopupWindowList.setModal(true);
        mPopupWindowList.show();
        mPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "click position="+position);
                mPopupWindowList.hide();
            }
        });
    }

}
