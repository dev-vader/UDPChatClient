package com.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.json.JsonObject;

public class Client {
	
	private final static int PORT = 40123;				// Fixed unused UDP port 
	private final static int MAX_PACKET_SIZE = 65507;	// Max size of UDP packet
	private static boolean socketBound = true;			// Boolean condition to keep loop for in/outgoing packets
	private static String messageContents;				// String for message contents
	private static String sender;
	
	
	/*
	 * main method calls start client method
	 */
	public static void main (String[] args) throws IOException {
		
		startClient();
		
	}
	
	
	protected static void startClient() throws SocketException, IOException{
		
		//Buffer to hold packet data, Buffer set to max size of bytes for an UDP packet
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		
		//Try to bind socket to a port, could throw SecurityException or SocketException
		DatagramSocket socket = new DatagramSocket();
				
				
		/*
		 * Create datagram packet, constructor does not need address because this will be used
		 *  to receive incoming packets.
		 */
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				
		// Stores IP Adress of server, THIS will only work for localhost
		InetAddress serverAddress = InetAddress.getByName("localhost");
		

				/*
				 * While socket is bound keep receiving and sending packets, 
				 * once client wants to break from chat type "close" and 
				 * while loop breaks.
				 * 
				 */
	
				while(socketBound){
					
					//Buffered reader for text inputted to console.
					BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
					
					//String to send 
					messageContents = inputReader.readLine();
					if (messageContents.equals("close")){
						socketBound = false;
					}
					
					sender = serverAddress.getAddress().toString();

					/*
					 * Create message object and pass message contents over
					 * for encoding.
					 * 
					 */
					Message toJson = new Message();
					JsonObject messageToJson = toJson.encodeMessage();
					buffer = messageToJson.toString().getBytes();
					
					
					/*
					 * Create datagram packet for sending packets
					 * @params buffer, length of buffer , address to connect to, port to send to.
					 *  
					 */
					DatagramPacket sendPacket = new DatagramPacket(buffer , buffer.length , serverAddress , PORT);
					
					
					//receive and send packets via socket
					socket.send(sendPacket);
					socket.receive(receivePacket);
					
					// Print out and format received message
					String receivedMessage = "Message " + receivePacket.getAddress() + " " + receivePacket.getPort() + " " +
							new String(receivePacket.getData() , 0 , receivePacket.getLength());
					System.out.println(receivedMessage);
		 		}	
		
		// Close socket
		socket.close();
		System.out.println("SOCKET CLOSED");
				
	}
	
	protected static String getSender(){
		return sender;
	}
	
	protected static String getMessage(){
		return messageContents;
	}
}
