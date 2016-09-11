package cn.edu.qzu.ynhelper.fragment.disease;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.adapter.DiseaseListAdapter;
import cn.edu.qzu.ynhelper.db.DiseaseDBHelper;
import cn.edu.qzu.ynhelper.entity.Disease;
import cn.edu.qzu.ynhelper.event.DiseaseSearchEvent;
import cn.edu.qzu.ynhelper.view.PullToLoadMoreRecyclerView;

public class DiseaseListFragment extends Fragment {
    private static String TAG = "DiseaseListFragment";

    private PullToLoadMoreRecyclerView recyclerView;
    private DiseaseListAdapter mAdapter;
    private List<Disease> data;
    private DiseaseDBHelper dbHelper;

    private int lastRow = 0;
    private String key = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_disease_list, container, false);

        initView(rootView);
        initData();
        mAdapter = new DiseaseListAdapter(this.getContext(),data);

        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void initView(View rootView){
        recyclerView = (PullToLoadMoreRecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnLoadMoreListener(new PullToLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                    new DiseaseQueryTask().execute(lastRow,key);
            }
        });
    }

    private void initData() {
        data = new ArrayList<>();
        dbHelper = new DiseaseDBHelper(getContext());
        new DiseaseQueryTask().execute(0,"");
    }

    @Subscribe
    public void onEventMainThread(DiseaseSearchEvent event){
        key = event.getKeyword();
        lastRow = 0;
        new DiseaseQueryTask().execute(lastRow,key);
    }

    private List<Disease> search(int lastRow,String key){
        if(lastRow == 0){
            if(data != null){
                data.clear();
            }
        }
        return dbHelper.query(lastRow,key);
    }

    class DiseaseQueryTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            return search((int)params[0],(String)params[1]);
        }

        @Override
        protected void onPostExecute(Object o) {
            List<Disease> list = (List<Disease>) o;
            if(lastRow == 0){
                data.clear();
            }
            data.addAll(list);
            lastRow = lastRow + list.size();
            recyclerView.onLoadMoreFinish(true);
            super.onPostExecute(o);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
