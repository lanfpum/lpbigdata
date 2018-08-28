package lxpsee.lxpsee.javaday03.tcp.qq.client;

import top.lxpsee.javaday03.tcp.qq.common.MessageFactory;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QQClientUI extends JFrame {
    public Map<String, QQClientChatSingleUI> windows = new HashMap<String, QQClientChatSingleUI>();

    private ClientCommThread clientCommThread;

    //历史聊天区
    private JTextArea taHistory;

    //好友列表区
    private JList<String> lstFriends;

    //消息输入区
    private JTextArea taInputMessage;

    //发送按钮
    private JButton btnSend;

    //刷新好友列表按钮
    private JButton btnRefresh;

    public QQClientUI() {
        init();
        this.setVisible(true);
        /* 添加窗口关闭事件 */
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
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

        //lstFriends
        lstFriends = new JList<String>();
        lstFriends.setBounds(620, 0, 160, 400);
        this.add(lstFriends);

        /* 好友列表双击打开私聊窗口
         *       选中好友，判断是否已经打开与该好友私聊窗口，未打开新开私聊窗口，将通信线程，好友传入。并添加到窗口集合中
         */
        lstFriends.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String friend = lstFriends.getModel().getElementAt(lstFriends.getSelectedIndex());
                    if (windows.containsKey(friend)) {
                        windows.get(friend).setVisible(true);
                    } else {
                        QQClientChatSingleUI qqClientChatSingleUI = new QQClientChatSingleUI(clientCommThread, friend);
                        windows.put(friend, qqClientChatSingleUI);
                    }
                }
            }
        });

        //taInputMessage
        taInputMessage = new JTextArea();
        taInputMessage.setBounds(0, 420, 540, 160);
        this.add(taInputMessage);

        //btnSend
        btnSend = new JButton("发送");
        btnSend.setBounds(560, 420, 100, 160);
        this.add(btnSend);
        /* 添加发送按钮事件监听
         *      获取到输入区的内容
         *      进行判断，非空对
         *      最后，置空输入区
         */
        btnSend.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = taInputMessage.getText();

                if (!message.trim().equals("")) {
                    byte[] msgBytes = MessageFactory.popClientChatsMessage(message);
                    clientCommThread.sendMessage(msgBytes);
                }

                taInputMessage.setText("");
            }
        });

        //btnRefresh
        btnRefresh = new JButton("刷新");
        btnRefresh.setBounds(680, 420, 100, 160);
        this.add(btnRefresh);

        /* 刷新好友列表  */
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte[] msg = MessageFactory.popClientRefreshFriendsMessage();
                clientCommThread.sendMessage(msg);
            }
        });
    }

    /**
     * 添加消息到历史区
     */
    public void addMsgToHistory(String messageInfo, String senderInfo) {
        taHistory.append(senderInfo + " 说 ：\r\n");
        taHistory.append("  " + messageInfo);
        taHistory.append("\r\n");
        taHistory.append("\r\n");
    }

    /**
     * 刷新好友列表
     */
    public void refreshFriends(List<String> friendList) {
        ListModel<String> model = new DefaultListModel<String>();

        for (String s : friendList) {
            ((DefaultListModel<String>) model).addElement(s);
        }

        lstFriends.setModel(model);
    }

    public ClientCommThread getClientCommThread() {
        return clientCommThread;
    }

    public void setClientCommThread(ClientCommThread clientCommThread) {
        this.clientCommThread = clientCommThread;
    }
}