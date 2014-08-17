package com.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	private final static int PORT = 40123;
	private final static int MAX_PACKET_SIZE = 65507;
	private static boolean connected = true;
	
	public static void main (String[] args) throws IOException {
		
		
		//Try to bind socket to a port, could throw SecurityException or SocketException
		DatagramSocket socket = new DatagramSocket();
		
		
		//Buffer to hold packet data, Buffer set to max size of bytes for an UDP packet
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		
		/*Create Datagram packet, Constructor does not need address because this will be used
		*  to receive incoming packets.
		*/
		DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
		
		// Stores IP Adress of server, THIS will only work for localhost
		InetAddress serverAddress = InetAddress.getByName("localhost");
		
		while (connected){
			
			//Buffered reader for input text
			BufferedReader writeMessage = new BufferedReader(new InputStreamReader(System.in));
			
			//String to send read into string from console then placed in buffer
			String handledMessage = writeMessage.readLine();
			buffer = handledMessage.getBytes();
			
			DatagramPacket outPacket = new DatagramPacket(buffer , buffer.length , serverAddress , PORT);
			
			socket.send(outPacket);
			
			socket.receive(inPacket);
			String receivedMessage = "Message " + inPacket.getAddress() + " " + inPacket.getPort() + " " +
					new String(inPacket.getData() , 0 , inPacket.getLength());
			System.out.println(receivedMessage);
			

		}	
	}
}
