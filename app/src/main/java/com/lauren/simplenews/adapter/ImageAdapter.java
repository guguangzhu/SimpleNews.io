package com.lauren.simplenews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lauren.simplenews.R;
import com.lauren.simplenews.image.ImageBean;
import com.lauren.simplenews.utils.ImageLoaderUtils;
import com.lauren.simplenews.utils.ToolsUtil;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ItemViewHolder> {

    private List<ImageBean> mData;
    private Context mContext;
    private int mMaxWidth;
    private int mMaxHeight;

    private OnItemClickListener mOnItemClickListener;

    public ImageAdapter(Context context) {
        this.mContext = context;
        mMaxWidth = ToolsUtil.getWidthInPx(mContext) - 20;
        mMaxHeight = ToolsUtil.getHeightInPx(mContext) - ToolsUtil.getStatusHeight(mContext) -
                ToolsUtil.dip2px(mContext, 96);
    }

    public void setDate(List<ImageBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public ImageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ItemViewHolder holder, int position) {
        ImageBean imageBean = mData.get(position);
        if(imageBean == null) {
            return;
        }
        holder.mTitle.setText(imageBean.title);
        float scale = (float)imageBean.width / (float) mMaxWidth;
        int height = (int)(imageBean.height / scale);
        if(height > mMaxHeight) {
            height = mMaxHeight;
        }
        holder.mImage.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth, height));
        ImageLoaderUtils.display(mContext, holder.mImage, imageBean.thumbUrl);
    }

    @Override
    public int getItemCount() {
        if(mData == null) {
            return 0;
        }
        return mData.size();
    }

    public ImageBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public ImageView mImage;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mImage = (ImageView) v.findViewById(R.id.ivImage);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }

}
