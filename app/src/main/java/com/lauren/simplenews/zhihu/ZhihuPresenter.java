package com.lauren.simplenews.zhihu;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lauren.simplenews.beans.ZhihuBean;
import com.lauren.simplenews.commons.ApiConstants;
import com.lauren.simplenews.image.ImageBean;
import com.lauren.simplenews.mvp.BaseSchedulerProvider;
import com.lauren.simplenews.utils.ActivityUtils;
import com.lauren.simplenews.utils.ListUtils;
import com.lauren.simplenews.utils.OkHttpUtils;
import com.lauren.simplenews.utils.StringUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/3/22 18:22
 */
public class ZhihuPresenter implements ZhihuContract.Presenter {
    private static final String TAG = "ZhihuPresenter";

    private final ZhihuContract.View mView;//view接口 用于更新UI
    private final BaseSchedulerProvider mSchedulerProvider;
    private CompositeSubscription mSubscriptions;

    public ZhihuPresenter(ZhihuContract.View statisticsView, BaseSchedulerProvider schedulerProvider) {
        mView = ActivityUtils.checkNotNull(statisticsView, "StatisticsView cannot be null!");
        mSchedulerProvider = ActivityUtils.checkNotNull(schedulerProvider,"schedulerProvider cannot be null!");
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    private void startTask() {
        mSubscriptions.clear();
        Subscription subscription = getObservable().subscribe(getSubscriber());
        mSubscriptions.add(subscription);
    }

    public Observable<List<ZhihuBean.StoriesEntity>> getObservable() {
        return Observable.just(ApiConstants.URL_ZHIHU).flatMap(new Func1<String, Observable<List<ZhihuBean.StoriesEntity>>>() {
            @Override
            public Observable<List<ZhihuBean.StoriesEntity>> call(final String url) {
                //可以拼接url
                return Observable.create(new Observable.OnSubscribe<List<ZhihuBean.StoriesEntity>>() {

                    @Override
                    public void call(final Subscriber<? super List<ZhihuBean.StoriesEntity>> subscriber) {
                        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
                            @Override
                            public void onSuccess(String response) {
                                Log.d(TAG, "onSuccess response:" + response);
                                if(StringUtils.isEmpty(response)){
                                    subscriber.onError(null);
                                    return;
                                }
                                Gson gson = new Gson();
                                ZhihuBean zhihuBean = gson.fromJson(response, ZhihuBean.class);
                                if(zhihuBean==null){
                                    subscriber.onError(null);
                                    return;
                                }
                                if(ListUtils.isEmpty(zhihuBean.getStories())){
                                    subscriber.onError(null);
                                    return;
                                }
                                subscriber.onNext(zhihuBean.getStories());
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                subscriber.onError(e);
                            }
                        };
                        OkHttpUtils.get(url, loadNewsCallback);
                    }
                });
            }
        }) .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui());
    }

    private Subscriber<List<ZhihuBean.StoriesEntity>> getSubscriber() {
        return new Subscriber<List<ZhihuBean.StoriesEntity>>() {
            @Override
            public void onStart() {
                super.onStart();
                mView.showProgress();
            }

            @Override
            public void onCompleted() {
                mView.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                mView.showLoadFailMsg();
            }

            @Override
            public void onNext(List<ZhihuBean.StoriesEntity> list) {
                mView.onSuccess(list);
            }
        };
    }

    @Override
    public void subscribe() {
        startTask();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
