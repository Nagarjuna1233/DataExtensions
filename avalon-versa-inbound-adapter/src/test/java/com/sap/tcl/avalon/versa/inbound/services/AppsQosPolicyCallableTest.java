package com.sap.tcl.avalon.versa.inbound.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import com.sap.tcl.avalon.versa.inbound.constants.VersaIntegrationConstants;
import com.sap.tcl.avalon.versa.inbound.dtos.Template;
import com.sap.tcl.avalon.versa.inbound.pojos.VersaQueryObject;



public class AppsQosPolicyCallableTest {

    private static final Logger LOG = LoggerFactory.getLogger(AppsQosPolicyCallableTest.class);
    
    
    
    @Spy
	@InjectMocks
	private AppsQosPolicyCallable appsQosPolicyCallable;
	
	@Mock
	private VersaApi versaApi;
	@Mock
	MessageChannel rawFragmentMessageInputChannel;
	String appsQosPolicy = "{ \"app-qos-policy-group\": [ { \"name\": \"Ind_test_policy\", \"description\": \"testpolicy\", \"rules\": { \"app-qos-policy\": [ { \"name\": \"IND_Test\" }, { \"name\": \"Ind_test1\" } ] } }, { \"name\": \"Ind_test_policy111111\", \"description\": \"testpolicy11111\", \"rules\": { \"app-qos-policy\": [ { \"name\": \"IND_Test11111\" }, { \"name\": \"Ind_test111111\" } ] } } ] }";
	
	String appQosRules = "{ \"app-qos-policy\": [ { \"name\": \"IND_Test\", \"match\": { \"source\": { \"user\": {} }, \"destination\": {}, \"application\": { \"predefined-application-list\": [ \"EZTRAVEL\", \"IMEET\", \"STOCKQ\", \"ELTIEMPO\", \"GUDANGLAGU\", \"ZENMATE\", \"IMEEM\", \"EVONY\", \"AMAZON_MUSIC\" ], \"user-defined-application-list\": [ \"ujwal\" ] }, \"url-category\": { \"predefined\": [ \"personal_storage\", \"sports\", \"reference_and_research\", \"computer_and_internet_security\", \"parked_domains\", \"dead_sites\", \"illegal\", \"bot_nets\", \"dating\", \"abortion\" ], \"user-defined\": [ \"IndiaTest\" ] } }, \"set\": { \"qos-profile\": \"IND_1st\" } }, { \"name\": \"Ind_test1\", \"match\": { \"source\": { \"user\": {} }, \"destination\": {}, \"application\": { \"predefined-application-list\": [ \"20MIN\", \"VMTP\", \"UNASSIGNED_IP_PROT_201\", \"SCORECARDRESEARCH\", \"CENTRAL_TORRENT\", \"BBC\", \"SINA_FINANCE\", \"IMEEM\", \"EVONY\", \"INMOBI\" ] }, \"url-category\": { \"predefined\": [ \"auctions\", \"home_and_garden\", \"personal_storage\", \"social_network\" ], \"user-defined\": [ \"IndiaTest\" ] } }, \"set\": { \"qos-profile\": \"IND_2nd\" } } ] }";
	
	@Before
	public void setup(){		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAppsQosPolicyData(){
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(appsQosPolicy);
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			Mockito.doReturn(true).when(appsQosPolicyCallable)
			    .appsQosRulesData(Mockito.anyList());
			assertTrue(appsQosPolicyCallable.appsQosPolicyData(VesraTestDataUtils.getTemplates()));
			Mockito.verify(rawFragmentMessageInputChannel).send(Mockito.any(GenericMessage.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	@Test
	public void testAppsQosPolicyDataWrongApi(){
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(appQosRules);
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			Mockito.doReturn(true).when(appsQosPolicyCallable)
			    .appsQosRulesData(Mockito.anyList());
			assertFalse(appsQosPolicyCallable.appsQosPolicyData(VesraTestDataUtils.getTemplates()));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	
	@Test
	public void testAppsQosPolicyDataEmptyApi(){
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn("");
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			Mockito.doReturn(true).when(appsQosPolicyCallable)
			    .appsQosRulesData(Mockito.anyList());
			assertTrue(appsQosPolicyCallable.appsQosPolicyData(VesraTestDataUtils.getTemplates()));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	
	@Test
	public void testAppQosRulesData(){
		
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(appQosRules);
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			assertTrue(appsQosPolicyCallable.appsQosRulesData(VesraTestDataUtils.getVersaQueryData()));
			Mockito.verify(rawFragmentMessageInputChannel).send(Mockito.any(GenericMessage.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	
	@Test
	public void testAppQosRulesDataWrongApi(){
		
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(appsQosPolicy);
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			assertFalse(appsQosPolicyCallable.appsQosRulesData(VesraTestDataUtils.getVersaQueryData()));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	
	@Test
	public void testAppQosRulesDataEmptyApi(){
		
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn("");
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			assertTrue(appsQosPolicyCallable.appsQosRulesData(VesraTestDataUtils.getVersaQueryData()));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	@Test
    public void pulingFailedTest(){
        
        try {
            Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenThrow(new ResoucePullingFailedException("INTERFACE","url","Message"));
            Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
            assertTrue(appsQosPolicyCallable.appsQosRulesData(VesraTestDataUtils.getVersaQueryData()));
        } catch (Exception e) {
            LOG.error(VesraTestDataUtils.CAUSE,e);
        }
    }
	
	 @Test
	    public void channelFailedTest(){
	        
	        try {
	            Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(appsQosPolicy);
	            Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(false);
	            assertFalse(appsQosPolicyCallable.appsQosRulesData(VesraTestDataUtils.getVersaQueryData()));
	        } catch (Exception e) {
	            LOG.error(VesraTestDataUtils.CAUSE,e);
	        }
	    }
}
