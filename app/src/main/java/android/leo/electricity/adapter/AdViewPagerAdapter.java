package android.leo.electricity.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Leo on 2017/7/10.
 */

public class AdViewPagerAdapter extends PagerAdapter{

    private List<ImageView> images;

    public AdViewPagerAdapter(List<ImageView> images){
        this.images = images;
    }

    @Override
    public int getCount(){
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj){
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object){
        view.removeView(images.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position){
        // TODO Auto-generated method stub
        view.addView(images.get(position));
        return images.get(position);
    }
}
