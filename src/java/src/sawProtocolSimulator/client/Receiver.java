package sawProtocolSimulator.client;

public class Receiver extends Client
{

    /**
     * Create a client whose sole purpose is to receive from the sender (transmitter).
     * 
     * @param clientMode the client mode.
     */
    public Receiver(ClientMode clientMode)
    {
        super(clientMode);
    }

    @Override
    public void run()
    {

    }

}
