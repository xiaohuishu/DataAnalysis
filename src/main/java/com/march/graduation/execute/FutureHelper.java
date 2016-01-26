package com.march.graduation.execute;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

//********************************************
// * guava listenableFuture包装类
// * @author: xiaohui.shu
// * @version: 日期: 16-1-22 时间: 下午1:24
//********************************************
public class FutureHelper {

    private static final Logger logger = LoggerFactory.getLogger(FutureHelper.class);

    public static final ThreadLocal<ListenableFuture> futureThreadLocal = new ThreadLocal<ListenableFuture>();

    private static final AtomicInteger blockSize = new AtomicInteger(0);

    private static final Integer maxBlockSize = 10;

    private static final ListeningExecutorService LISTENING_EXECUTOR_SERVICE = MoreExecutors
            .listeningDecorator(Executors.newFixedThreadPool(10));

    public static <T, F> void execute(final FutureHandler<T, F> futureHandler, final F... args) {

        ListenableFuture listenableFuture = LISTENING_EXECUTOR_SERVICE.submit(new Callable<Object>() {
            @Override
            public T call() throws Exception {
                if(blockSize.get() == maxBlockSize) {
                    logger.error("block limit 10");
                    throw new FutureBlockException("block limit 10");
                }
                blockSize.incrementAndGet();
                return futureHandler.handler(args);
            }
        });
        futureThreadLocal.set(listenableFuture);
        Futures.addCallback(listenableFuture, new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                futureHandler.onSuccess(result, args);
                blockSize.decrementAndGet();
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
