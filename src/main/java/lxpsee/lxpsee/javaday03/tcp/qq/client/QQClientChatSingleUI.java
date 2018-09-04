package lxpsee.lxpsee.javaday03.tcp.qq.client;

import top.lxpsee.javaday03.tcp.qq.common.MessageFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/12 15:06.
 * <p>
 * 客户端私聊界面
 */

public class QQClientChatSingleUI extends JFrame implements ActionListener {

    private ClientCommThread clientCommThread;

    //历史聊天区
    private JTextArea taHistory;

    //消息输入区
    private JTextArea taInputMessage;

    //发送按钮
    private JButton btnSend;

    //接受者信息
    private String recvInfo;

    public QQClientChatSingleUI(ClientCommThread clientCommThread, String recvInfo) {
        this.clientCommThread = clientCommThread;
        this.recvInfo = recvInfo;
        init();
        this.setVisible(true);
    }

    /**
     * 初始化布局
     */
    private void init() {
        this.setTitle("QQClient");
        this.setBounds(100, 100, 800, 600);
        this.setLayout(null);

        //历史区
        taHistory = new JTextArea();
        taHistory.setBounds(0, 0, 600, 400);

        JScrollPane sp1 = new JScrollPane(taHistory);
        sp1.setBounds(0, 0, 600, 400);
        this.add(sp1);

        //taInputMessage
        taInputMessage = new JTextArea();
        taInputMessage.setBounds(0, 420, 540, 160);
        this.add(taInputMessage);

        //btnSend
        btnSend = new JButton("发送");
        btnSend.setBounds(560, 420, 100, 160);
        btnSend.addActionListener(this);
        this.add(btnSend);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //发送按钮
        if (source == btnSend) {
            String messageText = taInputMessage.getText();
            if (messageText != null && !messageText.equals("")) {
                //发送自己的聊天信息
                byte[] bytes = MessageFactory.popClientSingleChatMessage(recvInfo, messageText);
                clientCommThread.sendMessage(bytes);
                //清空消息框
                taInputMessage.setText("");
                //添加到历史区
                taHistory.append("我 说 : \r\n");
                taHistory.append("        " + messageText);
                taHistory.append("\r\n");
            }
        }
    }

    /**
     * 更新历史区域内容
     */
    public void updateHistory(String senderInfo, String msg) {
        taHistory.append(senderInfo + " 说:\r\n");
        taHistory.append("       " + msg);
        taHistory.append("\r\n");
    }


}
