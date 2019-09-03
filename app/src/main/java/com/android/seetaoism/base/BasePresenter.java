package com.android.seetaoism.base;

import java.util.ArrayList;

public abstract class BasePresenter<M extends BaseModel,V extends BaseView> {
    protected M model;
    protected V view;
    ArrayList<BaseModel> list = new ArrayList<>();
    public void setModel(M model) {
        this.model = model;
        list.add(model);
    }

    public void setView(V view) {
        this.view = view;
    }

    protected void onDestory(){
        if(view != null){
            view = null;
        }
        if(list!=null && list.size()>0){
            for (BaseModel model :list) {
                model.onDestory();
            }
        }
        if(model != null){
            model = null;
        }

    }
}
