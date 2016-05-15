package com.example.administrator.ovswitch_fragment;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/3/22.
 */
public class TitleListFragment extends ListFragment {
    //当前是否为横屏
    private boolean currentIsLand;
    //当前选中位置
    private int currentCheckedPosition = -1;
    private final String currentCheckedKey="currentChecked";

    //每次重绘Fragment时都会调用该onActivityCreated()方法
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("---> onActivityCreated");
        //设置适配器
        ArrayAdapter<String> arrayAdapter=
                new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_checked, Data.TITLES);
        setListAdapter(arrayAdapter);

        //判断当前是否为横屏
        //注意方式:
        //先利用getActivity()找到该Fragment隶属于的Activity,再findViewById()
        //为什么要这么判断是否是横屏呢?
        //因为横屏的时候会去读取layout-land下的main.xml布局文件
        //在该布局文件下有一个RelativeLayout,其id为containerRelativeLayout
        //所以,若能找到它那么就可以表明当前是横屏
        View containerView = getActivity().findViewById(R.id.containerRelativeLayout);
        currentIsLand = containerView != null && containerView.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            currentCheckedPosition = savedInstanceState.getInt(currentCheckedKey, 0);
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setItemChecked(currentCheckedPosition, true);
            System.out.println("---> onActivityCreated() currentCheckedPosition="+currentCheckedPosition);
        }else{
            System.out.println("---> onActivityCreated() savedInstanceState == null ");
        }

        if (currentIsLand) {
            //设置ListView为单选模式
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetailContent(currentCheckedPosition);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("---> onStart");
    }

    //处理手机一直在竖屏时:
    //点击标题,跳转后,再按下back键的情况
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("---> onResume");
        if (!currentIsLand) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //currentCheckedPosition默认为-1
            //所以在第一次时不选择任何一个
            if (currentCheckedPosition>=0) {
                getListView().setItemChecked(currentCheckedPosition, true);
            }
        }
    }

    //保存数据
    //Fragment被系统或者某些内存清理,或者横竖屏切换而被销毁时
    //将会触发onSaveInstanceState(Bundle savedInstanceState):
    //Called to ask the fragment to save its current dynamic state,
    //so it can later be reconstructed in a new instance of its process is restarted.
    //然后调用onActivityCreated()生命周期方法,重绘Fragment

    //存在的疑问:
    //在手机一直竖屏的情况下,点击TitleListFragment的条目
    //为什么会执行该onSaveInstanceState()方法和MainActivity的
    //onSaveInstanceState()方法
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(currentCheckedKey, currentCheckedPosition);
        System.out.println("---> onSaveInstanceState()");
        System.out.println("---> onSaveInstanceState() currentCheckedPosition="+currentCheckedPosition);
    }


    //点击ListFragment上的某个条目时调用
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetailContent(position);
    }

    //显示详细内容
    private void showDetailContent(int index) {
        //保存当前被选中的位置
        currentCheckedPosition=index;
        //横屏时
        if (currentIsLand) {
            //1  先设置TitleListFragment
            //  改变ListFragment的index位置被选中
            getListView().setItemChecked(index, true);

            //2  再设置DetailFragment
            //  利用FragmentManager判断在某个容器(container)中是否有一个DetailFragment
            //  如果没有或在有的情况下其CheckedIndex不等于当前被选中的index
            //  则进行replace()操作
            DetailFragment detailFragment =
                    (DetailFragment) getFragmentManager().findFragmentById(R.id.containerRelativeLayout);
            if (detailFragment == null || detailFragment.getCheckedIndex() != index) {
                //生成新的Fragment.
                detailFragment = DetailFragment.newInstance(index);
                //开始Fragment的事务Transaction
                //因为在这个事务中执行了replace()操作,所以会将该
                //detailFragment attach到activity,按照生命周期
                //的顺序必然会调用DetailFragment的onCreateView().
                //更加白话地说:其实相当于我们先做了一些内在的东西,再做了外在的东西.
                //什么意思呢?
                //内在的部分:我们先new了一个DetailFragment,并且为这个DetailFragment
                //设置了一些参数.
                //这些都体现在了DetailFragment.newInstance(index)方法中
                //外在的部分:利用replace()将该DetailFragment attach到activity
                //这些都体现在了DetailFragment的onCreateView()方法中.所以在
                //该方法中我们可以去取为此DetailFragment设置的参数.
                //即方法getCheckedIndex()
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                //替换容器(container)原来的Fragment
                fragmentTransaction.replace(R.id.containerRelativeLayout, detailFragment);
                //设置转换效果
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                //提交事务
                fragmentTransaction.commit();
            }
            //竖屏时
        } else {
            Intent intent = new Intent(getActivity(),DetailActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

}