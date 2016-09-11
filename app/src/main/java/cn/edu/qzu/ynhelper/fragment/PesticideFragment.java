package cn.edu.qzu.ynhelper.fragment;

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
import cn.edu.qzu.ynhelper.adapter.PesticideListAdapter;
import cn.edu.qzu.ynhelper.db.PesticideDBHelper;
import cn.edu.qzu.ynhelper.entity.Pesticide;
import cn.edu.qzu.ynhelper.event.PesticideSearchEvent;
import cn.edu.qzu.ynhelper.view.PullToLoadMoreRecyclerView;

public class PesticideFragment extends Fragment {


    private PullToLoadMoreRecyclerView recyclerView;
    private PesticideListAdapter mAdapter;
    private List<Pesticide> data;

    private PesticideDBHelper dbHelper;

    private int lastRow = 0;
    private String key = "";

    public PesticideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        System.out.println("农药OnCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pesticide, container, false);
        initView(rootView);

        initData();

        return rootView;
    }

    private void initView(View rootView){
        recyclerView = (PullToLoadMoreRecyclerView) rootView.findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initData() {
        data = new ArrayList<>();
        dbHelper = new PesticideDBHelper(getContext());
        mAdapter = new PesticideListAdapter(getActivity(),data);
        recyclerView.setAdapter(mAdapter);
        new PesticideTask().execute(0,"");
        recyclerView.setOnLoadMoreListener(new PullToLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new PesticideTask().execute(lastRow,key);
            }
        });
    }

    private List<Pesticide> search(int lastRow,String key){
        if(lastRow == 0){
            if(data != null){
                data.clear();
            }
        }
        return dbHelper.query(lastRow,key);
    }
    @Subscribe
    public void onEventMainThread(PesticideSearchEvent event){
        key = event.getKeyword();
        lastRow = 0;
        new PesticideTask().execute(lastRow,key);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class PesticideTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            return search((int)params[0],(String)params[1]);
        }

        @Override
        protected void onPostExecute(Object o) {
            List<Pesticide> list = (List<Pesticide>) o;
            if(lastRow == 0){
                data.clear();
            }
            data.addAll(list);
            lastRow = lastRow + list.size();
            recyclerView.onLoadMoreFinish(true);
            super.onPostExecute(o);
        }
    }

}
