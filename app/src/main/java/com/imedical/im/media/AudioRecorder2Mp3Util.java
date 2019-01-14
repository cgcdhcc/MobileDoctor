package com.imedical.im.media;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;

/**
 * AMR格式录制<br/>
 * <p>
 * 权限要求<br/>
 * <p>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 * @author WangXu
 */
public class AudioRecorder2Mp3Util {
    //录音所保存的文件
    private File mAudioFile;
    /**
     * 文件代号
     */
    public static final int AMR = 0X00000001;

    /**
     * 文件路径
     */
    private String amrPath = null;

    /**
     * 采样频率
     */
    private static final int SAMPLE_RATE = 8000;

    /**
     * 录音需要的一些变量
     */
    private short[] mBuffer;
    private MediaRecorder mRecorder;

    /**
     * 构造方法
     */
    public AudioRecorder2Mp3Util() {
    }

    /**
     * 录音状态
     */
    private boolean isRecording = false;
    /**
     * 是否转换ok
     */
    private boolean convertOk = false;

    /**
     * @param amrPath
     */
    public AudioRecorder2Mp3Util(String amrPath) {
        this.amrPath = amrPath;
    }

    /**
     * 开始录音
     */
    public boolean startRecording() {
        // 如果正在录音，则返回
        if (isRecording) {
            return isRecording;
        }
        // 初始化
        if (mRecorder == null) {
            initRecorder();
        }
        mRecorder.start();
        isRecording = true;
        return isRecording;
    }

    /**
     * 停止录音，并且转换文件,<br/>
     * <b>这很可能是个耗时操作，建议在后台中做
     *
     * @return
     */
    public boolean stopRecordingAndConvertFile() {
        if (!isRecording) {
            return isRecording;
        }

        // 停止
        mRecorder.stop();
        isRecording = false;
        return isRecording;
    }
    /**
     * 关闭,可以先调用cleanFile来清理文件
     */
    public void close() {
        if (mRecorder != null){
            mRecorder.release();
            mRecorder=null;
        }
    }

    // -------内部的一些工具方法-------

    /**
     * 初始化
     */
    private void initRecorder() {
        mRecorder = new MediaRecorder();
        try{
            mAudioFile = new File(amrPath);
            if(!mAudioFile.exists()){
                boolean createfile=mAudioFile.createNewFile();
                if(createfile){
                    Log.d("msg","新建成功");
                }else{
                    Log.d("msg","新建失败");
                }
            }
        }catch (Exception e){

        }

        try {
            //配置mRecorder相应参数
            //从麦克风采集声音数据
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置保存文件格式为MP4
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            //设置采样频率,44100是所有安卓设备都支持的频率,频率越高，音质越好，当然文件越大
            mRecorder.setAudioSamplingRate(8000);
            //设置声音数据编码格式,音频通用格式是AAC
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //设置编码频率
            mRecorder.setAudioEncodingBitRate(64);
            // 设置录制的音频通道数
            mRecorder.setAudioChannels(1);
            //设置录音保存的文件
            mRecorder.setOutputFile(mAudioFile.getAbsolutePath());
            //开始录音
            mRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
