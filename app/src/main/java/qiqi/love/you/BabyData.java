package qiqi.love.you;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iscod on 2016/5/25.
 */
public class BabyData {
    private String text;
    private int imageRes;
    private List<BabyData> mList;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public List<BabyData> getBabayList() {
        if (mList == null) mList = new ArrayList<BabyData>();
        if (mList.isEmpty()) {
            BabyData data1 = new BabyData();
            data1.setImageRes(R.mipmap.img1);
            data1.setText("琪琪，是个二货啊");
            mList.add(data1);
            BabyData data2 = new BabyData();
            data2.setImageRes(R.mipmap.img2);
            data2.setText("只愿君心似我心，定不负相思意。");
            mList.add(data2);
            BabyData data3 = new BabyData();
            data3.setImageRes(R.mipmap.img3);
            data3.setText("思君如明烛，煎心且衔泪。");
            mList.add(data3);
            BabyData data4 = new BabyData();
            data4.setImageRes(R.mipmap.img4);
            data4.setText("明月不谙离恨苦，斜光到晓穿朱户。");
            mList.add(data4);
            BabyData data5 = new BabyData();
            data5.setImageRes(R.mipmap.img5);
            data5.setText("天不老，情难绝。有关于爱情的诗句。心似双丝网，中有千千结。");
            mList.add(data5);
            BabyData data6 = new BabyData();
            data6.setImageRes(R.mipmap.img6);
            data6.setText("人如风后入江云，情似雨馀黏地絮。");
            mList.add(data6);
            BabyData data7 = new BabyData();
            data7.setImageRes(R.mipmap.img7);
            data7.setText("若有知音见采，不辞遍唱阳春。");
            mList.add(data7);
            BabyData data8 = new BabyData();
            data8.setImageRes(R.mipmap.img8);
            data8.setText("在天愿作比翼鸟，在地愿为连理枝。");
            mList.add(data8);
            BabyData data9 = new BabyData();
            data9.setImageRes(R.mipmap.img9);
            data9.setText("寻好梦，梦难成。况谁知我此时情。枕前泪共帘前雨，隔个窗儿滴到明。");
            mList.add(data9);
        }
        return mList;
    }
}
