package qiqi.love.you;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    RecycleAdapter mAdapter;
    List<BabyData> mList;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        BabyData data = new BabyData();
        mList = data.getBabayList();
    }

    private void initView() {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        mAdapter = new RecycleAdapter(this, mList);
        recycleView.setAdapter(mAdapter);
        //设置接口，并实现回调方法
        mAdapter.setRecycleOnClickListener(new RecycleOnClick() {
            @Override
            public void ItemOnClick(BabyData data, TextView view) {
                Snackbar.make(recycleView, data.getText(), Snackbar.LENGTH_SHORT).show();
                view.setText("小样点你了吧~~！！");
            }
        });
    }
}
