import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ClientGUI extends JFrame implements ActionListener, KeyListener, WindowListener, Thread.UncaughtExceptionHandler {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    // panels to align components
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    // components to be held in scrolling panels
    private final JTextArea log = new JTextArea();
    private final JList<String> userList = new JList<>();
    // components to be held in top panel
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top", true);
    private final JTextField tfLogin = new JTextField("gleb");
    private final JPasswordField pfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");
    // components to be held in bottom panel
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    // flag if log is saved
    private boolean logSaved = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    public ClientGUI() {
        // doing window household
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat client");
        setAlwaysOnTop(true);
        // creating panels with scroll bars for log and users
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setEditable(false);
        JScrollPane scrLog = new JScrollPane(log);
        userList.setListData(new String[]{
                "user1", "user2", "user3", "user4",
                "user5", "user6", "user7", "user8", "user9",
                "user-with-exceptionally-long-name-in-this-chat"
        });
        JScrollPane scrUser = new JScrollPane(userList);
        scrUser.setPreferredSize(new Dimension(100, 0));
        // filling top panel with components
        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        cbAlwaysOnTop.addActionListener(this);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(pfPassword);
        panelTop.add(btnLogin);
        // filling bottom panel with components
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        tfMessage.addKeyListener(this);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        btnSend.addActionListener(this);
        panelBottom.add(btnSend, BorderLayout.EAST);
        // add panels with components to window
        add(panelTop, BorderLayout.NORTH);
        add(scrLog, BorderLayout.CENTER);
        add(scrUser, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        // set exception handling
        Thread.setDefaultUncaughtExceptionHandler(this);
        // adding window listener
        addWindowListener(this);
        // show window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend) {        // Отправлять сообщения в лог по нажатию кнопки
            moveText();
        } else if (src == btnDisconnect) {  // Пишу лог при отключении от сервера
            if (!logSaved) saveLog();
        } else {
            throw new RuntimeException("Unknown source " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = String.format("Exception in \"%s\" %s: %s\n\tat %s",
                t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    @Override
    public void keyPressed(KeyEvent e) {    // Отправлять сообщения в лог по нажатию клавиши Enter.
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moveText();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void moveText() {
        String msg = tfMessage.getText();
        tfMessage.setText("");
        log.append(msg + "\n");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    /**
     * Решил писать лог файл еще и при закрытии окна. Понимаю, что решение не оптимальное,
     * пытался прикрутить DocumentListener из swing.events и писать все изменения, происходящие
     * в log, но не смог разобраться где открывать, а гланое, где закрывать PrintWriter
     * Была еще мысль сохранять содержимое JTextArea по таймеру, каждую, скажем минуту.
     * Подумал бы еще, но нужно идти на дежурство
     * С нетерапением жду вебинара, чтобы узнать как писать в файлы из приложения с GUI
     * не по кнопке "Save"
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if (!logSaved) saveLog();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    private void saveLog() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("log.txt", true))) {
            String text = log.getText();
            pw.println(text);
            logSaved = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Chat is not saved", JOptionPane.ERROR_MESSAGE);
        }
    }
}
