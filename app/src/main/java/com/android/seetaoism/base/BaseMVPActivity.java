package com.android.seetaoism.base;

public abstract class BaseMVPActivity<P extends BasePresenter,M extends BaseModel,V extends BaseView> extends BaseActivity {


    protected P p;

    @Override
    protected void initMVP() {
        super.initMVP();
        p = initMVPPresenter();
        if(p != null){
            p.setModel(initMVPModel());
            p.setView(initMVPView());
        }
    }

    protected abstract V initMVPView();

    protected abstract M initMVPModel();


    protected abstract P initMVPPresenter();

    @Override
    protected void onDestroy() {
        if(p != null){
            p.onDestory();
            p = null;
        }
        super.onDestroy();
    }
}
