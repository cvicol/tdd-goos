package com.helkaerea.tdd;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class FakeAuctionServer {
    
    private final SingleMessageListener messageListener = new SingleMessageListener();
    private static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "auction";
    public static final String AUCTION_PASSWORD = "auction";
    public static final String XMPP_HOSTNAME = "localhost";
    
    private final String itemId;
    private final XMPPConnection connection;
    private Chat currentChat;
    
    /**
     * contructs teh fake auction server
     * 
     * @param itemId
     */
    public FakeAuctionServer(String itemId) {
        this.itemId = itemId;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
    }
    
    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(String.format(ITEM_ID_AS_LOGIN, itemId),
                AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(new ChatManagerListener() {
            
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                currentChat = chat;
                chat.addMessageListener(messageListener);
            }
        });
    }
    
    public String getItemId() {
        return this.itemId;
    }
    
    public void hasRecievedJoinRequestFromSniper() throws InterruptedException {
        messageListener.recieveMessage();
        
    }
    
    public void announceClose() throws XMPPException {
        currentChat.sendMessage(new Message());
        
    }
    
    public void stop() {
        connection.disconnect();
    }
    
}
