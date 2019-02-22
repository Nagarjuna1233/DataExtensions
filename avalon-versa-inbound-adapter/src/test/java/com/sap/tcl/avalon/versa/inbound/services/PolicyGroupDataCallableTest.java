package com.sap.tcl.avalon.versa.inbound.services;

import static org.junit.Assert.assertFalse;
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


public class PolicyGroupDataCallableTest {
	
    private static final Logger LOG = LoggerFactory.getLogger(PolicyGroupDataCallableTest.class);
    
	@Spy
	@InjectMocks
	private PolicyGroupDataCallable policyGroupDataCallable;
	
	@Mock
	private VersaApi versaApi;
	@Mock
	MessageChannel rawFragmentMessageInputChannel;
	
	String policyGroupresponse = "{ \"sdwan-policy-group\": [ { \"name\": \"Policy1\", \"description\": \"policy1\", \"rules\": { \"rule\": [ { \"name\": \"testrule1\" }, { \"name\": \"testrule2\" } ], \"statistics\": {} } }, { \"name\": \"Policy111111\", \"description\": \"policy11111\", \"rules\": { \"rule\": [ { \"name\": \"testrule1111\" }, { \"name\": \"testrule2222222\" } ], \"statistics\": {} } } ] }";
	String policyGroupresponseError = "{ \"sdwan-policy-group\": [ { \"name\": \"Policy1\",\"name1\": \"Policy1\" ,\"description\": \"policy1\", \"rules\": { \"rule\": [ { \"name\": \"testrule1\" }, { \"name\": \"testrule2\" } ], \"statistics\": {} } }, { \"name\": \"Policy111111\", \"description\": \"policy11111\", \"rules\": { \"rule\": [ { \"name\": \"testrule1111\" }, { \"name\": \"testrule2222222\" } ], \"statistics\": {} } } ] }";
	String rulesPerPolicy = "{ \"rule\": [ { \"name\": \"testrule1\", \"description\": \"\", \"tag\": [ \"\" ], \"match\": { \"source\": { \"zone\": { \"zone-list\": [ \"\" ] }, \"user\": {}, \"region\": [ \"\" ] }, \"destination\": { \"region\": [ \"\" ] }, \"application\": { \"predefined-application-list\": [ \"BJNP\", \"BBM\", \"INMOBI\", \"BBC\", \"ELTIEMPO\" ] }, \"url-category\": { \"predefined\": [ \"internet_portals\", \"military\", \"hate_and_racism\" ] }, \"ip-version\": \"\", \"ip-flags\": \"\" }, \"set\": { \"action\": \"allow\", \"forwarding-profile\": \"IND_profile1\" } }, { \"name\": \"testrule2\", \"description\": \"\", \"tag\": [ \"\" ], \"match\": { \"source\": { \"zone\": { \"zone-list\": [ \"\" ] }, \"user\": {}, \"region\": [ \"\" ] }, \"destination\": { \"region\": [ \"\" ] }, \"application\": { \"predefined-application-list\": [ \"SINA_FINANCE\", \"IMEET\", \"ELTIEMPO\", \"BBC\", \"PULSE_COM_GH\" ] }, \"url-category\": { \"predefined\": [ \"internet_portals\", \"military\", \"real_estate\", \"hate_and_racism\" ] }, \"ip-version\": \"\", \"ip-flags\": \"\" }, \"set\": { \"action\": \"allow\", \"forwarding-profile\": \"IND_profile2\" } } ] }";
	String rulesPerPolicyError = "{ \"rule\": [ { \"name\": \"testrule1\",\"name1\": \"testrule1\", \"description\": \"\", \"tag\": [ \"\" ], \"match\": { \"source\": { \"zone\": { \"zone-list\": [ \"\" ] }, \"user\": {}, \"region\": [ \"\" ] }, \"destination\": { \"region\": [ \"\" ] }, \"application\": { \"predefined-application-list\": [ \"BJNP\", \"BBM\", \"INMOBI\", \"BBC\", \"ELTIEMPO\" ] }, \"url-category\": { \"predefined\": [ \"internet_portals\", \"military\", \"hate_and_racism\" ] }, \"ip-version\": \"\", \"ip-flags\": \"\" }, \"set\": { \"action\": \"allow\", \"forwarding-profile\": \"IND_profile1\" } }, { \"name\": \"testrule2\", \"description\": \"\", \"tag\": [ \"\" ], \"match\": { \"source\": { \"zone\": { \"zone-list\": [ \"\" ] }, \"user\": {}, \"region\": [ \"\" ] }, \"destination\": { \"region\": [ \"\" ] }, \"application\": { \"predefined-application-list\": [ \"SINA_FINANCE\", \"IMEET\", \"ELTIEMPO\", \"BBC\", \"PULSE_COM_GH\" ] }, \"url-category\": { \"predefined\": [ \"internet_portals\", \"military\", \"real_estate\", \"hate_and_racism\" ] }, \"ip-version\": \"\", \"ip-flags\": \"\" }, \"set\": { \"action\": \"allow\", \"forwarding-profile\": \"IND_profile2\" } } ] }";
	
