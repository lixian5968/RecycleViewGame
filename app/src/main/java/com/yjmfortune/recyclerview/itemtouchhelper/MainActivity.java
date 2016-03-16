package com.yjmfortune.recyclerview.itemtouchhelper;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yjmfortune.recyclerview.R;
import com.yjmfortune.recyclerview.sample.demo_recyclerview.StaggeredHomeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> datas;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
//        mAdapter = new RecyclerViewAdapter(datas);
        mAdapter = new StaggeredHomeAdapter(this, datas);
        mRecyclerView.setAdapter(mAdapter);

        ((StaggeredHomeAdapter)mAdapter).setOnItemClickLitener(new StaggeredHomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " click", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " long click", Toast.LENGTH_SHORT).show();
            }
        });


        //0则不执行拖动或者滑动
        //SimpleCallback(int dragDirs, int swipeDirs)
        //1、dragDirs- 表示拖拽的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
        //2、swipeDirs- 表示滑动的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
        //【注】：如果为0，则表示不触发该操作（滑动or拖拽）

        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
//                ItemTouchHelper.UP | ItemTouchHelper.DOWN
                , ItemTouchHelper.ACTION_STATE_IDLE) {
            /**
             * @param recyclerView
             * @param viewHolder 拖动的ViewHolderR
             * @param target 目标位置的ViewHolder
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Log.e("lx", "onMove");
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(datas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(datas, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                //返回true表示执行拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.e("lx", "onSwiped");
                int position = viewHolder.getAdapterPosition();
//                Toast.makeText(MainActivity.this,position+",删除",Toast.LENGTH_SHORT).show();
                datas.remove(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                Log.e("lx", "onChildDraw");
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //左右滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    //setTranslationX改变了view的位置，但没有改变view的LayoutParams里的margin属性；
                    viewHolder.itemView.setTranslationX(dX);
                }
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
                if (actionState == 1) {
                    Toast.makeText(MainActivity.this, "删除", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.e("lx", "onSelectedChanged");
                Log.e("lx", actionState + "");
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
                Log.e("lx", "clearView");
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void initDatas() {
        datas = new ArrayList<>();
        for (int i = 0; i <= 40; i++) {
            datas.add("Number:" + i);
        }
    }
}
