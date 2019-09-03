package com.android.seetaoism.base;

public abstract class BaseMVPFragment<P extends BasePresenter,M extends BaseModel,V extends BaseView> extends BaseFragment {


    protected P p;

    @Override
    protected void initMVP() {
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
    public void onDestroyView() {
        if(p != null){
            p.onDestory();
            p = null;
        }
        super.onDestroyView();
    }
}
