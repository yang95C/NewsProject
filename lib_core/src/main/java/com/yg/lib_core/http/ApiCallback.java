package com.yg.lib_core.http;

import io.reactivex.rxjava3.observers.DisposableObserver;

public abstract class ApiCallback<M> extends DisposableObserver<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFailure(e.getMessage());
        onFinish();
    }

    @Override
    public void onNext(M model) {
        onSuccess(model);
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
