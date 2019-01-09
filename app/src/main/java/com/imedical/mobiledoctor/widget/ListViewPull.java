/**
 * @file ListViewPull.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.imedical.mobiledoctor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

public class ListViewPull extends ListView implements OnScrollListener {

	private Scroller mScroller; // used for scroll back
	private OnScrollListener mScrollListener; // user's scroll listener

	// the interface to trigger refresh and load more.
	private IXListViewListener mListViewListener;

	// -- footer view
	private XListViewFooter mFooterView;
	
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
													// feature.
    private int mVisibleItemCount = 0 ;
	private boolean haveMore;
	/**
	 * @param context
	 */
	public ListViewPull(Context context) {
		super(context);
		initWithContext(context);
	}

	public ListViewPull(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public ListViewPull(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// ListViewPull need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		super.setOnScrollListener(this);
		// init header view
		mFooterView = new XListViewFooter(context);
		// init header height
		// make sure XListViewFooter is the last footer view, and only add once.
		addFooterView(mFooterView);
		setFooterDividersEnabled(false);
	}


	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
	}
	
//	@Override
//	public void computeScroll() {
//		if (mScroller.computeScrollOffset()) {
//			if (mScrollBack == SCROLLBACK_HEADER) {
//				mHeaderView.setVisiableHeight(mScroller.getCurrY());
//			} else {
//				mFooterView.setBottomMargin(mScroller.getCurrY());
//			}
//			postInvalidate();
//			invokeOnScrolling();
//		}
//		super.computeScroll();
//	}
//
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
		// send to user's listener
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,totalItemCount);
		}
		
		mVisibleItemCount = visibleItemCount + firstVisibleItem-2;
	}

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
		mFooterView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListViewListener.onClickLoadMore();
			}
		});
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IXListViewListener {
		public void onRefresh();

		public void onClickLoadMore();
	}
	
	public void startLoading(){
		mFooterView.starLoading();
	}
	public void endLoad(boolean haveMore){
		this.haveMore = haveMore;
		mFooterView.stopLoading(haveMore);
		//setSelection(mVisibleItemCount);
	}

	public boolean isThereMore(){
		return haveMore; 
	}

	public void resetStatu() {
		haveMore = true ;
	}

}
