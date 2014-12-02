package sawProtocolSimulator.models;

import java.net.InetAddress;

public class ClientConfiguration
{
    /**
     * Address of the network emulator.
     */
    private InetAddress networkAddress;

    /**
     * Port number, the network emulator is listening on.
     */
    private int         networkPort;

    /**
     * Address of the transmitter (sender).
     */
    private InetAddress transmitterAddress;

    /**
     * Port number of the transmitter (sender).
     */
    private int         transmitterPort;

    /**
     * Address of the receiver.
     */
    private InetAddress receiverAddress;

    /**
     * Port number of the receiver.
     */
    private int         receiverPort;

    /**
     * Default constructor.
     */
    public ClientConfiguration()
    {}

    /**
     * Creates a Client Configuration Object.
     * 
     * @param networkAddress the address of the network emulator
     * @param networkPort the port number of the network emulator
     * @param transmitterAddress the address of the transmitter (sender)
     * @param transmitterPort the port number of the transmitter (sender)
     * @param receiverAddress the address of the receiver
     * @param receiverPort the port number of the receiver
     */
    public ClientConfiguration(InetAddress networkAddress, int networkPort,
            InetAddress transmitterAddress, int transmitterPort, InetAddress receiverAddress,
            int receiverPort)
    {
        this.networkAddress = networkAddress;
        this.networkPort = networkPort;
        this.transmitterAddress = transmitterAddress;
        this.transmitterPort = transmitterPort;
        this.receiverAddress = receiverAddress;
        this.receiverPort = receiverPort;
    }



    /**
     * Get the Address of the network emulator.
     * 
     * @return the networkAddress
     */
    public InetAddress getNetworkAddress()
    {
        return networkAddress;
    }

    /**
     * Set the address of the network emulator.
     * 
     * @param networkAddress the networkAddress to set
     */
    public void setNetworkAddress(InetAddress networkAddress)
    {
        this.networkAddress = networkAddress;
    }

    /**
     * Get the port of the network emulator.
     * 
     * @return the networkPort
     */
    public int getNetworkPort()
    {
        return networkPort;
    }

    /**
     * Set the port of the network emulator.
     * 
     * @param networkPort the networkPort to set
     */
    public void setNetworkPort(int networkPort)
    {
        this.networkPort = networkPort;
    }


}
