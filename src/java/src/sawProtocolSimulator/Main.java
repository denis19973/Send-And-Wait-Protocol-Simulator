package sawProtocolSimulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;

import sawProtocolSimulator.client.ClientMode;
import sawProtocolSimulator.client.Receiver;
import sawProtocolSimulator.client.Sender;
import sawProtocolSimulator.exceptions.CouldNotReadConfigurationException;
import sawProtocolSimulator.models.ClientConfiguration;
import sawProtocolSimulator.models.NetworkConfiguration;
import sawProtocolSimulator.networkEmulator.Network;
import sawProtocolSimulator.utilities.Log;

public class Main
{

    /**
     * The main entry point of the entire application.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        // Ask user what this current instance will be: sender, receiver or network module.
        System.out.println("Welcome to the Send And Wait Protocol Simulator (Java Version)..!");
        System.out.println("You can be one of the following: ");
        System.out.println("1) Enter 1 to be a Sender");
        System.out.println("2) Enter 2 to be a Receiver");
        System.out.println("3) Enter 3 to be the Network Emulator");
        System.out.print("What would you like the current instance of the program to be (1-3):\t");

        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        scan.close();

        switch (choice)
        {
            case 1:
                // be the sender
                Sender sender = new Sender(ClientMode.SENDER);

                try
                {
                    // scan configuration
                    sender.setConfiguration(loadClientConfiguration());
                }
                catch (CouldNotReadConfigurationException e)
                {
                    Log.d(e.getMessage());

                    // critical thing failed, close the program.
                    System.exit(0);
                }

                // print all loaded configuration
                sender.printConfiguration();

                Log.d("You are now a SENDER!");
                
                // start
                sender.run();

                break;
            case 2:
                // be the receiver
                Receiver receiver = new Receiver(ClientMode.RECEIVER);

                try
                {
                    // scan configuration
                    receiver.setConfiguration(loadClientConfiguration());
                }
                catch (CouldNotReadConfigurationException e)
                {
                    Log.d(e.getMessage());

                    // critical thing failed, close the program.
                    System.exit(0);
                }

                // print all loaded configuration
                receiver.printConfiguration();
                
                Log.d("You are now a RECEIVER!");

                // start
                receiver.run();

                break;
            case 3:
                // be the network emulator
                createNetworkModule();

                break;
            default:
                Log.d("You didn't enter a valid mode option. Now exiting!");

                System.exit(0);
        }

        return;
    }

    /**
     * Creates and executes the network emulator.
     */
    public static void createNetworkModule()
    {
        // If network module, read the network configuration and create the object.
        try
        {
            NetworkConfiguration configuration = loadNetworkConfiguration();
            Network networkModule = new Network(configuration);

            networkModule.printConfiguration();

            Log.d("You are now the Network Module!");
            
            // run network module
            networkModule.run();
        }
        catch (CouldNotReadConfigurationException e)
        {
            Log.d(e.getMessage());

            // critical thing failed, close the program.
            System.exit(0);
        }
    }

    /**
     * Parse the client configuration file and fill up the ClientConfiguration model with it.
     * 
     * @return Configuration the configuration parsed from the config file; null if nothing read.
     * @throws CouldNotReadConfigurationException if the file cannot be parsed.
     */
    public static ClientConfiguration loadClientConfiguration()
            throws CouldNotReadConfigurationException
    {
        Properties clientProperties = new Properties();
        InputStream fileInputStream = null;
        ClientConfiguration configuration = new ClientConfiguration();

        try
        {
            // create a stream to the properties file
            fileInputStream = new FileInputStream("ClientConfiguration.properties");

            // load the configuration file
            clientProperties.load(fileInputStream);

            configuration.setNetworkAddress(InetAddress.getByName(clientProperties
                    .getProperty("networkAddress")));
            configuration.setNetworkPort(Integer.parseInt(clientProperties
                    .getProperty("networkPort")));
            configuration.setTransmitterAddress(InetAddress.getByName(clientProperties
                    .getProperty("transmitterAddress")));
            configuration.setTransmitterPort(Integer.parseInt(clientProperties
                    .getProperty("transmitterPort")));
            configuration.setReceiverAddress(InetAddress.getByName(clientProperties
                    .getProperty("receiverAddress")));
            configuration.setReceiverPort(Integer.parseInt(clientProperties
                    .getProperty("receiverPort")));
            configuration
                    .setWindowSize(Integer.parseInt(clientProperties.getProperty("windowSize")));
            configuration.setMaxPacketsToSend(Integer.parseInt(clientProperties
                    .getProperty("maxPackets")));
            configuration
                    .setMaxTimeout(Integer.parseInt(clientProperties.getProperty("maxTimeout")));
        }
        catch (FileNotFoundException e)
        {
            throw new CouldNotReadConfigurationException("Couldn't read the configuration file.");
        }
        catch (UnknownHostException e)
        {
            throw new CouldNotReadConfigurationException(e.getMessage());
        }
        catch (IOException e)
        {
            throw new CouldNotReadConfigurationException("Couldn't load the configuration file.");
        }
        finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException e)
                {
                    throw new CouldNotReadConfigurationException(
                            "Couldn't close the configuration file.");
                }
            }
        }

        return configuration;
    }

    /**
     * Parse the network configuration file and fill up the NetworkConfiguration model with it.
     * 
     * @return Configuration the configuration parsed from the config file; null if nothing read.
     * @throws CouldNotReadConfigurationException if the file cannot be parsed.
     */
    public static NetworkConfiguration loadNetworkConfiguration()
            throws CouldNotReadConfigurationException
    {
        Properties networkProperties = new Properties();
        InputStream fileInputStream = null;
        NetworkConfiguration configuration = new NetworkConfiguration();

        try
        {
            // create a stream to the properties file
            fileInputStream = new FileInputStream("NetworkConfiguration.properties");

            // load the configuration file.
            networkProperties.load(fileInputStream);

            // set configuration properties
            configuration.setReceiver(InetAddress.getByName(networkProperties
                    .getProperty("receiverAddress")));
            configuration.setReceiverPort(Integer.parseInt(networkProperties
                    .getProperty("receiverPort")));
            configuration.setSender(InetAddress.getByName(networkProperties
                    .getProperty("senderAddress")));
            configuration.setSenderPort(Integer.parseInt(networkProperties
                    .getProperty("senderPort")));
            configuration.setDropRate(Integer.parseInt(networkProperties.getProperty("dropRate")));
            configuration.setAverageDelayPerPacket(Integer.parseInt(networkProperties
                    .getProperty("averageDelayPerPacket")));

        }
        catch (FileNotFoundException e)
        {
            throw new CouldNotReadConfigurationException("Couldn't read the configuration file.");
        }
        catch (UnknownHostException e)
        {
            throw new CouldNotReadConfigurationException(e.getMessage());
        }
        catch (IOException e)
        {
            throw new CouldNotReadConfigurationException("Couldn't load the configuration file.");
        }
        finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();

                    throw new CouldNotReadConfigurationException(
                            "Couldn't close the configuration file.");
                }
            }
        }

        return configuration;
    }

}
