package com.march.graduation.execute;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-22 时间: 下午1:24
// ********************************************
public class FutureHelper {

    private static final ListeningExecutorService LISTENABLE_FUTURE_Service = MoreExecutors
            .listeningDecorator(Executors.newFixedThreadPool(10));

    public static <T, F> void execute(final FutureHandler<T, F> futureHandler, final F... args) {

        ListenableFuture listenableFuture = LISTENABLE_FUTURE_Service.submit(new Callable<Object>() {
            @Override
            public T call() throws Exception {
                return futureHandler.handler(args);
            }
        });
        Futures.addCallback(listenableFuture, new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                futureHandler.onSuccess(result, args);
            }

            @Override
            public void onFailure(Throwable throwable) {
                futureHandler.onFailure(throwable, args);
            }
        });
    }

    public interface FutureHandler<E, F> {

        E handler(F... args);

        void onSuccess(E result, F... args);

        void onFailure(Throwable throwable, F... args);
    }
}
