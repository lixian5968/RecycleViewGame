package com.yjmfortune.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));


        //显示多少列
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        //显示多少行
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));

//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));


        mRecyclerView.setAdapter(mAdapter = new HomeAdapter(MainActivity.this));


    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        Context ct;

        public HomeAdapter(Context ct) {
            this.ct = ct;
        }

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            //一点的
//            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(ct).inflate(R.layout.item, null));
            //全局
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(ct).inflate(R.layout.item_home, viewGroup, false));

            return holder;
        }

        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.tv.setText(mDatas.get(i));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_num);
            }
        }
    }

}
