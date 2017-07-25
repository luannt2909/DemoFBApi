package com.luannt.lap10515.demosimpleapp.presenter.base;

/**
 * Created by lap10515 on 18/07/2017.
 */

public interface Presenter<V> {
    void setView(V view);
    V getView();
}
