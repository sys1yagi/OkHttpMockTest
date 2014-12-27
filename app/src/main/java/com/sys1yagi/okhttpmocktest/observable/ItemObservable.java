package com.sys1yagi.okhttpmocktest.observable;

import com.google.gson.Gson;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sys1yagi.okhttpmocktest.models.Item;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class ItemObservable {

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Gson gson;

    @Inject
    public ItemObservable() {
    }

    public String buildPath(int id) {
        return "https://dummy.api.endpoint/" + id;
    }

    public Observable<Item> fromId(final int id) {
        return Observable.create(new Observable.OnSubscribe<Item>() {
            @Override
            public void call(Subscriber<? super Item> subscriber) {
                String url = buildPath(id);
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        subscriber.onNext(gson.fromJson(json, Item.class));
                        subscriber.onCompleted();
                    } else {
                        //TODO
                        //I think better that define the OkHttpClientException.
                        subscriber.onError(new Exception(response.message()));
                        subscriber.onCompleted();
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                    subscriber.onCompleted();
                }
            }
        });
    }
}
