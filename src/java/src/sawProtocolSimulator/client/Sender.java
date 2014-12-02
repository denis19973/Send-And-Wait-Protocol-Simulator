package sawProtocolSimulator.client;

public class Sender extends Client
{

    /**
     * Create a client, whose sole purpose is to send (transmit) to the receiver.
     * 
     * @param clientMode the client mode.
     */
    public Sender(ClientMode clientMode)
    {
        super(clientMode);
    }

    @Override
    public void run()
    {

    }

}