	@Before
	public void setup(){		
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPolicyGroupData(){
		try {
			Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(policyGroupresponse);
			Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			Mockito.doReturn(true).when(policyGroupDataCallable)
			    .rulesPerPolicyData(Mockito.anyList());
		
			assertTrue(policyGroupDataCallable.policyGroupData(VesraTestDataUtils.getTemplates()));
			Mockito.verify(rawFragmentMessageInputChannel).send(Mockito.any(GenericMessage.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEmptyVersaApiResponse(){
		try {
		   	Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenThrow(new ResoucePullingFailedException("INTERFACE","url","Message"));
		   	Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
		   	Mockito.doReturn(true).when(policyGroupDataCallable)
			    .rulesPerPolicyData(Mockito.anyList());
			assertTrue(policyGroupDataCallable.policyGroupData(VesraTestDataUtils.getTemplates()));
			Mockito.verify(versaApi).versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCahnnelFailed(){
		try {
		   	Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(policyGroupresponse);
		   	Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(false);
		   	Mockito.doReturn(true).when(policyGroupDataCallable)
			    .rulesPerPolicyData(Mockito.anyList());
			assertFalse(policyGroupDataCallable.policyGroupData(VesraTestDataUtils.getTemplates()));
			Mockito.verify(versaApi).versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	
	@Test
	public void testRulesPerPolicyData(){
		try {
		   	Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(rulesPerPolicy);
		   	Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			assertTrue(policyGroupDataCallable.rulesPerPolicyData(VesraTestDataUtils.getVersaQueryData()));
			Mockito.verify(versaApi).versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	@Test
	public void testRulesApiResponse(){
		try {
		   	Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenThrow(new ResoucePullingFailedException("INTERFACE","url","Message"));
		   	Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			assertTrue(policyGroupDataCallable.rulesPerPolicyData(VesraTestDataUtils.getVersaQueryData()));
			Mockito.verify(versaApi).versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class));
		} catch (Exception e) {
		    LOG.error(VesraTestDataUtils.CAUSE,e);
		}
	}
	
	@Test
	public void testRulesApiErrorResponse(){
		try {
		   	Mockito.when(versaApi.versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class))).thenReturn(rulesPerPolicy);
		   	Mockito.when(rawFragmentMessageInputChannel.send(Mockito.any(GenericMessage.class))).thenReturn(true);
			assertTrue(policyGroupDataCallable.rulesPerPolicyData(VesraTestDataUtils.getVersaQueryData()));
			Mockito.verify(versaApi).versaGetCall(Mockito.anyString(), Mockito.anyMap(), Mockito.eq(String.class));
		} catch (Exception e) {
		    LOG.error("Cause is {}",e);
		}
	}
	
	
}
