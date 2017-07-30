package com.luannt.lap10515.demosimpleapp.view.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.luannt.lap10515.demosimpleapp.R;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.application.MainApplication;
import com.luannt.lap10515.demosimpleapp.eventmessage.ConnectionMessage;
import com.luannt.lap10515.demosimpleapp.presenter.base.Presenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MainActivity<P extends Presenter> extends AppCompatActivity implements MainView {

    protected ProgressDialog mProgressDialog;

    @Inject
    protected P mPresenter;

    @Inject
    protected Context mContext;
    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        inject(getAppCompent());
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    protected void initViews() {
        mProgressDialog= new ProgressDialog(mContext);
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");
    }




    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeNetworking(ConnectionMessage message){
        if(message.isEnable()) {
            Log.d("Connection", "Connecting");
            Toast.makeText(mContext, "Connected internet!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mContext, "Disconnected internet!!!", Toast.LENGTH_SHORT).show();
            Log.d("Connection", "Disconnecting");
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWaitingDialog() {
        if(mProgressDialog !=null ){
            mProgressDialog.show();
        }
    }
    public void dissmissWaitingDialog() {
        if (mProgressDialog !=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    protected abstract void inject(AppComponent appComponent);
    protected abstract int getLayoutId();
    protected AppComponent getAppCompent(){
       return MainApplication.getInstance().getmAppComponent();

    }

}
