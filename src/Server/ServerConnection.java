package Server;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import Token.RequestTokenizer;
import Token.Token;

/**
 * Represents a connection between a Server and a connecting object.
 * 
 * Is a Thread so that the requests coming to the Server can be handled on a new thread,
 * which allows for one Server to server multiple connecting objects.
 * 
 * When the Thread is run, the connection waits for a request and then passes this
 * request onto the underlying Server's request handler.
 */
public class ServerConnection extends Thread {
    
    // member variables
    private Server server;
    private Connection connection;
    private boolean isActive;

    /**
     * Class constructor.
     * 
     * @param server The Server object involved in the connection.
     * @param connection The conection between the Server and the connector.
     */
    public ServerConnection(Server server, Connection connection) throws Exception{
        this.server = server;
        this.connection = connection;
        this.isActive = true;
    }

    /**
     * Method run when thread started.
     */
    public void run(){
        // listening for future requests
        this.waitForRequest();
    }

    /**
     * Waits for an incoming request.
     */
    public void waitForRequest(){
        try{
            while(this.isActive()){
                // getting request from connnection
                String request = this.connection.getMessage();

                // tokenizing request
                Token requestToken = RequestTokenizer.getToken(request);

                // handling request
                this.server.getRequestHandler().handleRequest(this, requestToken); // TODO Does this need to be ran on a new Thread? Not doing it wont cause concurrency issues, but could be slow.
            }
        }
        catch(NullPointerException e){
            // Connector disconnected - passing it on to the server to handle
            this.server.handleDisconnect(this.getConnection().getSocket().getPort());
        }
        catch(Exception e){
            this.server.getServerInterface().handleError(this.server.getType().toString() + " on port : " + this.connection.getSocket().getLocalPort() + " unable to handle request from port : " + this.connection.getSocket().getPort());
        }
    }

    /**
     * Called to stop the connection for looking for futher requests.
     * 
     * i.e., It ends the connection.
     */
    public void close(){
        this.isActive = false;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////
    
    public Connection getConnection(){
        return this.connection;
    }

    public boolean isActive(){
        return this.isActive;
    }
}
