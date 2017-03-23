package com.lauren.simplenews.zhihu;

import com.lauren.simplenews.beans.ZhihuBean;
import com.lauren.simplenews.image.ImageBean;
import com.lauren.simplenews.mvp.BasePresenter;
import com.lauren.simplenews.mvp.BaseView;

import java.util.List;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/3/22 17:46
 */
public interface ZhihuContract {
    interface View extends BaseView<Presenter> {
        void onSuccess(List<ZhihuBean.StoriesEntity> list);

        void showProgress();

        void hideProgress();

        void showLoadFailMsg();
    }


    interface Presenter extends BasePresenter {
    }
}
