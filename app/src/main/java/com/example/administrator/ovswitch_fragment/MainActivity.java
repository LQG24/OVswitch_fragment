package com.example.administrator.ovswitch_fragment;

import android.app.Activity;
import android.os.Bundle;
/**
 * Demo描述:
 * Fragment使用示例
 *
 * 备注说明:
 * 1 在main.xml中存在一个Fragment即
 *   TitleListFragment.用它来显示Titles
 * 2 点击TitleListFragment中的某个条目的时候
 *   判断的横竖屏
 *   2.1 若是横屏,则用另外一个Fragment显示详细信息
 *   2.2 若是竖屏,则启动另一个Activity显示详细信息
 *       2.2.1 在该Activity中动态添加一个Fragment显示详细信息
 *
 * 参考文档:
 * 1 http://blog.csdn.net/t12x3456/article/details/8120309
 * 2 http://blog.csdn.net/nkmnkm/article/category/958669/5
 * 3 http://blog.csdn.net/xiaanming/article/details/9254749
 *   Thank you very much
 *
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("---> MainActivity onSaveInstanceState()");
    }
}
