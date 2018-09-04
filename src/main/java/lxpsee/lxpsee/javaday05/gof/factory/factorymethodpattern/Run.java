package lxpsee.lxpsee.javaday05.gof.factory.factorymethodpattern;

import top.lxpsee.javaday05.gof.factory.base.Operation;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 16:10.
 */

public class Run {
    public static void main(String[] args) {
        Operation operation = new SubFactory().CreateOption();
        operation.setValue1(10);
        operation.setValue2(2);
        System.out.println(operation.getResult());
    }

}
