package sawProtocolSimulator.client;

public class Receiver extends Client
{
    
    private int sequenceNumber;

    /**
     * Create a client whose sole purpose is to receive from the sender (transmitter).
     * 
     * @param clientMode the client mode.
     */
    public Receiver(ClientMode clientMode)
    {
        super(clientMode);
        this.sequenceNumber = 0;
    }

    @Override
    public void run()
    {

    }

}
