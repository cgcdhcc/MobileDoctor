package com.imedical.mobiledoctor.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.imedical.mobiledoctor.interfaces.CallbackItemTouch;


/**
 * Created by Alessandro on 12/01/2016.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    CallbackItemTouch callbackItemTouch; // interface
    View view=null;

    public MyItemTouchHelperCallback(CallbackItemTouch callbackItemTouch){
        this.callbackItemTouch = callbackItemTouch;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false; // swiped disabled
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT; // movements drag
        return makeFlag( ItemTouchHelper.ACTION_STATE_DRAG , dragFlags); // as parameter, action drag and flags drag
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        callbackItemTouch.itemTouchOnMove(viewHolder.getAdapterPosition(),target.getAdapterPosition()); // information to the interface
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // swiped disabled
    }
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.0f, 1.4f);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.0f, 1.4f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(300);
            animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
            animatorSet.start();
            view = viewHolder.itemView;
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_IDLE){
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.4f, 1.0f);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.4f, 1.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(300);
            animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
            animatorSet.start();
        }
    }
}
