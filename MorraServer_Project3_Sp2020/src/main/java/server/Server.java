package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server
{
    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<>();
    TheServer server;
    private Consumer<Serializable> callback;

    public Server(Consumer<Serializable> call)
    {
        callback = call;
        server = new TheServer();
        server.start();
    }


    public class TheServer extends Thread
    {
        public void run()
        {
            try(ServerSocket mySocket = new ServerSocket(5555))
            {
                System.out.println("Server is waiting for a client!");
                while(true)
                {
                    ClientThread c = new ClientThread(mySocket.accept(), count);
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();

                    count++;
                }
            }
            catch(Exception e)
            {
                callback.accept("Server socket did not launch");
            }
        }
    }

    class ClientThread extends Thread
    {
        int count;
        Socket connection;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count)
        {
            this.connection = s;
            this.count = count;
        }

        public void updateClients(String message)
        {
            for (ClientThread t : clients)
            {
                try
                {
                    t.out.writeObject(message);
                }
                catch (Exception ignored) { }
            }
        }

        public void run()
        {
            try
            {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e)
            {
                System.out.println("Streams not open");
            }

            updateClients("new client on server: client #"+count);

            while(true)
            {
                try
                {
                    String data = in.readObject().toString();
                    callback.accept("client: " + count + " sent: " + data);
                    updateClients("client #"+count+" said: "+data);

                }
                catch(Exception e)
                {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    updateClients("Client #"+count+" has left the server!");
                    clients.remove(this);
                    break;
                }
            }
        }
    }
}





