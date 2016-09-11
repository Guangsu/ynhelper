package cn.edu.qzu.ynhelper;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cn.edu.qzu.ynhelper.db.PesticideDBHelper;
import cn.edu.qzu.ynhelper.entity.Pesticide;

public class PesticideDetailActivity extends AppCompatActivity  {

    public static final String TAG = "PesticideActivity";

    private MenuItem menuStar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private TextView tvCompany,tvCategory,tvSpec,tvContent;

    private PesticideDBHelper dbHelper;
    private Pesticide pesticide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesticide);

        initView();
        initData();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        tvSpec = (TextView) findViewById(R.id.tv_spec);
        tvContent = (TextView) findViewById(R.id.tv_content);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star(view);
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Log.i(TAG,"AppBarLayout===onOffsetChanged");
                if (menuStar != null) menuStar.setVisible(!fab.isShown());
            }
        });
    }

    private void initData(){
        collapsingToolbarLayout.setBackgroundResource(R.drawable.pggfz);
        dbHelper = new PesticideDBHelper(this);
        pesticide = dbHelper.queryDetail(getIntent().getIntExtra("id",0));
        collapsingToolbarLayout.setTitle(pesticide.getName());
        if(pesticide.getCompany() == null || pesticide.getCompany().trim().isEmpty()){
            tvCompany.setVisibility(View.GONE);
        }else {
            tvCompany.setText(pesticide.getCompany());
        }
        if(pesticide.getCategory() == null || pesticide.getCategory().trim().isEmpty()){
            tvCategory.setVisibility(View.GONE);
        }else {
            tvCategory.setText(pesticide.getCategory());
        }
        if(pesticide.getSpec() == null || pesticide.getSpec().trim().isEmpty()){
            tvSpec.setVisibility(View.GONE);
        }else {
            tvSpec.setText(pesticide.getSpec());
        }
        if(pesticide.getIntroduction() == null || pesticide.getIntroduction().trim().isEmpty()){
            tvContent.setVisibility(View.GONE);
        }else {
            tvContent.setText(pesticide.getIntroduction());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        menuStar = menu.findItem(R.id.action_star);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_star:
                star(fab);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void star(View view){
        Snackbar.make(view, "收藏成功", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void updateStarState(boolean stared){

    }

}
