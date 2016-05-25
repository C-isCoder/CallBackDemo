package qiqi.love.you;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by iscod on 2016/5/25.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    //创建一个接口对象
    private RecycleOnClick mListenter;

    //设置接口对象
    public void setRecycleOnClickListener(RecycleOnClick listener) {
        mListenter = listener;
    }

    private List<BabyData> mList;
    private Context mContext;

    public RecycleAdapter(Context context, List<BabyData> mList) {
        mContext = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycle_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BabyData data = mList.get(position);
        if (data == null) return;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                holder.imageView.setImageBitmap((Bitmap) msg.obj);
                super.handleMessage(msg);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapPhotoUtils.decodeBitmapFromResource(
                        mContext.getResources(), data.getImageRes(), 300, 300);
                Message message = new Message();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }).start();

        holder.textView.setText(data.getText());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用实现接口中的方法
                mListenter.ItemOnClick(data, holder.textView);
            }
        });
    }


    @Override
    public int getItemCount() {

        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view)
        ImageView imageView;
        @BindView(R.id.text_view)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
