package com.lauren.simplenews.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lauren.simplenews.R;
import com.lauren.simplenews.adapter.MyPagerAdapter;
import com.lauren.simplenews.fragment.BaseFragment;

public class NewsFragment extends BaseFragment {

    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_RECREATION = 1;
    public static final int NEWS_TYPE_SCIENCE = 2;
    public static final int NEWS_TYPE_HEALTH = 3;

    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);
        setupViewPager(mViewPager);
        mTablayout.addTab(mTablayout.newTab().setText(R.string.top));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.recreation));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.science));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.health));
        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP), getString(R.string.top));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_RECREATION), getString(R.string.recreation));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_SCIENCE), getString(R.string.science));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_HEALTH), getString(R.string.health));
        mViewPager.setAdapter(adapter);
    }


}
