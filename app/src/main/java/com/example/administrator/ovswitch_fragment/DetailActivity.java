package com.example.administrator.ovswitch_fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/3/22.
 */
public class DetailActivity extends Activity {

    /**
     * 在此Activity中我们将一个Fragment嵌入到
     * 该Activity中进行显示
     */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //横屏的处理
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                finish();
                return;
            }

            //竖屏的处理
            //此处的道理和TitleListFragment中对于横屏的
            //处理很相似,只是方式稍有差别.
            //1 生成设置好参数的DetailFragment
            //2 将此DetailFragment attach到当前的activity
            if (savedInstanceState == null) {
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(getIntent().getExtras());
                getFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, detailFragment)
                        .commit();
            }
        }
    }