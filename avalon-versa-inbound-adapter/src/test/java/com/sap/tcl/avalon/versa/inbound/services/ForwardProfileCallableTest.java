package com.sap.tcl.avalon.versa.inbound.services;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.sap.tcl.avalon.exceptions.ResoucePullingFailedException;

public class ForwardProfileCallableTest {

    private static final Logger LOG = LoggerFactory.getLogger(ForwardProfileCallableTest.class);
    
	@Spy
	@InjectMocks
	private ForwardProfileCallable forwardProfileCallable;
	
	@Mock
	private VersaApi versaApi;
	@Mock
	MessageChannel rawFragmentMessageInputChannel;
	
	String forwadProfiless = "{ \"forwarding-profile\": [ { \"name\": \"IND_profile1\", \"description\": \"profile1\", \"sla-profile\": \"IND_testpackage1\", \"circuit-priorities\": { \"priority\": [ { \"value\": \"1\", \"description\": \"test1\", \"circuit-names\": { \"local\": [ \"WAN\" ], \"remote\": [ \"test1\" ] }, \"circuit-types\": { \"local\": [ \"Broadband\" ], \"remote\": [ \"Broadband\" ] }, \"circuit-media\": { \"local\": [ \"cable\" ], \"remote\": [ \"cable\" ] } } ], \"avoid\": {} }, \"connection-selection-method\": \"weighted-round-robin\", \"sla-violation-action\": \"forward\", \"evaluate-continuously\": \"enable\", \"recompute-timer\": \"300\", \"encryption\": \"always\", \"symmetric-forwarding\": \"enable\", \"replication\": {}, \"fec\": { \"sender\": {} }, \"load-balance\": \"per-flow\" }, { \"name\": \"IND_profile2\", \"description\": \"profile2\", \"sla-profile\": \"IND_testpackage2\", \"circuit-priorities\": { \"priority\": [ { \"value\": \"1\", \"description\": \"test2\", \"circuit-names\": { \"local\": [ \"WAN\" ], \"remote\": [ \"test2\" ] }, \"circuit-types\": { \"local\": [ \"Broadband\" ], \"remote\": [ \"Broadband\" ] }, \"circuit-media\": { \"local\": [ \"cable\" ], \"remote\": [ \"cable\" ] } } ], \"avoid\": {} }, \"connection-selection-method\": \"weighted-round-robin\", \"sla-violation-action\": \"forward\", \"evaluate-continuously\": \"disable\", \"recompute-timer\": \"300\", \"encryption\": \"Always\", \"symmetric-forwarding\": \"enable\", \"replication\": {}, \"fec\": { \"sender\": {} }, \"load-balance\": \"per-flow\" } ] }";
   
	@Before
	public void setup(){		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testForwardProfilesData(){
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(forwadProfiless);
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			Mockito.doReturn(true).when(forwardProfileCallable)
			    .postCircuitPriorityData(Mockito.anyString(),Mockito.any());
		
			assertTrue(forwardProfileCallable.forwardProfilesData(VesraTestDataUtils.getTemplates()));
			Mockito.verify(rawFragmentMessageInputChannel).send(Mockito.any(GenericMessage.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	@Test
	public void testEmptyVersaApi(){
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(null);
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			Mockito.doReturn(true).when(forwardProfileCallable)
			    .postCircuitPriorityData(Mockito.anyString(),Mockito.any());
		
			assertTrue(forwardProfileCallable.forwardProfilesData(VesraTestDataUtils.getTemplates()));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	
	@Test
	public void ForwardProfilesApiExceptionResponse(){
		try {
		   	Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenThrow(new ResoucePullingFailedException("INTERFACE","url","Message"));
		   	Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
		   	assertTrue(forwardProfileCallable.forwardProfilesData(VesraTestDataUtils.getTemplates()));
			Mockito.verify(versaApi).versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
}
