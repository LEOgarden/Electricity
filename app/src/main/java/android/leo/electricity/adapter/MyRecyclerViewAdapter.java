package android.leo.electricity.adapter;

import android.graphics.Bitmap;
import android.leo.electricity.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2017/7/7.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private ArrayList<Bitmap> bitmapDrawables = new ArrayList<>();

    private View mHeaderView;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li){
        mListener = li;
    }

    public void setHeaderView(View headerView){
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView(){
        return mHeaderView;
    }

    public void addBitmap(List<Bitmap> bitmaps){
        bitmapDrawables.addAll(bitmaps);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position){
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){

        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        final Bitmap bitmap = bitmapDrawables.get(pos);
        if(viewHolder instanceof Holder){
            ((Holder) viewHolder).imageView.setImageBitmap(bitmap);
            if(mListener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mListener.onItemClick(pos, bitmap);
                }
            });
        }

    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount(){
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);
            if(itemView == mHeaderView) return;
            imageView = (ImageView) itemView.findViewById(R.id.recycleView_item_imageView);
        }
    }

    interface OnItemClickListener{
        void onItemClick(int position, Bitmap bitmap);
    }
}
