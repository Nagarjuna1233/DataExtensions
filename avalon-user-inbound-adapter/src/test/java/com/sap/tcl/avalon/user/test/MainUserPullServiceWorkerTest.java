package com.sap.tcl.avalon.user.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sap.tcl.avalon.user.inbound.services.EventServiceWorker;
import com.sap.tcl.avalon.user.inbound.services.MainPullServiceWorker;
import com.sap.tcl.avalon.user.inbound.services.UserPullServiceWorker;

public class MainUserPullServiceWorkerTest {

    @Spy
    @InjectMocks
    private MainPullServiceWorker mainUserPullServiceWorker;
    @Mock
    private UserPullServiceWorker userPullServiceWorker;
    @Mock
    private EventServiceWorker eventServiceWorker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPull() {

        Mockito.doNothing().when(userPullServiceWorker).pull();

        Mockito.doNothing().when(eventServiceWorker).pullEvent();

        mainUserPullServiceWorker.userPull();
    }

}
