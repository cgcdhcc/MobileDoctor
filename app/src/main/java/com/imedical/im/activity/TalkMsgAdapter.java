package com.imedical.im.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imedical.im.entity.AdmInfo;
import com.imedical.im.entity.DoctorTemplate;
import com.imedical.im.entity.MessageInfo;
import com.imedical.im.entity.PatTemplate;
import com.imedical.im.entity.UserFriend;
import com.imedical.im.entity.VoicePlayItem;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.DialogUtil;
import com.imedical.mobiledoctor.util.DownloadUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TalkMsgAdapter extends BaseAdapter {
	public TalkMsgActivity activity;
	public List<MessageInfo> data_list;
	VoicePlayItem vp = new VoicePlayItem();
	public Map<String, MediaPlayer> mapPlay = new HashMap<String, MediaPlayer>();
    public AdmInfo admInfo;
	public TalkMsgAdapter(TalkMsgActivity activity, List<MessageInfo> data_list,AdmInfo admInfo) {
		this.activity = activity;
		this.data_list = data_list;
		this.admInfo=admInfo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int p, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view = activity.getLayoutInflater().inflate(R.layout.im_item_talklist,
				null);
		final int n = p;
		View left = view.findViewById(R.id.view_left);
		View right = view.findViewById(R.id.view_right);
		TextView talk_time = (TextView) view.findViewById(R.id.talk_time);
		if(data_list.get(p).timeStamp!=null){
			talk_time.setText(DateUtil.ConvertDateTime(data_list.get(p).timeStamp));
			if(p>0&&data_list.get(p-1).timeStamp!=null){
				if(DateUtil.CountDistance(data_list.get(p-1).timeStamp,data_list.get(p).timeStamp)<5000){
					talk_time.setVisibility(View.GONE);
				}
			}
		}
		ImageView iv_pat_head=view.findViewById(R.id.iv_pat_head);
		if("女".equals(admInfo.patientSex)){
			iv_pat_head.setImageResource(R.drawable.pat_famale);
		}else if("男".equals(admInfo.patientSex)){
			iv_pat_head.setImageResource(R.drawable.pat_male);
		}else{
			iv_pat_head.setImageResource(R.drawable.icon_common_head);
		}
		ImageView iv_doctor_head=view.findViewById(R.id.iv_doctor_head);
		if(Validator.isBlank(admInfo.doctorPicUrl)){
			DownloadUtil.loadImage(iv_doctor_head, admInfo.doctorPicUrl, R.drawable.pat_male, R.drawable.pat_male, R.drawable.pat_male);
		}
		if (!data_list.get(p).fromUser.equals(admInfo.docMarkId)) {// 自己发言
			left.setVisibility(View.VISIBLE);
			right.setVisibility(View.GONE);
			TextView left_content = (TextView) view
					.findViewById(R.id.left_content);
			final ImageView left_iv_content = (ImageView) view
					.findViewById(R.id.left_iv_content);
			LinearLayout left_ll_template=view.findViewById(R.id.left_ll_template);
			ProgressBar left_pb_send=(ProgressBar)view.findViewById(R.id.left_pb_send);
			if(data_list.get(p).sended==0){
				left_pb_send.setVisibility(View.VISIBLE);
			}else{
				left_pb_send.setVisibility(View.GONE);
			}

			if (data_list.get(p).messageType.equals("img")) {
				left_content.setVisibility(View.GONE);
				left_ll_template.setVisibility(View.GONE);
				if(data_list.get(p).sended==0){
					DownloadUtil.loadImage(left_iv_content,"file:///"+data_list.get(p).thumbnailRemotePath,
							R.drawable.im_iconfont_tupian, R.drawable.im_iconfont_tupian,
							R.drawable.im_iconfont_tupian);
				}else{
					DownloadUtil.loadImage(left_iv_content,data_list.get(p).thumbnailRemotePath,
							R.drawable.im_iconfont_tupian, R.drawable.im_iconfont_tupian,
							R.drawable.im_iconfont_tupian);
				}
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						getImgs(n);
					}
				});
			} else if (data_list.get(p).messageType.equals("audio")) {
				left_content.setVisibility(View.GONE);
				left_ll_template.setVisibility(View.GONE);
				left_iv_content.setBackgroundResource(R.drawable.im_bg_blue_voice);
				Log.d("msg", "设置声音图标");
				left_iv_content.setImageResource(R.drawable.voice_left_p3);

				MediaPlayer mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
				try {
					mediaPlayer.setDataSource(data_list.get(p).fileRemotePath);
					mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

						@Override
						public void onPrepared(MediaPlayer mp) {
							// TODO Auto-generated method stub
							left_iv_content.setOnClickListener(CreateVoiceListener(
									"L",data_list.get(n).fileRemotePath,
									left_iv_content));
						}

					});
					mediaPlayer.prepareAsync();
					mapPlay.put(
							data_list.get(n).fileRemotePath,
							mediaPlayer);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if(("template").equals(data_list.get(p).messageType)){
				left_content.setVisibility(View.GONE);
				left_iv_content.setVisibility(View.GONE);
				left_ll_template.setVisibility(View.VISIBLE);
				if(1==data_list.get(p).templateId){
					View templateView=activity.getLayoutInflater().inflate(R.layout.im_item_patientinfo, null);
					TextView tv_patientName=templateView.findViewById(R.id.tv_patientName);
					TextView tv_patAge=templateView.findViewById(R.id.tv_patAge);
					final PatTemplate patTemplate=new Gson().fromJson(data_list.get(p).content, PatTemplate.class);
					tv_patientName.setText(patTemplate.patientName);
					tv_patAge.setText(patTemplate.patientSex+"   "+patTemplate.patientAge);
					templateView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							Intent intent=new Intent(activity,AdmInfoActivity.class);
							intent.putExtra("admId", patTemplate.admId);
							activity.startActivity(intent);
						}
					});
					View view_cancle=templateView.findViewById(R.id.view_cancle);
					if("2".equals(activity.admInfo.chatStatus)){
						view_cancle.setVisibility(View.GONE);
					}
					View tv_cancel=templateView.findViewById(R.id.tv_cancel);
					tv_cancel.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							DialogUtil.showAlertYesOrNo(activity, "操作确认", "确认进行退号操作吗？", new MyCallback<Boolean>() {
								@Override
								public void onCallback(Boolean value) {
									if(value){
										activity.cancleOrder();
									}
								}
							});
						}
					});
					left_ll_template.addView(templateView);
				}

			}else {
				left_content.setText(data_list.get(p).content);
				left_iv_content.setVisibility(View.GONE);
				left_ll_template.setVisibility(View.GONE);
			}

		} else {
			left.setVisibility(View.GONE);
			right.setVisibility(View.VISIBLE);
			TextView right_content = (TextView) view
					.findViewById(R.id.right_content);
			right_content.setText(data_list.get(p).content);
			final ImageView right_iv_content = (ImageView) view
					.findViewById(R.id.right_iv_content);
			LinearLayout right_ll_template=view.findViewById(R.id.right_ll_template);
			ProgressBar right_pb_send=(ProgressBar)view.findViewById(R.id.right_pb_send);
			if(data_list.get(p).sended==0){
				right_pb_send.setVisibility(View.VISIBLE);
			}else{
				right_pb_send.setVisibility(View.GONE);
			}
			if (data_list.get(p).messageType.equals("img")) {
				right_content.setVisibility(View.GONE);
				right_ll_template.setVisibility(View.GONE);
				if(data_list.get(p).sended==0){
					DownloadUtil.loadImage(right_iv_content,  "file:///"+data_list.get(p).thumbnailRemotePath,
							R.drawable.im_iconfont_tupian, R.drawable.im_iconfont_tupian,
							R.drawable.im_iconfont_tupian);
				}else {
					DownloadUtil.loadImage(right_iv_content,  data_list.get(p).thumbnailRemotePath,
							R.drawable.im_iconfont_tupian, R.drawable.im_iconfont_tupian,
							R.drawable.im_iconfont_tupian);
				}
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						getImgs(n);
					}
				});
			} else if (data_list.get(p).messageType.equals("audio")) {
				right_content.setVisibility(View.GONE);
				right_ll_template.setVisibility(View.GONE);
				right_iv_content
						.setBackgroundResource(R.drawable.im_bg_blue_voice);
				Log.d("msg", "设置声音图标");
				right_iv_content.setImageResource(R.drawable.voice_right_p3);

				MediaPlayer mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
				try {
					mediaPlayer.setDataSource( data_list.get(p).fileRemotePath);
					mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

						@Override
						public void onPrepared(MediaPlayer mp) {
							// TODO Auto-generated method stub
							right_iv_content.setOnClickListener(CreateVoiceListener(
									"R",data_list.get(n).fileRemotePath,
									right_iv_content));
						}

					});
					mediaPlayer.prepareAsync();
					mapPlay.put(
							data_list.get(n).fileRemotePath,
							mediaPlayer);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if(("template").equals(data_list.get(p).messageType)){
				right_content.setVisibility(View.GONE);
				right_iv_content.setVisibility(View.GONE);
				right_ll_template.setVisibility(View.VISIBLE);
				if(2==data_list.get(p).templateId){
					View templateView=activity.getLayoutInflater().inflate(R.layout.im_item_diagnosis, null);
					final DoctorTemplate doctorTemplate=new Gson().fromJson(data_list.get(p).content, DoctorTemplate.class);
					TextView tv_doctorName=templateView.findViewById(R.id.tv_doctorName);
					tv_doctorName.setText(doctorTemplate.doctorName);
					TextView tv_doctorTitle=templateView.findViewById(R.id.tv_doctorTitle);
					tv_doctorTitle.setText(doctorTemplate.doctorTitle);
					right_ll_template.addView(templateView);
					templateView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							Intent intent=new Intent(activity,AddDiagnosisActivity.class);
							intent.putExtra("admId", doctorTemplate.admId);
							activity.startActivity(intent);
						}
					});
				}

			}else {
				right_content.setText(data_list.get(p).content);
				right_iv_content.setVisibility(View.GONE);
				right_ll_template.setVisibility(View.GONE);
			}
		}

		return view;
	}



	// 语音消息加载和播放
	public OnClickListener CreateVoiceListener(final String type,
                                               final String url, final ImageView iv_showimg) {
		return new OnClickListener() {
			public void onClick(View paramView) {

				if (vp.mediaPlayer != null) {
					vp.mediaPlayer.pause();
					vp.mediaPlayer.seekTo(0);
					vp.animationDrawable.stop();
					if ("L".equals(vp.type)) {
						vp.iv_showimg
								.setImageResource(R.drawable.voice_left_p3);
					} else {
						vp.iv_showimg
								.setImageResource(R.drawable.voice_right_p3);
					}
				}
				if ("L".equals(type)) {
					iv_showimg.setImageResource(R.drawable.voice_play_left);
				} else {
					iv_showimg.setImageResource(R.drawable.voice_play_right);
				}
				final AnimationDrawable animationDrawable = (AnimationDrawable) iv_showimg
						.getDrawable();
				vp.animationDrawable = animationDrawable;
				vp.mediaPlayer = mapPlay.get(url);
				vp.iv_showimg = iv_showimg;
				vp.type = type;
				try {
					vp.mediaPlayer
							.setOnCompletionListener(new OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer arg0) {
									// TODO Auto-generated method stub
									animationDrawable.stop();
									if ("L".equals(type)) {
										iv_showimg
												.setImageResource(R.drawable.voice_left_p3);
									} else {
										iv_showimg
												.setImageResource(R.drawable.voice_right_p3);
									}
								}

							});
					vp.mediaPlayer.start();
					animationDrawable.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	public void getImgs(int n){
		Intent intent=new Intent(activity,TalkImageShowActivity.class);
		int imgnum=0;
		for(int i=0;i<data_list.size();i++){
              if("img".equals(data_list.get(i).messageType)){
				  imgnum++;
			  }
		}
		String[] imgs=new String[imgnum];
		imgnum=0;
		for(int i=0;i<data_list.size();i++){
			if("img".equals(data_list.get(i).messageType)){
				if(data_list.get(i).sended==0){
					imgs[imgnum]="file:///"+data_list.get(i).thumbnailRemotePath;
				}else {
					imgs[imgnum]=data_list.get(i).fileRemotePath;
				}
				if(i==n){
					intent.putExtra("position", imgnum);
				}
				imgnum++;
			}
		}
		intent.putExtra("imgurl",imgs);
		activity.startActivity(intent);
	}
	
}
