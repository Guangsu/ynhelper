package cn.edu.qzu.ynhelper.fragment.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qzu.ynhelper.NewsDetailActivity;
import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.adapter.NewsListAdapter;
import cn.edu.qzu.ynhelper.entity.News;
import cn.edu.qzu.ynhelper.fragment.NewsFragment;
import cn.edu.qzu.ynhelper.http.UrlConfig;
import cn.edu.qzu.ynhelper.view.PullToLoadMoreRecyclerView;

public class NewsListFragment extends Fragment implements NewsListAdapter.OnItemClickListener{

    private static String TAG = "NewsListFragment";

    private int TYPE;

    private SwipeRefreshLayout swipeRefreshLayout;
    private PullToLoadMoreRecyclerView recyclerView;
    private NewsListAdapter mAdapter;
    private List<News> data;
    private int mLastId = 0;

    public NewsListFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TYPE = getArguments().getInt(NewsFragment.FRAGMENT_TAG);
            Log.i(TAG,"onCreate," + TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        initViews(view);
        data = new ArrayList<>();
        mAdapter = new NewsListAdapter(getContext(),data);

        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        requestData(mLastId);
        //initData();
        Log.i(TAG,"onCreateView," + TYPE);
        return view;
    }

    private void initData() {
        JSONObject json;
        switch (TYPE){
            /*case NewsFragment.FOCUS_NEWS:

                break;
            case NewsFragment.TRENDS:

                break;
            case NewsFragment.FOOD_SAFETY:

                break;
            case NewsFragment.POLICY:

                break;
            case NewsFragment.DEEPTH:

                break;*/
        }

            /*json = getTestData(TYPE);
            JSONArray array = json.getJSONArray("retData");
            data = EntityUtil.JSONArray2NewsList(array);*/
            data = new ArrayList<>();
            requestData(mLastId);

    }

    private void requestData(final int lastId){
        String url = UrlConfig.NEWS_LIST;
        OkHttpUtils.get().addParams("type",""+TYPE).addParams("lastid",""+lastId).url(url).tag(this).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                if(lastId != 0){
                    recyclerView.onLoadingMore();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                if(lastId != 0) {
                    recyclerView.onLoadMoreFinish(false);
                }
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    int errCode = json.getInt("errCode");
                    if(errCode == 200){
                        JSONArray array = json.getJSONArray("data");
                        JSONArray array1;
                        if(lastId == 0){
                            // lastId==0表示第一次加载或刷新
                            data.clear();
                        }
                        for(int i =0;i < array.length();i++){
                            array1 = array.getJSONArray(i);
                            data.add(News.fromJSONArray(array1));
                        }
                        mLastId = data.get(data.size()-1).getId();
                        if(lastId != 0){
                            recyclerView.onLoadMoreFinish(true);
                        }else{
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private JSONObject getTestData(int type) throws JSONException {
        switch(type){
            case News.FOCUS_NEWS:
                return new JSONObject("{\"errNum\":0,\"errMsg\":\"success\",\"retData\":[{\"id\":1,\"title\":\"FOCUS_NEWS-1北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"views\":1,\"author\":\"\"},{\"id\":2,\"title\":\"2北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":3,\"title\":\"3北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":4,\"title\":\"4北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":5,\"title\":\"5北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"}]}");
            case News.TRENDS:
                return new JSONObject("{\"errNum\":0,\"errMsg\":\"success\",\"retData\":[{\"id\":1,\"title\":\"TRENDS-1北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":2,\"title\":\"2北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":3,\"title\":\"3北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":4,\"title\":\"4北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":5,\"title\":\"5北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"}]}");
            case News.FOOD_SAFETY:
                return new JSONObject("{\"errNum\":0,\"errMsg\":\"success\",\"retData\":[{\"id\":1,\"title\":\"FOOD_SAFETY-1北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":2,\"title\":\"2北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":3,\"title\":\"3北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":4,\"title\":\"4北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":5,\"title\":\"5北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"}]}");
            case News.POLICY:
                return new JSONObject("{\"errNum\":0,\"errMsg\":\"success\",\"retData\":[{\"id\":1,\"title\":\"POLICY-1北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":2,\"title\":\"2北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":3,\"title\":\"3北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":4,\"title\":\"4北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":5,\"title\":\"5北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"}]}");
            /*case NewsFragment.DEEPTH:
                return new JSONObject("{\"errNum\":0,\"errMsg\":\"success\",\"retData\":[{\"id\":1,\"title\":\"DEEPTH-1北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":2,\"title\":\"2北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":3,\"title\":\"3北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":4,\"title\":\"4北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"},{\"id\":5,\"title\":\"5北京四分之三地域将划入生态红线区\",\"abstract\":\"本报北京11月30日电(记者朱竞若、余荣华)北京市规划委副主任王飞11月30日披露,正在修改的北京市总体规划,初步划定生态红线区面积约占市域面积的70%以上,远期生态红线区比例为75%左右。这意味着,未来北京将实现3/4市域为严禁与生态保护无关建设活动的生态红线区。\",\"image_url\":\"\",\"time\":\"\",\"views\":1,\"author\":\"\"}]}");*/
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach," + TYPE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        Log.i(TAG,"onDestroy," + TYPE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,"onDetach," + TYPE);
    }

    private void initViews(View root){
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swiperefreshlayout);
        recyclerView = (PullToLoadMoreRecyclerView) root.findViewById(R.id.recylerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(0);
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnLoadMoreListener(new PullToLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                requestData(mLastId);
            }
        });
        recyclerView.setLoadMoreText("上拉加载更多");
        recyclerView.setLoadingMoreText("正在加载...");
        recyclerView.setLoadMoreFailText("加载失败，请点击重试");
    }

    @Override
    public void OnItemClick(View v, int position) {
        int newsId = data.get(position).getId();
        Intent intent = new Intent(this.getActivity(), NewsDetailActivity.class);
        intent.putExtra("id",newsId);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause," + TYPE);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"onStart," + TYPE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop," + TYPE);
    }
}
