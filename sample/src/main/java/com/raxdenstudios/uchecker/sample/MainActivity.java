package com.raxdenstudios.uchecker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.raxdenstudios.uchecker.ReactiveUCheckerProvider;
import com.raxdenstudios.uchecker.UCheckerRequest;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UCheckerRequest request = new UCheckerRequest
                .Builder(this)
                .setPackageName("com.google.android.gm")
                .setVersionName("1.0.0")
                .create();

        ReactiveUCheckerProvider provider = new ReactiveUCheckerProvider();
        provider.checkVersion(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isLastVersion) {
                        doSomething(isLastVersion);
                    }
                });
    }

    private void doSomething(boolean isLastVersion) {
        // do somehing...
    }

}
