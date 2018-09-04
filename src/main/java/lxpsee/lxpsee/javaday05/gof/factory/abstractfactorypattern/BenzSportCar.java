package lxpsee.lxpsee.javaday05.gof.factory.abstractfactorypattern;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 16:22.
 */

public class BenzSportCar implements BenzCar {
    @Override
    public void gasUp() {
        System.out.println("给我的奔驰跑车加最好的汽油");
    }
}
