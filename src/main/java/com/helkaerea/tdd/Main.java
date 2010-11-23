package com.helkaerea.tdd;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main {
    
    /**
     * the indices of the arguments array
     */
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    
    /**
     * We need to connect to some auction
     */
    private static final String AUCTION_RESOURCE = "auction";
    private static final String ITEM_ID_AS_LOGIN = "auction-%s";
    private static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
            + AUCTION_RESOURCE;
    
    public static final String MAIN_WINDOW_NAME = "TEST NAME";
    public static final String SNIPER_STATUS_NAME = "sniper status";
    
    private MainWindow ui;
    private Chat notGB;
    public Main() throws Exception {
        startUserInterface();
    }
    
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.joinAuction(connectTo(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]), args[ARG_ITEM_ID]);
    }
    
    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        
        Chat chat = connection.getChatManager().createChat(
                auctionId(itemId, connection),
                new MessageListener() {
                    
                    @Override
                    public void processMessage(Chat aChat, Message aMessage) {
                        SwingUtilities.invokeLater(new Runnable() {
                            
                            @Override
                            public void run() {
                               ui.showStatus(MainWindow.STATUS_LOST);
                            }
                        });
                    }
                });
        this.notGB = chat;
        chat.sendMessage(new Message());
    }

    
    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }
    
    private static XMPPConnection connectTo(String hostName, String userName,
            String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostName);
        connection.connect();
        connection.login(userName, password, AUCTION_RESOURCE);
        return connection;
    }
    
    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                ui = new MainWindow();
            }
        });
    }
}
