package cn.edu.qzu.ynhelper.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.event.DiseaseSearchEvent;
import cn.edu.qzu.ynhelper.fragment.disease.DiseaseListFragment;

public class DiseaseFragment extends Fragment {

    private String []tabs = {"植物","动物"};
    public static String FRAGMENT_TAG  = "tag";
    public final static int PLANT = 0;
    public final static int ANIMAL = 1;

    TextView tv;
    public TabLayout tabLayout;
    public ViewPager viewPager;

    public FragmentPagerAdapter mAdapter;

    public List<Fragment> fragments;

    public DiseaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_disease, container, false);
        tv = (TextView) rootView.findViewById(R.id.tv);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.GRAY);//设置文本在选中和为选中时候的颜色
        for(int i = 0;i < tabs.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]), false);//添加 Tab
        }
        tabLayout.getTabAt(0).select();
        initFragments();
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(mAdapter);
        //用来设置tab的，同时也要覆写  PagerAdapter 的 CharSequence getPageTitle(int position) 方法，要不然 Tab 没有 title
        tabLayout.setupWithViewPager(viewPager);
        //关联 TabLayout viewpager
        tabLayout.setTabsFromPagerAdapter(mAdapter);
        return rootView;
    }

    private void initFragments(){
        fragments = new ArrayList<>();
        Bundle tag ;
        Fragment fragment;
        for(int i =0;i < tabs.length;i++){
            tag = new Bundle();
            tag.putInt(FRAGMENT_TAG,i);
            fragment = new DiseaseListFragment();
            fragment.setArguments(tag);
            fragments.add(fragment);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Subscribe
    public void onEventMainThread(DiseaseSearchEvent event){
        tv.setText(event.getKeyword());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{

        private List<Fragment> mFragments;

        public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments=fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
