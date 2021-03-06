/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description ListViewPull's footer
 */
package com.imedical.mobiledoctor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;


public class XListViewFooter extends LinearLayout {

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;

	private LinearLayout moreView;

	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void starLoading(){
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		mProgressBar.invalidate();
	}
	
	public void stopLoading(Boolean haveMore){
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		
		mHintView.setVisibility(View.VISIBLE);
		if(haveMore ==null){
			mHintView.setText("暂无数据！");
			mHintView.setClickable(false);
			return ;
		}
	  
		if(haveMore ==true){
			mHintView.setText("点击加载更多");
			mHintView.setClickable(true);
		}else if(haveMore ==false){
			mHintView.setText("数据加载完毕！");
			mHintView.setClickable(false);
		}
		
	}
	

	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
//	public void hide() {
//		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
//				.getLayoutParams();
//		lp.height = 0;
//		mContentView.setLayoutParams(lp);
//	}

	/**
	 * show footer
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	private void initView(Context context) {
		mContext = context;
		moreView = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView
				.findViewById(R.id.xlistview_footer_hint_textview);
		
	}
	
	public void setText(String text){
		mHintView.setText(text);
	}
}
