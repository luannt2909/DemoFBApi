package com.luannt.lap10515.demosimpleapp.application.AppComponent.modules;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luannt.lap10515.demosimpleapp.BuildConfig;
import com.luannt.lap10515.demosimpleapp.utils.AppConstants;
import com.luannt.lap10515.demosimpleapp.utils.ConnectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lap10515 on 18/07/2017.
 */
@Module
public class RetrofitModule {

    @Provides
    @Singleton
    public Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File httpCacheFile = new File(application.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheFile, cacheSize);
        return cache;
    }

    @Provides
    @Named("online")
    public Interceptor provideCacheControlInterceptor(final Application application){
        /*Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                if (ConnectionUtils.hasInternetConnection(application.getApplicationContext())) {
                    int maxAge = 60; // read from cache for 1 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };*/
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                String cacheControl = originalResponse.header("Cache-Control");
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + 5000)
                            .build();
                } else {
                    return originalResponse;
                }
            }
        };

        return interceptor;
    }

    @Provides
    @Named("offline")
    public Interceptor provideCacheOfflineInterceptor(final Application application){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!ConnectionUtils.hasInternetConnection(application.getApplicationContext())) {
                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached")
                            .build();
                }
                return chain.proceed(request);
            }
        };
        return interceptor;
    }


    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Singleton
    public OkHttpClient.Builder provideOkHttpClientBuilder(HttpLoggingInterceptor logging, Cache cache,
                                                           @Named("online") Interceptor interceptorOnline
                                                            , @Named("offline") Interceptor interceptorOffline) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(AppConstants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(AppConstants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .cache(cache)
                .addNetworkInterceptor(interceptorOnline)
                .addInterceptor(interceptorOffline)
                .addInterceptor(logging);
        return httpClient;
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofitModule(OkHttpClient.Builder httpClient, Gson gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }
}
