package sawProtocolSimulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;

import sawProtocolSimulator.exceptions.CouldNotReadConfigurationException;
import sawProtocolSimulator.models.NetworkConfiguration;
import sawProtocolSimulator.networkEmulator.Network;

public class Main
{

    /**
     * The main entry point of the entire application.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        //Ask user what this current instance will be: sender, receiver or network module.
        System.out.println("Welcome to the Send And Wait Protocol Simulator (Java Version)..!");
        System.out.println("You can be one of the following: ");
        System.out.println("1) Enter 1 to be a Sender");
        System.out.println("2) Enter 2 to be a Receiver");
        System.out.println("3) Enter 3 to be the Network Emulator");
        System.out.print("What would you like the current instance of the program to be (1-3):\t");
        
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        
        switch(choice)
        {
            case 1:
                //be the sender
                
                break;
            case 2:
                //be the receiver

                break;
            case 3:
                //be the network emulator
                createNetworkModule();
                
                break;
            default:
                System.out.println("You didn't enter a valid option. Now exiting!");
                
                //LOG
                System.exit(0);
        }
        
        return;
    }
    
    /**
     * Creates and executes the network emulator.
     */
    public static void createNetworkModule()
    {
        //If network module, read the network configuration and create the object.
        try
        {
            NetworkConfiguration configuration = loadNetworkConfiguration();
            Network networkModule = new Network(configuration);
            
            networkModule.takeInput();
            networkModule.printConfiguration();
            
            //run network module
        }
        catch (CouldNotReadConfigurationException e)
        {
            //LOG
            
            //critical thing failed, close the program.
            System.exit(0);
        }
    }

    /**
     * Parse the network configuration file and fill up the Configuration model with it.
     * 
     * @return Configuration the configuration parsed from the config file; null if nothing read.
     * @throws CouldNotReadConfigurationException if the file cannot be parsed.
     */
    public static NetworkConfiguration loadNetworkConfiguration() throws CouldNotReadConfigurationException
    {
        Properties networkProperties = new Properties();
        InputStream fileInputStream = null;
        NetworkConfiguration configuration = new NetworkConfiguration();
        
        try
        {
            //create a stream to the properties file
            fileInputStream = new FileInputStream("NetworkConfiguration.properties");
            
            //load the configuration file.
            networkProperties.load(fileInputStream);
            
            //set configuration properties
            configuration.setReceiver(InetAddress.getByName(networkProperties.getProperty("receiverAddress")));
            configuration.setReceiverPort(Integer.parseInt(networkProperties.getProperty("receiverPort")));
            configuration.setSender(InetAddress.getByName(networkProperties.getProperty("senderAddress")));
            configuration.setSenderPort(Integer.parseInt(networkProperties.getProperty("senderPort")));
            
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            
            throw new CouldNotReadConfigurationException("Couldn't read the configuration file.");
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
            e.getCause(); //this is the cause
            e.getMessage(); //this is the message
            
            //network module isn't reachable. throw this.
        }
        catch (IOException e)
        {
            e.printStackTrace();

            throw new CouldNotReadConfigurationException("Couldn't load the configuration file.");
        }
        finally
        {
            if(fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();

                    throw new CouldNotReadConfigurationException("Couldn't close the configuration file.");
                }
            }
        }
        
        return configuration;
    }

}
