package ru.gb.jtwo.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class SocketThread extends Thread {

    private Socket socket;
    private SocketThreadListener listener;
    private DataOutputStream out;
    // перенес в поле метода, чтобы закрывать при закрытии сокета
    private DataInputStream in;

    public SocketThread(SocketThreadListener listener, String name, Socket socket) {
        super(name);
        this.socket = socket;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        try {
            listener.onSocketStart(this, socket);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            listener.onSocketReady(this, socket);
            while (!isInterrupted()) {
                try {
                    String msg = in.readUTF();
                    listener.onReceiveString(this, socket, msg);
                } catch (EOFException eofx) {
                    // похоже это исключение можно игнорировать,
                    // если я правильно понял документацию оракула
                    // но это не помогает корректно закрыть InputStream
                }
            }
        } catch (IOException e) {
            listener.onSocketException(this, e);
        } finally {
            close();
            listener.onSocketStop(this);
        }
    }

    public synchronized boolean sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketException(this, e);
            close();
            return false;
        }
    }

    public synchronized void close() {
        try {
            out.close();
        } catch (IOException e) {
            listener.onSocketException(this, e);
        }
        interrupt();
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            listener.onSocketException(this, e);
        }
    }

}
