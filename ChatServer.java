package ru.gb.jtwo.chat.server.core;

import ru.gb.jtwo.network.ServerSocketThread;
import ru.gb.jtwo.network.ServerSocketThreadListener;
import ru.gb.jtwo.network.SocketThread;
import ru.gb.jtwo.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    ServerSocketThread server;
    private Vector<SocketThread> clients = new Vector<>();

    public void start(int port) {
        if (server != null && server.isAlive())
            putLog("Already running");
        else
            server = new ServerSocketThread(this, "Server", port, 2000);
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            putLog("Nothing to stop");
        } else {
            server.interrupt();
        }
    }

    private void putLog(String msg) {
        System.out.println(msg);
    }

    /**
     * Server Socket Thread Listener methods
     */

    @Override
    public void onServerStarted(ServerSocketThread thread) {
        putLog("Server thread started");
    }

    @Override
    public void onServerCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server socket started");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        //putLog("Server timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client connected");
        String name = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        SocketThread st = new SocketThread(this, name, socket);
        clients.add(st);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable throwable) {
        putLog("Server exception");
        throwable.printStackTrace();
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server thread stopped");
    }

    /**
     * Socket Thread Listener methods
     */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket started");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Socket stopped");
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket ready");
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        //thread.sendMessage("echo: " + msg);
        for (SocketThread st : clients) {
            st.sendMessage("echo: " + msg);
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Throwable throwable) {
        throwable.printStackTrace();
    }
}
