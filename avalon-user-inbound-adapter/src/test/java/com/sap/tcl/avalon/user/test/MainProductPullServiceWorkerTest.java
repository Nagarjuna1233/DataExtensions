package com.sap.tcl.avalon.user.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sap.tcl.avalon.user.inbound.services.EventServiceWorker;
import com.sap.tcl.avalon.user.inbound.services.ProductPullServiceWorker;
import com.sap.tcl.avalon.user.inbound.services.MainProductPullServiceWorker;


public class MainProductPullServiceWorkerTest {
    
        @Spy
        @InjectMocks
        private MainProductPullServiceWorker mainProductPullServiceWorker;
        @Mock
        private ProductPullServiceWorker productPullServiceWorker;
        @Mock
        private EventServiceWorker eventServiceWorker;
        
        @Before
        public void setup() {
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testPull() {

            Mockito.doNothing().when(productPullServiceWorker).pull();

            Mockito.doNothing().when(eventServiceWorker).pullEvent();

            mainProductPullServiceWorker.productPull();
        }

    }