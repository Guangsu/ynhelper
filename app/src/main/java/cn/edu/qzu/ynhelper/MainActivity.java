package cn.edu.qzu.ynhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.edu.qzu.ynhelper.entity.User;
import cn.edu.qzu.ynhelper.entity.Weather;
import cn.edu.qzu.ynhelper.event.DiseaseSearchEvent;
import cn.edu.qzu.ynhelper.event.PesticideSearchEvent;
import cn.edu.qzu.ynhelper.event.UserEvent;
import cn.edu.qzu.ynhelper.fragment.NewsFragment;
import cn.edu.qzu.ynhelper.fragment.PesticideFragment;
import cn.edu.qzu.ynhelper.fragment.disease.DiseaseListFragment;
import cn.edu.qzu.ynhelper.util.UserUtil;
import cn.edu.qzu.ynhelper.util.WeatherUtil1;
import de.hdodenhof.circleimageview.CircleImageView;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,SearchView.OnQueryTextListener,View.OnClickListener {

    public Fragment mContent = null;   // 当前显示的Fragment

    public Fragment mNewsFragment,
            mDiseaseListFragment,
            mPesticideFragment;

    public NavigationView navigationView;
    public SearchView mSearchView;
    public CircleImageView ivProfile;
    public TextView tvUsername,tvWeather,tvWeatherTemp;
    public ImageView ivWeather;

    MenuItem item ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ivProfile = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.img_profile);
        ivProfile.setOnClickListener(this);
        tvUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_username);
        tvWeather = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_weather);
        tvWeatherTemp = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_weather_temp);
        ivWeather = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_weather);
        tvWeather.setOnClickListener(this);
        ivWeather.setOnClickListener(this);
        initFragment();
        attemptLoadUser();
        requestWeather();
    }

    private void setDefaultNavMenuItem(NavigationView navigationView){
        navigationView.setCheckedItem(R.id.nav_news);
        navigationView.getMenu().performIdentifierAction (R.id.nav_news, 0);
    }

    private void initFragment(){
        mNewsFragment = new NewsFragment();
        mDiseaseListFragment = new DiseaseListFragment();
        mPesticideFragment = new PesticideFragment();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        item = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(this);
        setDefaultNavMenuItem(navigationView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            switchContent(mNewsFragment);
            getSupportActionBar().setTitle("农业资讯");
            hideSearchMenu();
        } else if (id == R.id.nav_disease) {
            switchContent(mDiseaseListFragment);
            getSupportActionBar().setTitle("常见病症");
            showSearchMenu();
        } else if (id == R.id.nav_pesticide) {
            switchContent(mPesticideFragment);
            getSupportActionBar().setTitle("常见农药");
            showSearchMenu();
        } else if (id == R.id.nav_settings) {
            //startActivity(new Intent(this,DiseaseDetailActivity.class));
            startActivity(new Intent(this,SettingActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 切换Fragment
    public void switchContent( Fragment des) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        if (mContent != null) {
            if (!des.isAdded()) {    // 先判断是否被add过
                t.hide(mContent).add(R.id.content_frame, des).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                t.hide(mContent).show(des).commit(); // 隐藏当前的fragment，显示下一个
            }
        } else {
            t.add(R.id.content_frame,des).commit();
        }
        mContent = des;
    }

    private void showSearchMenu(){
        item.setVisible(true);
        item.setEnabled(true);
    }

    private void hideSearchMenu(){
        item.setVisible(false);
        item.setEnabled(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mContent instanceof DiseaseListFragment){
            // 搜索病症
            EventBus.getDefault().post(new DiseaseSearchEvent(newText));
        }else if(mContent instanceof PesticideFragment){
            // 搜索农药
            EventBus.getDefault().post(new PesticideSearchEvent(newText));
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_profile:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.tv_weather:
            case R.id.iv_weather:
                startActivity(new Intent(this,WeatherActivity.class));
        }
    }

    @Subscribe
    public void onEventMainThread(UserEvent event){
        User user = event.getUser();
        if(user == null){
            // 退出登录
        }else {
            Picasso.with(this).load(user.getImg()).into(ivProfile);
            tvUsername.setText(user.getUsername());
        }
    }

    public void attemptLoadUser(){
        User user = UserUtil.getLocalUser(this);
        if(user != null){
            EventBus.getDefault().post(new UserEvent(user));
        }
    }

    public void requestWeather(){
        WeatherUtil1.requestWeather(this, new WeatherUtil1.IWeather() {
            @Override
            public void onSuccess(List<Weather> list) {
                Weather weather = list.get(0);
                tvWeather.setText(weather.getWeather());
                tvWeatherTemp.setText(weather.getTemp_high()+"~"+weather.getTemp_low()+"℃");
                String iconId = WeatherUtil1.getWeatherIconId(weather.getWeatid());
                ivWeather.setImageDrawable(WeatherUtil1.getWeatherDrawable(getApplicationContext(),iconId));
            }

            @Override
            public void onError(String msg) {
                tvWeather.setText("加载失败");
                Log.e("Weather",msg==null?"":msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
