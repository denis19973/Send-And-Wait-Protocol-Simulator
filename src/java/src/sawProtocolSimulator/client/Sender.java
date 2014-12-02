package sawProtocolSimulator.client;

public class Sender extends Client
{

    /**
     * The current sequence number.
     * 
     * Initialized to 0 in the constructor.
     */
    private int sequenceNumber;
    
    /**
     * Create a client, whose sole purpose is to send (transmit) to the receiver.
     * 
     * @param clientMode the client mode.
     */
    public Sender(ClientMode clientMode)
    {
        super(clientMode);
        this.sequenceNumber = 0;
    }

    @Override
    public void run()
    {

    }

}
