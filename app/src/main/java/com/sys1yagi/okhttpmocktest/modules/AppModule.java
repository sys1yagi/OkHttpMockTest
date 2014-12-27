package com.sys1yagi.okhttpmocktest.modules;

import com.google.gson.Gson;

import com.squareup.okhttp.OkHttpClient;
import com.sys1yagi.okhttpmocktest.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = MainActivity.class)
public class AppModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

}
