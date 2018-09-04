package lxpsee.lxpsee.otherlearn.javanet.interruptible;

import javax.swing.*;
import java.awt.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/2 08:42.
 */

public class InterruptibleSocketTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new InteruptibleSocketFrame();
                frame.setTitle("InterruptibleScoketTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
