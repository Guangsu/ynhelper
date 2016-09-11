package cn.edu.qzu.ynhelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Jaren on 2016/7/7.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private String[] tabs;

    public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments,String[] tabs) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments=fragments;
        this.tabs = tabs;
    }

    @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
}
