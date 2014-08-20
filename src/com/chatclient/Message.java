package com.chatclient;

import javax.json.Json;
import javax.json.JsonObject;

public class Message extends Client {

	
	private String messageContents = Client.getMessage();				//String for contents of message
	private String author = Client.getSender();							//String for author of message
	
	
	/*
	 * @method encodeMessage - encodes message to json.
	 * @return jsonMessage.
	 */
	public JsonObject encodeMessage(){
	JsonObject jsonMessage = Json.createObjectBuilder()
			.add("Sender", author)
			.add("Receipent" , "unused")
			.add("Message" , messageContents)
			.build();
	return jsonMessage;
	}
	
	
}
