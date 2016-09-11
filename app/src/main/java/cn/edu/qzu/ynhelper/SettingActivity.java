package cn.edu.qzu.ynhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private RippleView rvDeleteCache,rvDeleteAccount;
    private MaterialDialog mDeleteAccountDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("设置");
        initView();
    }

    private void initView(){
        rvDeleteCache = (RippleView) findViewById(R.id.rv_delete_cache);
        rvDeleteAccount = (RippleView) findViewById(R.id.rv_delete_account);

        rvDeleteCache.setOnClickListener(this);
        rvDeleteAccount.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rv_delete_cache:


                break;
            case R.id.rv_delete_account:
                if(mDeleteAccountDialog == null){
                    mDeleteAccountDialog = new MaterialDialog.Builder(this)
                            .title("删除账户")
                            .content("此操作会退出当前登录且删除已缓存的当前账号信息")
                            .positiveText("删除")
                            .negativeText("取消")
                            .show();
                }else{
                    mDeleteAccountDialog.show();
                }

                break;
        }
    }
}
