package cn.edu.qzu.ynhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Map;

import cn.edu.qzu.ynhelper.entity.DataReturn;
import cn.edu.qzu.ynhelper.entity.NewsDetail;
import cn.edu.qzu.ynhelper.http.AbstractObjectCallback;
import cn.edu.qzu.ynhelper.http.ObjectCallback;
import cn.edu.qzu.ynhelper.http.UrlConfig;

public class NewsDetailActivity extends AppCompatActivity{

    private TextView tvTitle,tvAuthor,tvSrc,tvTime;
    private WebView wvDetail;
    private View loadFailedView;

    public int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getSupportActionBar().setTitle("资讯详情");
        newsId = getIntent().getIntExtra("id",-1);
        initViews();
        requestNews(newsId);
    }

    private void initViews(){
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvAuthor =(TextView)  findViewById(R.id.tv_author);
        tvSrc = (TextView)findViewById(R.id.tv_src);
        tvTime = (TextView)findViewById(R.id.tv_time);
        wvDetail = (WebView) findViewById(R.id.webview);
        loadFailedView = findViewById(R.id.v_load_failed);

        loadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFailedView.setVisibility(View.GONE);
                requestNews(newsId);
            }
        });
        wvDetail.getSettings().setDefaultTextEncodingName("GBK");
        wvDetail.setBackgroundColor(0);
        // 可以自动缩放图片显示
        wvDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    private void requestNews(int newsId){
        if(newsId < 1){
            return ;
        }
        OkHttpUtils.get().url(UrlConfig.NEWS_DETAIL)
                .addParams("id",newsId+"").build()
                .execute(new ObjectCallback(new NewsCallback()));
    }

    private void handleNews(NewsDetail detail){
        tvTitle.setText(detail.getTitle());
        tvAuthor.setText(detail.getAuthor());
        tvSrc.setText(detail.getSrc());
        tvTime.setText(detail.getTime());

        wvDetail.loadUrl(UrlConfig.HOST + detail.getDetail());
    }

    class NewsCallback extends AbstractObjectCallback{
        @Override
        public void onSuccess(DataReturn data) {
            Map map = (Map)data.getData();
            NewsDetail detail = new Gson().fromJson(new Gson().toJson(map),NewsDetail.class);
            handleNews(detail);
        }

        @Override
        public void onError(DataReturn data) {
            setLoadFailedView();
        }

        @Override
        public void onFailure(Request request, Exception e) {
            super.onFailure(request, e);
            setLoadFailedView();
        }
    }

    private void setLoadFailedView(){
        loadFailedView.setVisibility(View.VISIBLE);
    }
}
