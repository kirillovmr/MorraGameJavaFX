package client;

import core.Logic;
import core.MorraInfo;
import elements.UI;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread
{
    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;

    public Client(Consumer<Serializable> callback)
    {
        this.callback = callback;
    }

    public boolean connect(String ip, int port) {
        try {
            socketClient = new Socket(ip, port);
            return true;
        }
        catch (Exception e) {
            System.out.println("No connect");
            return false;
        }
    }

    public void run()
    {
        try {
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {
            System.out.println("client ex");
        }

        while(true)
        {
            try {
                Object data = in.readObject();
                callback.accept((Serializable) data);
            }
            catch(Exception e) {
                System.out.println("client read ex");
                break;
            }
        }

        Platform.runLater(() -> {
            Logic.logger.add("Disconnected from server");
            UI.gameToConnectScene();
        });
    }

    public void sendInfo(MorraInfo morraInfo)
    {
        try {
            out.writeObject(morraInfo);
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
    }

    public void send(String data)
    {
        try {
            out.writeObject(data);
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
    }
}

