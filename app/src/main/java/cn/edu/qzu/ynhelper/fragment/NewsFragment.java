package cn.edu.qzu.ynhelper.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.adapter.FragmentPagerAdapter;
import cn.edu.qzu.ynhelper.fragment.news.NewsListFragment;


public class NewsFragment extends Fragment {

    private String[] tabs = {"要闻","动态","食品安全","政策"};

    public static String FRAGMENT_TAG  = "tag";


    public TabLayout tabLayout;
    public ViewPager viewPager;

    public FragmentPagerAdapter mAdapter;

    public List<Fragment> fragments;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_news,container,false);

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        //tabLayout.setTabTextColors(Color.WHITE, Color.GRAY);//设置文本在选中和为选中时候的颜色
        for(int i = 0;i < tabs.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]), false);//添加 Tab
        }
        tabLayout.getTabAt(0).select();
        initFragments();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager(),fragments,tabs);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);
        //用来设置tab的，同时也要覆写  PagerAdapter 的 CharSequence getPageTitle(int position) 方法，要不然 Tab 没有 title
        tabLayout.setupWithViewPager(viewPager);
        //关联 TabLayout viewpager
        tabLayout.setTabsFromPagerAdapter(mAdapter);

        return view;
    }

    public void initFragments(){
        fragments = new ArrayList<Fragment>();
        Bundle tag ;
        Fragment fragment;
        for(int i =0;i < tabs.length;i++){
            tag = new Bundle();
            tag.putInt(FRAGMENT_TAG,i+1);
            fragment = new NewsListFragment();
            fragment.setArguments(tag);
            fragments.add(fragment);
        }
    }

    public void onAttach(Context context) {
        super.onDetach();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
