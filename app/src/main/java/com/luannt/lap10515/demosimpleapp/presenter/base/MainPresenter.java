package com.luannt.lap10515.demosimpleapp.presenter.base;

import com.luannt.lap10515.demosimpleapp.view.base.MainView;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class MainPresenter<V extends MainView> implements Presenter<V> {
    protected V mView;
    @Override
    public void setView(V view) {
        mView = view;
    }

    @Override
    public V getView() {
        return mView;
    }
}
