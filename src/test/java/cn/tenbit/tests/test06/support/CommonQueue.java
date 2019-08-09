package cn.tenbit.tests.test06.support;

import cn.tenbit.hare.core.lite.function.HareExecutor;
import cn.tenbit.hare.core.lite.util.HareAssertUtils;
import cn.tenbit.hare.core.lite.util.HareInvokeUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author bangquan.qian
 * @Date 2019-07-26 16:58
 */
public final class CommonQueue<T> {

    private final Queue<T> queue = new ConcurrentLinkedQueue<>();

    private final Lock lock = new ReentrantLock();

    private final Condition cond = lock.newCondition();

    public CommonQueue() {
    }

    public boolean offer(T t) {
        HareAssertUtils.notNull(t);

        boolean result = queue.offer(t);
        if (result) {
            signalAll();
        }

        return result;
    }

    public T peek() {
        return queue.peek();
    }

    public T poll() {
        return queue.poll();
    }

    public T take() {
        while (true) {
            T t = poll();
            if (t == null) {
                await();
            }
            return t;
        }
    }

    private void signalAll() {
        invokeWithLock(() -> {
            cond.signalAll();
            return null;
        });
    }

    private void signal() {
        invokeWithLock(() -> {
            cond.signal();
            return null;
        });
    }

    private void await() {
        invokeWithLock(() -> {
            return null;
        });
    }

    private void await(long time, TimeUnit unit) {
        invokeWithLock(() -> {
            cond.await(time, unit);
            return null;
        });
    }

    private <T> T invokeWithLock(HareExecutor<T> f) {
        return HareInvokeUtils.invokeWithTurnRe(() -> {
            try {
                lock.lockInterruptibly();
                return f.execute();
            } finally {
                lock.unlock();
            }
        });
    }
}
