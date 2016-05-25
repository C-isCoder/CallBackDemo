# CallBackDemo
一个回调的Demo 还包含了 RecycleView 的用法 瀑布流布局  以及 Bitmap的工具类 压缩图片
#回调关键类:
  set1：定义一个接口；
  <pre><code>
    package qiqi.love.you;
    import android.widget.TextView;
    /**
    * Created by iscod on 2016/5/25.
    */
    public interface RecycleOnClick {
      void ItemOnClick(BabyData data, TextView view);
    }
  </code></pre> 
  set2：一个类中创建这个接口的对象并提供一个对外的设置方法（方法穿的参数即接口类）；
  <pre><code>
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
    }
  </code></pre>
  set3：在要回调的地方创建一个set2类的对象，并调用设置方法设置set2类中的接口；
  <pre><code>
    RecycleAdapter mAdapter;
    mAdapter = new RecycleAdapter(this, mList);
    mAdapter.setRecycleOnClickListener(new RecycleOnClick() {
            @Override
            public void ItemOnClick(BabyData data, TextView view) {
                Snackbar.make(recycleView, data.getText(), Snackbar.LENGTH_SHORT).show();
                view.setText("小样点你了吧~~！！");
            }
            });
  </code></pre>
#RecycleAdapter的写法，以及RecycleView实现瀑布流布局
<pre><code>
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
          //开启线程，异步生成图片、压缩避免recycle滑动卡顿
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
</code></pre>
#图片压缩工具类：
<pre><code>
package qiqi.love.you;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
  * Created by iscod on 2016/5/25.
  */
/**
  * @author:Jack Tony
  * @description :
  * @web :
  * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
  * http://www.cnblogs.com/kobe8/p/3877125.html
  * @date :2015年1月27日
  */
  public class BitmapPhotoUtils {

    /**
     * @param options   参数
     * @param reqWidth  目标的宽度
     * @param reqHeight 目标的高度
     * @return
     * @description 计算图片的压缩比率
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * @param src
     * @param dstWidth
     * @param dstHeight
     * @return
     * @description 通过传入的bitmap，进行压缩，得到符合标准的bitmap
     */
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, int inSampleSize) {
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    /**
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     * @description 从Resources中加载图片
     */
    public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置成了true,不占用内存，只获取bitmap宽高
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长宽，目的是得到图片的宽高
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); // 调用上面定义的方法计算inSampleSize值
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize); // 通过得到的bitmap，进一步得到目标大小的缩略图
    }

    /**
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     * @description 从SD卡上加载图片
     */
    public static Bitmap decodeBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize);
    }
  }

</code></pre>
