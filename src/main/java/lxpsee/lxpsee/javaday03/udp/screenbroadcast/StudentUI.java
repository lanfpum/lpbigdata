package lxpsee.lxpsee.javaday03.udp.screenbroadcast;

import javax.swing.*;

/**
 * StudentUI
 */
public class StudentUI extends JFrame {

    private JLabel lblIcon;

    public StudentUI() {
        init();
    }

    private void init() {
        this.setTitle("学生端");
        this.setBounds(0, 0, 1366, 768);
        this.setLayout(null);

        //标签空间
        lblIcon = new JLabel();
        lblIcon.setBounds(0, 0, 1366, 768);

        //图标
        ImageIcon icon = new ImageIcon("F:\\bigdata\\arch\\1.jpg");
        lblIcon.setIcon(icon);
        this.add(lblIcon);
        this.setVisible(true);
    }

    /**
     * 更新图标
     */
    public void updateIcon(byte[] dataBytes) {
        ImageIcon icon = new ImageIcon(dataBytes);
        lblIcon.setIcon(icon);
    }
}