package cn.tenbit.tests.test06;

import cn.tenbit.tests.test06.support.CommonQueue;

/**
 * @Author bangquan.qian
 * @Date 2019-07-26 16:58
 */
public class Broker<T> {

    private CommonQueue<T> queue = new CommonQueue<>();

    private boolean submit(T msg) {
        return queue.offer(msg);
    }

    private T acquire() {
        return queue.take();
    }

}
