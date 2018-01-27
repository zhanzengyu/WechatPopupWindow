package com.nesger.wechatpopupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by GT on 2018/1/22.
 */

public class PopupWindowList {

    private Context mContext;
    private PopupWindow mPopupWindow;
    //the view where PopupWindow lie in
    private View mAnchorView;
    //ListView item data
    private List<String> mItemData;
    //the animation for PopupWindow
    private int mPopAnimStyle;
    //the PopupWindow width
    private int mPopupWindowWidth;
    //the PopupWindow height
    private int mPopupWindowHeight;
    private AdapterView.OnItemClickListener mItemClickListener;
    private boolean mModal;

    public PopupWindowList(Context mContext) {
        if (mContext == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        this.mContext = mContext;
        setHeightWidth();
    }

    public void setAnchorView(@Nullable View anchor) {
        mAnchorView = anchor;
    }

    public void setItemData(List<String> mItemData) {
        this.mItemData = mItemData;
    }

    public void setPopAnimStyle(int mPopAnimStyle) {
        this.mPopAnimStyle = mPopAnimStyle;
    }

    public void setPopupWindowWidth(int mPopupWindowWidth) {
        this.mPopupWindowWidth = mPopupWindowWidth;
    }

    public void setPopupWindowHeight(int mPopupWindowHeight) {
        this.mPopupWindowHeight = mPopupWindowHeight;
    }

    /**
     * Set whether this window should be modal when shown.
     *
     * <p>If a popup window is modal, it will receive all touch and key input.
     * If the user touches outside the popup window's content area the popup window
     * will be dismissed.
     *
     * @param modal {@code true} if the popup window should be modal, {@code false} otherwise.
     */
    public void setModal(boolean modal) {
        mModal = modal;
    }

    public boolean isShowing(){
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    public void hide(){
        if (isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * Sets a listener to receive events when a list item is clicked.
     *
     * @param clickListener Listener to register
     *
     * @see ListView#setOnItemClickListener(AdapterView.OnItemClickListener)
     */
    public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener clickListener) {
        mItemClickListener = clickListener;
    }

    public void show() {
        if (mAnchorView == null) {
            throw new IllegalArgumentException("PopupWindow show location view can  not be null");
        }
        if (mItemData == null) {
            throw new IllegalArgumentException("please fill ListView Data");
        }
        ListView popView = new ListView(mContext);
        popView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
        popView.setVerticalScrollBarEnabled(false);
        popView.setDivider(null);
        popView.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_list_item_1, mItemData));
        popView.setOnItemClickListener(mItemClickListener);
        popView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        if (mPopupWindowWidth == 0) {
            mPopupWindowWidth = mDeviceWidth / 3;
        }
        if (mPopupWindowHeight == 0) {
            mPopupWindowHeight = mItemData.size() * popView.getMeasuredHeight();
            if (mPopupWindowHeight > mDeviceHeight / 2) {
                mPopupWindowHeight = mDeviceHeight / 2;
            }
        }
        mPopupWindow = new PopupWindow(popView, mPopupWindowWidth, mPopupWindowHeight);
        if (mPopAnimStyle != 0) {
            mPopupWindow.setAnimationStyle(mPopAnimStyle);
        }
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(mModal);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), (Bitmap) null));


        Rect location = locateView(mAnchorView);
        if (location != null) {
            int x;
            int xMiddle = location.left + mAnchorView.getWidth() / 2;
            if (xMiddle > mDeviceWidth / 2) {
                //在右边
                x = location.left + mAnchorView.getWidth() / 2 - mPopupWindowWidth;
            } else {
                x = location.left + mAnchorView.getWidth() / 2;
            }
            int y;
            int yMiddle = location.top + mAnchorView.getHeight() / 2;
            if (yMiddle > mDeviceHeight / 2) {
                //在下方
                y = location.top - mPopupWindowHeight + mAnchorView.getHeight() / 2;
            } else {
                //在上方
                y = location.top + mAnchorView.getHeight() / 2;
            }
            mPopupWindow.showAtLocation(mAnchorView, Gravity.NO_GRAVITY, x, y);
        }
    }

    public Rect locateView(View v) {
        if (v == null) return null;
        int[] loc_int = new int[2];
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }


    private int mDeviceWidth, mDeviceHeight;

    public void setHeightWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //API 13才允许使用新方法
        Point outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
        if (outSize.x != 0) {
            mDeviceWidth = outSize.x;
        }
        if (outSize.y != 0) {
            mDeviceHeight = outSize.y;
        }
    }

}
