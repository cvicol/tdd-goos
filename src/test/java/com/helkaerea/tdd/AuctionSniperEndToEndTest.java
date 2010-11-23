package com.helkaerea.tdd;

import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();
    
    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws XMPPException,
            InterruptedException {
        auction.startSellingItem();// step1
        application.startBiddingIn(auction);// step 2
        auction.hasRecievedJoinRequestFromSniper();// step 3
        auction.announceClose();// step 4
        application.showsSniperHasLostAuction(); // step 5
    }
    
    @After
    public void stopAuction() {
        auction.stop();
    }
    
    @After
    public void stopApplication() {
        application.stop();
    }
}