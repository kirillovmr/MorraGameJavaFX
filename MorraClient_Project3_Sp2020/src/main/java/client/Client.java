package client;

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

    public Client(Consumer<Serializable> call)
    {
        callback = call;
    }

    public void run()
    {
        try {
            socketClient= new Socket("localhost",5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
            System.out.println("lol");
        }
        catch(Exception e) {}

        while(true)
        {
            try {
                String message = in.readObject().toString();
                callback.accept(message);
            }
            catch(Exception e) {}
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
