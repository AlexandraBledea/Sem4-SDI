package client.tcp;

import common.Constants;
import common.Message;
import common.exceptions.ConnectionException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

@Component
public class TcpClient {
    public Message sendAndReceive(Message request) throws ConnectionException {
        try (var socket = new Socket(Constants.HOST, Constants.PORT);
             var is = socket.getInputStream();
             var os = socket.getOutputStream()) {

            System.out.println("sending request: " + request.getHeader());
            request.writeTo(os);
            System.out.println("request sent");

            Message response = new Message();
            response.readFrom(is);
            System.out.println("received response: " + response.getBody());

            return response;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new ConnectionException("Tried to connect to unknown host!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConnectionException("I/O error occurred while creating the socket!");
        }

    }
}
