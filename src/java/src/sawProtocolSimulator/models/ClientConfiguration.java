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

    /**
     * Get the address of the transmitter (sender).
     * 
     * @return the transmitterAddress
     */
    public InetAddress getTransmitterAddress()
    {
        return transmitterAddress;
    }

    /**
     * Set the address of the transmitter (sender).
     * 
     * @param transmitterAddress the transmitterAddress to set
     */
    public void setTransmitterAddress(InetAddress transmitterAddress)
    {
        this.transmitterAddress = transmitterAddress;
    }

    /**
     * Get the port number of the transmitter (sender).
     * 
     * @return the transmitterPort
     */
    public int getTransmitterPort()
    {
        return transmitterPort;
    }

    /**
     * Set the port number of the transmitter (sender).
     * 
     * @param transmitterPort the transmitterPort to set
     */
    public void setTransmitterPort(int transmitterPort)
    {
        this.transmitterPort = transmitterPort;
    }

    /**
     * Get the address of the receiver.
     * 
     * @return the receiverAddress
     */
    public InetAddress getReceiverAddress()
    {
        return receiverAddress;
    }

    /**
     * Set the address of the receiver.
     * 
     * @param receiverAddress the receiverAddress to set
     */
    public void setReceiverAddress(InetAddress receiverAddress)
    {
        this.receiverAddress = receiverAddress;
    }

    /**
     * Get the port number of the receiver.
     * 
     * @return the receiverPort
     */
    public int getReceiverPort()
    {
        return receiverPort;
    }

    /**
     * Set the port number of the receiver.
     * 
     * @param receiverPort the receiverPort to set
     */
    public void setReceiverPort(int receiverPort)
    {
        this.receiverPort = receiverPort;
    }

}
