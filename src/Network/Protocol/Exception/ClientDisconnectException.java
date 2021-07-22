package Network.Protocol.Exception;

/**
 * 
 */
public class ClientDisconnectException extends NetworkException {

    // member variables
    private int port;
    
    /**
     * Class constructor.
     * 
     * @param clientType
     * @param port
     */
    public ClientDisconnectException(int port, ConnectionTerminatedException terminationException){
        super("The connection to Client on port : " + port + " was terminated.", terminationException.getCause());
        this.port = port;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public int getPort(){
        return this.port;
    }
}
