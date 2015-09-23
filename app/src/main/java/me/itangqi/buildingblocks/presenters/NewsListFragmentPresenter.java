package me.itangqi.buildingblocks.presenters;

import java.util.List;

import me.itangqi.buildingblocks.model.entity.Daily;
import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.model.IHttpCallBack;
import me.itangqi.buildingblocks.view.IViewPager;
import me.itangqi.buildingblocks.domin.utils.NetworkUtils;
import me.itangqi.buildingblocks.domin.utils.PrefUtils;

/**
 * Created by Troy on 2015/9/21.
 */
public class NewsListFragmentPresenter {
    private DailyModel mDailyModel;
    private IViewPager mIViewPager;
    private String date;

    public NewsListFragmentPresenter(IViewPager IViewPager, final String date) {
        this.mIViewPager = IViewPager;
        this.date = date;
        mDailyModel = new DailyModel(new IHttpCallBack() {
            @Override
            public void onFinish(List<Daily> dailyList) {
                mDailyModel.saveDailies(dailyList, date);
                loadData(dailyList);
            }
        });
    }

    public void getNews(String date) {
        if (PrefUtils.isEnableCache()) {
            mDailyModel.getFromCache(date);
            mDailyModel.getFromNet(date);
        }else if (!PrefUtils.isEnableCache() && NetworkUtils.isNetworkConnected()) {
            mDailyModel.getFromNet(date);
        }else if (PrefUtils.isEnableCache() && !NetworkUtils.isNetworkConnected()) {
            mDailyModel.getFromCache(date);
        }
    }

    public void loadData(List<Daily> dailies) {
        mIViewPager.loadData(dailies);
    }

}
