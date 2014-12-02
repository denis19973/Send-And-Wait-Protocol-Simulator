package sawProtocolSimulator.client;

import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.utilities.PacketUtilities;

public class Receiver extends Client
{
    /**
     * Current sequence number.
     * 
     * Initialized to 0 in the constructor.
     */
    private int currentSequenceNumber;

    /**
     * Create a client whose sole purpose is to receive from the sender (transmitter).
     * 
     * @param clientMode the client mode.
     */
    public Receiver(ClientMode clientMode)
    {
        super(clientMode);
        this.currentSequenceNumber = 0;
    }

    @Override
    public void run()
    {
        // listen for SOT

        // scan each packet

        // send an ack for each packet

        // if a packet arrives again, send ack again

        // listen for EOT
    }

    @Override
    protected Packet makePacket(int packetType)
    {
        return PacketUtilities.makePacket(this.configuration.getTransmitterAddress()
                .getHostAddress(), this.configuration.getTransmitterPort(), this.configuration
                .getReceiverAddress().getHostAddress(), this.configuration.getReceiverPort(),
                packetType, this.currentSequenceNumber, this.currentSequenceNumber,
                this.configuration.getWindowSize());

    }

}
