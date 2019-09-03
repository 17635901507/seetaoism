package com.android.seetaoism.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.seetaoism.R;
import com.android.seetaoism.Text;
import com.android.seetaoism.entity.NvBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class RlvNvAdapter extends RecyclerView.Adapter<RlvNvAdapter.ViewHolder> {
    Context context;
    ArrayList<NvBean> list;

    public RlvNvAdapter(Context context, ArrayList<NvBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RlvNvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_nv, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RlvNvAdapter.ViewHolder viewHolder, int i) {
        NvBean nvBean = list.get(i);
        viewHolder.title.setText(nvBean.title);
        viewHolder.img.setBackgroundColor(nvBean.color);
        RequestOptions requestOptions = new RequestOptions()
                                .circleCrop();//圆形

        RoundedCorners roundedCorners = new RoundedCorners(20);//圆角
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(R.drawable.bg_channel).apply(requestOptions).into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }
    }
}
