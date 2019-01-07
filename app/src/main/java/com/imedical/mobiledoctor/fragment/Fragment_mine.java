package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imedical.mobiledoctor.R;

public class Fragment_mine extends Fragment {
    private Activity ctx;
    private String mInfo=null;
    View mView=null;

    public void onAttach(Activity activity) {
        this.ctx = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, null);
        return mView;
    }
}
