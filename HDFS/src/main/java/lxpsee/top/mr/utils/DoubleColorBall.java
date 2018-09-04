package lxpsee.top.mr.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/2 16:35.
 */
public class DoubleColorBall {
    public static void main(String[] args) {
        Set<Integer> redBalls = new HashSet<Integer>(6);

        for (int i = 0; i < 6; i++) {
            int redBall = (int) (Math.random() * 33 + 1);
            redBalls.add(redBall);
        }

        if (redBalls.size() < 6) {
            int redBall = (int) (Math.random() * 33 + 1);
            redBalls.add(redBall);
        }

        int blueBall = (int) (Math.random() * 16 + 1);
        ArrayList<Integer> redBallList = new ArrayList<Integer>(redBalls);
        Collections.sort(redBallList);
        int bei = (int) (Math.random() * 10 + 1);
        System.out.println("红球：" + redBallList + " 篮球" + blueBall + " + " + bei + " 倍");
    }
}
