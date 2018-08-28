package lxpsee.lxpsee.otherlearn.javanet.interruptible;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/2 08:42.
 */
public class InteruptibleSocketFrame extends JFrame {
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLS = 60;

    private Scanner scanner;
    private JButton interuptibleButton;
    private JButton blockingButton;

    private JButton    cancelButton;
    private JTextArea  messages;
    private TestServer testServer;

    private Thread connectThread;

    public InteruptibleSocketFrame() {
        JPanel northPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        messages = new JTextArea();
        add(new JScrollPane(messages));

        interuptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking ");
        northPanel.add(interuptibleButton);
        northPanel.add(blockingButton);

        interuptibleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                interuptibleButton.setEnabled(false);
                blockingButton.setEnabled(false);
                cancelButton.setEnabled(true);

                connectThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            connectInterruptibly();
                        } catch (IOException e) {
                            messages.append("\n InterruptibleScoket.connectInterruptibly :" + e);
                        }
                    }
                });

                connectThread.start();
            }
        });

        blockingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interuptibleButton.setEnabled(false);
                blockingButton.setEnabled(false);
                cancelButton.setEnabled(true);

                connectThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            connectBlocking();
                        } catch (IOException e) {
                            messages.append("\n InterruptibleSocketTest.connectBlocking: " + e);
                        }
                    }
                });
                connectThread.start();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectThread.interrupt();
                cancelButton.setEnabled(false);
            }
        });

        testServer = new TestServer();
        new Thread(testServer).start();
        pack();
    }

    private void connectBlocking() throws IOException {
        messages.append("Blocking :\n");
        try (Socket socket = new Socket("localhost", 8189)) {
            scanner = new Scanner(socket.getInputStream());

            while (!Thread.currentThread().isInterrupted()) {
                messages.append("Reading ");

                if (scanner.hasNextLine()) {
                    messages.append(scanner.nextLine());
                    messages.append("\n");
                }
            }
        } finally {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    messages.append("Socket closed \n");
                    interuptibleButton.setEnabled(true);
                    blockingButton.setEnabled(true);
                }
            });
        }
    }


    private void connectInterruptibly() throws IOException {
        messages.append("Interruptible:\n");
        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8189))) {
            scanner = new Scanner(socketChannel);

            while (!Thread.currentThread().isInterrupted()) {
                messages.append("Reading ... ");

                if (scanner.hasNextLine()) {
                    messages.append(scanner.nextLine());
                    messages.append("\n");
                }
            }
        } finally {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    messages.append("Chanel closed \n");
                    interuptibleButton.setEnabled(true);
                    blockingButton.setEnabled(true);
                }
            });
        }
    }

    class TestServer implements Runnable {
        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8189);

                while (true) {
                    Socket socket = serverSocket.accept();
                    Runnable runnable = new TestServerHandler(socket);
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
            } catch (IOException e) {
                messages.append("\nTestServer.run :" + e);
            }
        }
    }

    class TestServerHandler implements Runnable {
        private Socket socket;
        private int    counter;

        public TestServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream, true /* autoFlush */);
                    while (counter < 100) {
                        counter++;
                        if (counter <= 100) {
                            printWriter.println(counter);
                        }
                        Thread.sleep(100);
                    }
                } finally {
                    socket.close();
                    messages.append("Closing server\n");
                }

            } catch (Exception e) {
                messages.append("\nTestServerHandler.run :" + e);
            }
        }
    }

}