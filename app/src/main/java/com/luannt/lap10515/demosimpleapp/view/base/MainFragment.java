package com.luannt.lap10515.demosimpleapp.view.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.application.MainApplication;
import com.luannt.lap10515.demosimpleapp.presenter.base.Presenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lap10515 on 26/07/2017.
 */

public abstract class MainFragment<P extends Presenter> extends Fragment implements MainView {
    @Inject
    protected P mPresenter;

    @Inject
    protected Context mContext;

    protected Unbinder mUnbinder;

    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getAppComponent());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initViews();
        if(mPresenter != null){
            mPresenter.setView(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
        /*if(mPresenter != null){
            mPresenter.setView(null);
        }*/
    }

    protected void initViews() {
        mProgressDialog= new ProgressDialog(mContext);
        mProgressDialog.setTitle("Please wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWaitingDialog() {
        if(mProgressDialog ==null ){
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.show();
    }
    public void dissmissWaitingDialog() {
        if (mProgressDialog !=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    protected abstract int getLayoutId();
    protected abstract void inject(AppComponent appComponent);

    protected AppComponent getAppComponent(){
        return MainApplication.getInstance().getmAppComponent();
    }

}
