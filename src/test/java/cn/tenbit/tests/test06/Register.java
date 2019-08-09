package cn.tenbit.tests.test06;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @Author bangquan.qian
 * @Date 2019-07-26 14:03
 */
@Getter
@Builder
public class Register {

    private Broker broker;

    private List<Producer> producers;

    private List<Consumer> consumers;

    private Dispatcher dispatcher;

    private Printer printer;
}
