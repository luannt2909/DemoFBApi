package com.luannt.lap10515.demosimpleapp.presenter.login;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.luannt.lap10515.demosimpleapp.presenter.base.MainPresenter;
import com.luannt.lap10515.demosimpleapp.view.login.LoginView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class LoginPresenterImpl extends MainPresenter<LoginView> implements LoginPresenter {

    @Inject
    public LoginPresenterImpl() {
    }

    @Override
    public void onLoginSuccessful(LoginResult result) {
        Observable.just(result.getAccessToken().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        mView.loginSuccessful(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                .doOnNext(id->mView.loginSuccessful(id))
//                .doOnError(error-> mView.showToast(error.getMessage()));
    }

    @Override
    public void onLoginError(FacebookException error) {
        Observable.just(error.getMessage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String s) throws Exception {
                        mView.showToast(s);
                    }
                });
    }

    @Override
    public void checkLoginStatus() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token != null){
            mView.showToast(token.toString());
            mView.loginSuccessful(token.getUserId());
        }
    }


}
