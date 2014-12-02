package sawProtocolSimulator.client;

import java.util.ArrayList;

import sawProtocolSimulator.models.Packet;

public class Sender extends Client
{

    /**
     * The current sequence number.
     * 
     * Initialized to 0 in the constructor.
     */
    private int sequenceNumber;
    
    /**
     * The current packet window.
     */
    private ArrayList<Packet> packetWindow;
    
    /**
     * Create a client, whose sole purpose is to send (transmit) to the receiver.
     * 
     * @param clientMode the client mode.
     */
    public Sender(ClientMode clientMode)
    {
        super(clientMode);
        this.sequenceNumber = 0;
        this.packetWindow = new ArrayList<Packet>();
    }

    @Override
    public void run()
    {

    }

}
