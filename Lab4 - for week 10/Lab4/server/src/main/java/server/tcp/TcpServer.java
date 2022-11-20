package server.tcp;

import common.Message;

import java.util.function.UnaryOperator;

public interface TcpServer {

    void addHandler(String methodName, UnaryOperator<Message> handler);

    void startServer();
}
