package lxpsee.lxpsee.javaday05.gof.factory.abstractfactorypattern;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 16:24.
 */

public class TeslaBusinessCar implements TeslaCar {
    @Override
    public void charge() {
        System.out.println("不用给我特斯拉商务车冲满电");
    }
}
