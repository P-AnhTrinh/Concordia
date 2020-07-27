//package comp346pa2w2020;

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
public class Comp346pa2driver {

    /** 
     * main class
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintStream ps = null;
        try{
            ps = new PrintStream (new File("output_semaphores.txt"));
            System.setOut(ps);
        }
        catch(FileNotFoundException e){
            System.out.println("File output was not found");
            System.exit(0);
        }
        
    	Network objNetwork = new Network( );            /* Activate the network */
        objNetwork.start();

        Server objServer1 = new Server("Thread1");
        objServer1.start();

        Server objServer2 = new Server("Thread2");
        objServer2.start();

        Client objClient1 = new Client("sending");          /* Start the sending client thread */
        objClient1.start();
        Client objClient2 = new Client("receiving");        /* Start the receiving client thread */
        objClient2.start();


      /*..............................................................................................................................................................*/
       
    }
    
 }
