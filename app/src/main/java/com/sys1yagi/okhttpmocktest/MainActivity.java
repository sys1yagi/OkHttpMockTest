package com.sys1yagi.okhttpmocktest;

import com.sys1yagi.okhttpmocktest.models.Item;
import com.sys1yagi.okhttpmocktest.observable.ItemObservable;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import javax.inject.Inject;

import rx.android.observables.AndroidObservable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends ActionBarActivity {

    @Inject
    ItemObservable itemObservable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Application) getApplication()).inject(this);

        //このコードは動作しません。クライアントコードの例です。
        //ItemObservable doesn't work. It is example of code.
        AndroidObservable.bindActivity(this,
                itemObservable.fromId(10)
                        .subscribeOn(Schedulers.newThread()))
                .subscribe(
                        new Action1<Item>() {
                            @Override
                            public void call(Item item) {
                                showItem(item);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                //error
                            }
                        });
    }

    private void showItem(Item item) {
        //...
    }
}
