# WechatPopupWindow
高仿微信聊天界面长按弹框样式

## Run Result
![run result](https://github.com/nesger/WechatPopupWindow/blob/master/run_result.gif)

## Usage

1. 
```
implementation 'com.zengyu:popupwindowlist:1.0.0'
```
2. do as the follow sample example show.

```java

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

```

## NOTE
**setAnchorView() and setItemData() is must set, or will throw Exception.**

- setAnchorView() set the view which your popwindow show.
- setItemData() set the listview adapter data.
- setModal() means whether make popwindow focus.
- setOnItemClickListener() set item click listener for popwindow.
- setPopupWindowWidth() set PopupWindow width.
- setPopupWindowHeight() set PopupWindow height.
- setPopAnimStyle() set PopupWindow anim style.


## EXTRA
If you don't like the simple listview style, you can customize the [PopupWindowList.java](https://github.com/nesger/WechatPopupWindow/blob/master/app/src/main/java/com/nesger/wechatpopupwindow/PopupWindowList.java).

Any problem you can create issue for me.



![](https://github.com/nesger/WechatPopupWindow/blob/master/contact_me.png)
