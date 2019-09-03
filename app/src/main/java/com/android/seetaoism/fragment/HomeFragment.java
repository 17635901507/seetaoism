package com.android.seetaoism.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.android.seetaoism.R;
import com.android.seetaoism.base.BaseFragment;
import com.android.seetaoism.basef.Constants;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.img)
    ImageView mImg;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        mTab.addTab(mTab.newTab().setText("A"));
        mTab.addTab(mTab.newTab().setText("B"));
        mTab.addTab(mTab.newTab().setText("C"));
        mTab.addTab(mTab.newTab().setText("D"));
        mTab.addTab(mTab.newTab().setText("E"));
        mTab.addTab(mTab.newTab().setText("F"));
        mTab.addTab(mTab.newTab().setText("G"));
        mTab.addTab(mTab.newTab().setText("H"));
        mTab.addTab(mTab.newTab().setText("I"));
    }

    @OnClick({R.id.tab, R.id.img})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tab:

                break;
            case R.id.img:
                EventBus.getDefault().postSticky(Constants.OPEN);
                break;
        }
    }
}
