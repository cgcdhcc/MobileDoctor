package com.imedical.mobiledoctor.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liuende on 16/10/14.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration{
    private static final int[] attrs=new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    public MyItemDecoration(Context context) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs);
        mDivider=typedArray.getDrawable(0);
        typedArray.recycle();
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHDecoration(c,parent);
        drawVDecoration(c,parent);
    }
    /**
     * 绘制水平方向的分割线
     * @param c
     * @param parent
     */
    private void drawHDecoration(Canvas c, RecyclerView parent){
        int left_init=parent.getPaddingLeft();
        int right_init=parent.getWidth()-parent.getPaddingRight();
        int width=right_init-left_init;
        int childCount=parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child=parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams=(RecyclerView.LayoutParams)child.getLayoutParams();
            int left=left_init+mDivider.getIntrinsicWidth()*(i%4)+(width/4-2)*(i%4);
            int right=left+(width/4)+mDivider.getIntrinsicWidth();
            int top=child.getBottom()+layoutParams.bottomMargin;
            int bottom=top+mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }
    /**
     * 绘制垂直方向的分割线
     * @param c
     * @param parent
     */
    private void drawVDecoration(Canvas c, RecyclerView parent){
        int top_init=parent.getPaddingTop();
        int bottom_init=parent.getHeight()-parent.getPaddingBottom();
        int height=bottom_init-top_init;
        int childCount=parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child=parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams=(RecyclerView.LayoutParams)child.getLayoutParams();
            int top=top_init+mDivider.getIntrinsicHeight()*(i/4)+height/((childCount-1)/4+1)*(i/4);
            int bottom=top+height/((childCount-1)/4+1);
            int left=child.getRight()+layoutParams.rightMargin;
            int right=left+mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0,0,0);
        }
    }


