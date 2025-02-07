package com.lauren.simplenews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.lauren.simplenews.R;
import com.lauren.simplenews.utils.ImageLoaderUtils;

/**
 * Created by whiskeyfei on 16-2-27.
 */
public class NewCardView extends android.support.v7.widget.CardView {
    private ImageView mImageView;
    private TextView mTitle, mDesc;
    private Context mContext;

    public NewCardView(Context context) {
        super(context);
        init(context);
    }

    public NewCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp.leftMargin = (int) getResources().getDimension(R.dimen.card_margin);
        lp.rightMargin = (int) getResources().getDimension(R.dimen.card_margin);
        lp.bottomMargin = (int) getResources().getDimension(R.dimen.card_margin);
        setLayoutParams(lp);
        LayoutInflater.from(mContext).inflate(R.layout.item_news, this, true);
        mImageView = (ImageView) findViewById(R.id.ivNews);
        mTitle = (TextView) findViewById(R.id.tvTitle);
        mDesc = (TextView) findViewById(R.id.tvDesc);
    }

    public void setTitle(String string) {
        if (mTitle != null) {
            mTitle.setText(string);
        }
    }

    public void setContent(String string) {
        if (mDesc != null) {
            mDesc.setText(string);
        }
    }

    public void setImageURL(String imageUrl) {
        if (mImageView != null) {
            ImageLoaderUtils.display(mContext, mImageView, imageUrl);
        }
    }
}
