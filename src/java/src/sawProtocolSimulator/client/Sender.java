package sawProtocolSimulator.client;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.network.UDPNetwork;
import sawProtocolSimulator.utilities.Log;
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
        // initialize udp server
        this.initializeUdpServer(this.configuration.getTransmitterPort());

        // take control of the channel
        this.sendTakeControlPacket();

        // total packets sent so far
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
                // set a timer only if we are not already waiting..no point invoking it again and
                // again
                if (!this.waitingForAcks)
                {
                    // set timer and after it's over, check for ACK's.
                    this.setTimerForACKs();

                    Log.d("Window Status: " + packetWindow.size()
                            + " packets left in the current window!");
                }
            }

            // windowSize number of more packets have been sent
            packetsSent += this.configuration.getWindowSize();

            Log.d("[SENDER] Sent Packets:      " + packetsSent);
            Log.d("[SENDER] Remaining Packets: "
                    + (this.configuration.getMaxPacketsToSend() - packetsSent));
        }

        // when all window packets sent, send EOT
        this.sendEndOfTransmissionPacket();
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

        Log.d(PacketUtilities.generateClientPacketLog(packet, true));

        // wait for SOT packet from receiver
        try
        {
            Packet receiverResponse = UDPNetwork.getPacket(this.listen);

            if (receiverResponse.getPacketType() == PacketUtilities.PACKET_START_OF_TRANSMISSION)
            {
                Log.d(PacketUtilities.generateClientPacketLog(packet, false));
            }

            // wait for 2 seconds before sending data packets.
            Thread.sleep(2000);
        }
        catch (ClassNotFoundException e)
        {
            Log.d(e.getMessage());
        }
        catch (IOException e)
        {
            Log.d(e.getMessage());
        }
        catch (InterruptedException e)
        {
            Log.d(e.getMessage());
        }
    }

    /**
     * Send the packet to end the transmission.
     */
    private void sendEndOfTransmissionPacket()
    {
        // create an EOT packet.
        Packet packet = this.makePacket(PacketUtilities.PACKET_END_OF_TRANSMISSION);

        // send the packet
        this.sendPacket(packet);

        Log.d(PacketUtilities.generateClientPacketLog(packet, true));
    }

    @Override
    protected Packet makePacket(int packetType)
    {
        return PacketUtilities.makePacket(this.configuration.getReceiverAddress().getHostAddress(),
                this.configuration.getReceiverPort(), this.configuration.getTransmitterAddress()
                        .getHostAddress(), this.configuration.getTransmitterPort(), packetType,
                this.sequenceNumber, this.sequenceNumber, this.configuration.getWindowSize());
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

            // send the packet
            this.sendPacket(packet);

            // increment the sequence number
            this.sequenceNumber++;
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
                    // can block for a maximum of 2 seconds
                    Sender.this.listen.setSoTimeout(2000);

                    /**
                     * Scan while packet window size isn't 0. If 0, all packets have been ACK'ed.
                     * AND Scan while the thread hasn't been interrupted.
                     */
                    while (Sender.this.packetWindow.size() != 0
                            && !Thread.currentThread().isInterrupted())
                    {
                        Packet packet = UDPNetwork.getPacket(Sender.this.listen);

                        if (packet.getPacketType() == PacketUtilities.PACKET_ACK)
                        {
                            Sender.this.removePacketFromWindow(packet.getAckNum());
                        }
                    }
                }
                catch (SocketException e)
                {
                    Log.d(e.getMessage());
                    System.exit(0); // fatal
                }
                catch (ClassNotFoundException e)
                {
                    Log.d(e.getMessage());
                }
                catch (IOException e)
                {
                    Log.d(e.getMessage());
                }
            }

        };

        this.ackReceiver = new Thread(taskRunnable);
        this.ackReceiver.start();
    }

    /**
     * Checks all packets in the current window and removes the one whose acknowledgement number is
     * equal to the ACK number of the received packet.
     * 
     * @param ackNum the acknowledgement number
     */
    private void removePacketFromWindow(int ackNum)
    {
        for (int i = 0; i < this.packetWindow.size(); i++)
        {
            if (this.packetWindow.get(i).getAckNum() == ackNum)
            {
                Log.d(PacketUtilities.generateClientPacketLog(packetWindow.get(i), false));

                this.packetWindow.remove(i);
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
