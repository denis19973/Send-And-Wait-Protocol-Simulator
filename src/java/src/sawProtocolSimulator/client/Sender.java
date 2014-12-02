package sawProtocolSimulator.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.network.UDPNetwork;
import sawProtocolSimulator.utilities.PacketUtilities;

public class Sender extends Client
{

    /**
     * The current sequence number.
     * 
     * Initialized to 1 in the constructor.
     */
    private int               sequenceNumber;

    /**
     * The current packet window.
     */
    private ArrayList<Packet> packetWindow;

    /**
     * The timer for ACK's.
     */
    private Timer             timer;

    /**
     * Thread to wait for ack's.
     */
    private Thread            ackReceiver;

    /**
     * Boolean switch to wait for Ack's.
     */
    private boolean           waitingForAcks;

    /**
     * Create a client, whose sole purpose is to send (transmit) to the receiver.
     * 
     * @param clientMode the client mode.
     */
    public Sender(ClientMode clientMode)
    {
        super(clientMode);
        this.sequenceNumber = 1;
        this.packetWindow = new ArrayList<Packet>();
        this.timer = new Timer();
    }

    @Override
    public void run()
    {
        // take control of the channel
        this.sendTakeControlPacket();

        //total packets sent so far
        int packetsSent = 0;
        
        // once, all ack's arrive, empty window, and move onto the next window
        while (packetsSent < this.configuration.getMaxPacketsToSend())
        {
            // generate packets for a window and send
            this.generateWindowAndSend();

            // we are now waiting for ack's.
            this.waitingForAcks = true;

            // wait for ack's for each packet
            while (!this.packetWindow.isEmpty())
            {
                // set a timer only if we are not already waiting..no point invoking it again and again
                if (!this.waitingForAcks)
                {
                    // set timer and after it's over, check for ACK's.
                    this.setTimerForACKs();
                }
                
                //TODO: print packet window status.
            }
            
            //windowSize number of packets have been sent
            packetsSent += this.configuration.getWindowSize();
            //TODO: print packets sent so far
        }

        // when all window packets sent, send EOT
        
    }

    /**
     * Send the packet to take control of the communication channel.
     */
    private void sendTakeControlPacket()
    {
        // create a SOT packet
        Packet packet = this.makePacket(PacketUtilities.PACKET_START_OF_TRANSMISSION);

        // send the packet
        this.sendPacket(packet);
    }
    
    /**
     * Send the packet to end the transmission.
     */
    private void sentEndOfTransmissionPacket()
    {
        //create an EOT packet.
        Packet packet = this.makePacket(PacketUtilities.PACKET_END_OF_TRANSMISSION);
        
        //send the packet
        this.sendPacket(packet);
    }

    /**
     * Creates a packet from the configuration, sequence number, packet type and other details.
     * 
     * @param packetType the type of packet to make
     * 
     * @return a Packet
     */
    private Packet makePacket(int packetType)
    {
        return PacketUtilities.makePacket(this.configuration.getReceiverAddress().getHostAddress(),
                this.configuration.getReceiverPort(), this.configuration.getTransmitterAddress()
                        .getHostAddress(), this.configuration.getTransmitterPort(), packetType,
                this.sequenceNumber, this.sequenceNumber, this.configuration.getWindowSize());

    }

    /**
     * Send a packet to the network emulator.
     * 
     * @param packet the packet to send.
     */
    private void sendPacket(Packet packet)
    {
        try
        {
            DatagramSocket socket = UDPNetwork.createSocket();

            // send packet to the network emulator
            UDPNetwork.sendPacket(socket, packet, this.configuration.getNetworkAddress(),
                    this.configuration.getNetworkPort());
            
            //TODO: print this packet sent
        }
        catch (SocketException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Generate packets for a full window.
     */
    private void generateWindowAndSend()
    {
        for (int i = 1; i <= this.configuration.getWindowSize(); i++)
        {
            // craft a data packet
            Packet packet = this.makePacket(PacketUtilities.PACKET_DATA);

            // add it to the window
            this.packetWindow.add(packet);

            // increment the sequence number
            this.sequenceNumber++;

            // send the packet
            this.sendPacket(packet);
        }
    }

    /**
     * Wait for ACK's for the packets sent in the window.
     * 
     * If an ACK isn't received within the timer, re-send packet.
     */
    private void ackTimeout()
    {
        this.stopTimerAndAckReceiverThread();

        // if packet window isn't empty, send all those packets again, and wait for ack's.
        if (!this.packetWindow.isEmpty())
        {
            this.waitingForAcks = true;

            for (int i = 0; i < this.packetWindow.size(); i++)
            {
                Packet packet = this.packetWindow.get(i);
                this.sendPacket(packet);
            }

            this.setTimerForACKs();
        }

    }

    /**
     * Set timer and wait for ACKs.
     */
    private void setTimerForACKs()
    {
        this.timer.schedule(new TimerTask() {

            @Override
            public void run()
            {
                // call ackTimeout and check which packets have been ACK'ed.
                Sender.this.ackTimeout();
            }

        }, this.configuration.getMaxTimeout());

        // receive ack's in the meantime
        this.receiveACKs();
    }

    /**
     * Wait for ACKs.
     */
    private void receiveACKs()
    {
        Runnable taskRunnable = new Runnable() {

            @Override
            public void run()
            {
                try
                {
                    DatagramSocket listen =
                            UDPNetwork.createServer(Sender.this.configuration.getTransmitterPort());

                    // can block for a maximum of 2 seconds
                    listen.setSoTimeout(2000);

                    // scan while packet window size isn't 0. If 0, all packets have been acked.
                    // scan while the thread hasn't been interrupted
                    while (Sender.this.packetWindow.size() != 0
                            || !Thread.currentThread().isInterrupted())
                    {
                        Packet packet = UDPNetwork.getPacket(listen);

                        if (packet.getPacketType() == PacketUtilities.PACKET_ACK)
                        {
                            Sender.this.removePacketFromWindow(packet.getAckNum());
                        }
                    }
                }
                catch (SocketException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        this.ackReceiver = new Thread(taskRunnable);
        this.ackReceiver.start();
    }

    /**
     * Checks all packets in the current window and removes the one whose acknowledgement number is
     * equal to the ack number of the received packet.
     * 
     * @param ackNum the acknowledgement number
     */
    private void removePacketFromWindow(int ackNum)
    {
        for (int i = 0; i < this.packetWindow.size(); i++)
        {
            if (this.packetWindow.get(i).getAckNum() == ackNum)
            {
                this.packetWindow.remove(i);

                // TODO: print THIS PACKET ACKED
            }
        }
    }

    /**
     * Stop the timer.
     */
    private void stopTimerAndAckReceiverThread()
    {
        this.timer.cancel();
        this.timer.purge();

        // not waiting for ack's now.
        this.waitingForAcks = false;

        this.ackReceiver.interrupt();
    }

}
