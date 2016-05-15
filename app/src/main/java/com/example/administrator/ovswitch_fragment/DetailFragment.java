package com.example.administrator.ovswitch_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/22.
 */
public class DetailFragment extends Fragment{
    //往ScrollView中添加一个TextView然后将其返回
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        ScrollView scrollView = new ScrollView(getActivity());
        TextView textView = new TextView(getActivity());
        textView.setPadding(10, 10, 10, 10);
        scrollView.addView(textView);
        textView.setText(Data.DETAILS[getCheckedIndex()]);
        return scrollView;
    }

    //生成DetailFragment实例,并且保存index
    public static DetailFragment newInstance(int index) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    //取出index
    public int getCheckedIndex() {
        int index =getArguments().getInt("index", 0);
        return  index;
    }
}