//package pa1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
import java.io.*;
public class driver {

    /** 
     * main class
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
    	
    	 /*******************************************************************************************************************************************
    	  * TODO : implement all the operations of main class   																					*
    	  ******************************************************************************************************************************************/

        PrintStream ps = null;
        try{
            ps = new PrintStream (new File("output3.txt"));
            System.setOut(ps);
        }
        catch(FileNotFoundException e){
            System.out.println("File output was not found");
            System.exit(0);
        }


    	Network objNetwork = new Network("network");            /* Activate the network */
        objNetwork.start();
        Server objServer = new Server();                        /* Start the server */ 
        objServer.start();
        Client objClient1 = new Client("sending");              /* Start the sending client */
        objClient1.start();
        Client objClient2 = new Client("receiving");            /* Start the receiving client */
        objClient2.start();


    }
}
