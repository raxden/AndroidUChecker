package com.raxdenstudios.uchecker;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Ángel Gómez on 19/02/2017.
 */

public class ReactiveUCheckerProvider {

    private static final String TAG = ReactiveUCheckerProvider.class.getSimpleName();

    public ReactiveUCheckerProvider() {

    }

    public Observable<String> retrieveLastVersion(final UCheckerRequest request) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        String lastVersion = request.retrieveLastVersion();
                        subscriber.onNext(lastVersion);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<Boolean> checkVersion(final UCheckerRequest request) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(request.checkVersion());
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
