package com.sap.tcl.avalon.user.test;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.sap.tcl.avalon.user.inbound.services.UserApi;
import com.sap.tcl.avalon.user.inbound.services.UserProductPullServiceWorker;
import com.sap.tcl.avalon.user.inbound.services.UserPullServiceWorker;

public class UserPullServiceWorkerTest {

    private static final String IS_PARTNER = "IS_PARTNER";
    private static final String CUSTOMER = "CUSTOMER";
    private static final String ID_TYPE = "ID_TYPE";
    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    private static final String DEFAULT_FALSE = "false";

    @Spy
    @InjectMocks
    private UserPullServiceWorker userPullServiceWorker;
    @Mock
    private UserApi userApi;
    @Mock
    MessageChannel rawFragmentMessageInputChannel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userPullServiceWorker.setIsPartner(IS_PARTNER);
        userPullServiceWorker.setIsCustomer(CUSTOMER);
        userPullServiceWorker.setIsPartnerValue("Partner");
        userPullServiceWorker.setOwner("OWNER");
        userPullServiceWorker.setIdType(ID_TYPE);
        userPullServiceWorker.setIdTypeValue("Account ID");
        userPullServiceWorker.setAccountId(ACCOUNT_ID);
        userPullServiceWorker.setLeOwner("LE_OWNER");
        userPullServiceWorker.setcUID("CUID");
        userPullServiceWorker.setCountryName("COUNTRY_NAME");
        UserProductPullServiceWorker.setSkippingElement("metadata");
        UserProductPullServiceWorker.setRootElement("d");
        UserProductPullServiceWorker.setSubRootElement("results");
        UserProductPullServiceWorker.setUserApi(userApi);
    }

    @Test
    public void checkForAccount() {
        JSONObject jsonObject = new JSONObject(getAccountJsonString());
        JSONObject customerRootElement = (JSONObject) jsonObject.get("d");
        JSONArray array = (JSONArray) customerRootElement.get("results");
        List<Map<String, String>> properties = userPullServiceWorker
                .convertToRawdata(array);
        for (Map<String, String> map : properties) {
            Assert.assertEquals("ACCOUNT", map.get(ID_TYPE));
            Assert.assertEquals("", map.get(ACCOUNT_ID));
            Assert.assertNotEquals("", map.get("CUID"));
            Assert.assertEquals(DEFAULT_FALSE, map.get(CUSTOMER));
            Assert.assertEquals(DEFAULT_FALSE, map.get(IS_PARTNER));
            Assert.assertEquals("null", map.get("COUNTRY_NAME"));

        }
    }

    private static String getAccountJsonString() {
        return "{\"d\": { \"results\": [    {   \"__metadata\": {   \"type\": \"com.tcl.avalon.customer_inventory.services.ods_cust_master.customer_detailsType\",  "
                + "  },\"CUST_ID\": 88871, \"ID_TYPE\": \"Account ID\",    \"PARTY_ID\": \"912250063175379356\",    \"ACCOUNT_ID_18\": null,   "
                + " \"ACCOUNT_ID\": \"account111111\",    \"SFDC_ACCT_ID\": \"SFDC - acc11\",    \"CUID\": null,    \"CUSTOMER_NAME\": "
                + "\"InfoTrellis India\",    \"PARTNER_BUSINESS_ELIGIBILITY\": null,    \"SOURCE_DISPLAY_NAME\": \"InfoTrellis India\",  "
                + "  \"CUSTOMER_TYPE\": null,    \"COUNTRY_NAME\": null,    \"CITY_NAME\": null,    \"PROV_STATE_NAME\": null,    "
                + "\"ADDR_LINE_ONE\": null,    \"ADDR_LINE_TWO\": null,    \"ADDR_LINE_THREE\": null,    \"POSTAL_CODE\": null,    "
                + "\"SOURCE_CREATED_DATE\": \"2012-10-08 11:57:18\",    \"SOURCE_MODIFIED_DATE\": null,    \"RELAYWAREID\": null,    "
                + "\"PARTNER_SEGMENT\": null,    \"IS_PARTNER\": null,    \"CUSTOMER\": null,    \"PARTNER_END_CUSTOMER\": null,    "
                + "\"RECORD_TYPE\": \"01220000000LXrLAAW\",    \"PARTNER_TYPE\": null,    \"PARTNER_LEVEL\": null,    \"OPERATING_TERRITORY\": null,"
                + "    \"PROGRAM_TRACK\": null,    \"ACCOUNT_STATUS\": null,    \"PARTNER_ONBOARDING_FLAG\": null,    \"PROFILE\": null,    "
                + "\"SELLING_MODES\": null,    \"SERVICE_SEGMENT\": null,    \"ACCOUNT_OWNER\": null,    \"LE_OWNER\": null,    "
                + "\"LEGALENTITY_ID\": null,    \"CURRENCY\": \"USD\",    \"STATUS_IND\": \"ACTIVE\",    "
                + "   \"INACTIVATED_DT\": null,    \"LOADTIME\": \" Date(1516340110210)\",    \"OPERATION\": \"I\"   },    "
                + "      ]       }  }";
    }

    @Test
    public void checkForLegalEntity() {
        JSONObject jsonObject = new JSONObject(getLegalEntityJsonString());
        Map<String, String> properties = userPullServiceWorker
                .prepareCustomerRawData(jsonObject);
        Assert.assertEquals("LEGALENTITY", properties.get(ID_TYPE));
        Assert.assertNotEquals("", properties.get(ACCOUNT_ID));
        Assert.assertNotEquals("", properties.get("CUID"));
        Assert.assertEquals(DEFAULT_FALSE, properties.get(CUSTOMER));
        Assert.assertEquals("true", properties.get(IS_PARTNER));
        Assert.assertEquals("UG", properties.get("ISO_CODE"));

    }

    private static String getLegalEntityJsonString() {
        return "{ \"ID_TYPE\": \"CUID\",  \"PARTY_ID\": 258551074456875140,  \"ACCOUNT_ID_18\": \"\", "
                + " \"ACCOUNT_ID\": \"0010O0000199999\",  \"SFDC_ACCT_ID\": \"\",  \"CUID\": \"CU00666\", "
                + " \"CUSTOMER_NAME\": \"MDM SIT DEFECT 1234\",  \"PARTNER_BUSINESS_ELIGIBILITY\": \"\", "
                + " \"SOURCE_DISPLAY_NAME\": \"MDM SIT DEFECT 1234\",  \"CUSTOMER TYPE\": \"Carriers\", "
                + " \"COUNTRY_NAME\": \"Uganda\",  \"CITY_NAME\": \"Nagpur\",  \"PROV_STATE_NAME\": \"Kampala\", "
                + " \"ADDR_LINE_ONE\": \"18, Dashsnga Ggaba Road\",  \"ADDR_LINE_TWO\": \"\",  \"ADDR_LINE_THREE\":"
                + " \"\",  \"POSTAL_CODE\": 28237,  \"SOURCE_CREATED_DATE\": \"\",  \"SOURCE_MODIFIED_DATE\": \"\","
                + "  \"RELAYWAREID\": \"\",  \"PARTNER_SEGMENT\": \"\",  \"IS_PARTNER\": \"Partner\",  \"CUSTOMER\": "
                + "\"\",  \"PARTNER END CUSTOMER\": \"\",  \"RECORD TYPE\": \"\",  \"PARTNER_TYPE\": \"\","
                + "  \"PARTNER_LEVEL\": \"\",  \"OPERATING_TERRITORY\": \"\",  \"PROGRAM_TRACK\": \"\", "
                + " \"ACCOUNT_STATUS\": \"\",  \"PARTNER_ONBOARDING_FLAG\": \"\",  \"PROFILE\": \"\",  "
                + "\"SELLING_MODES\": \"\",  \"SERVICE SEGMENT\": \"Premium\",  \"ACCOUNT_OWNER\": \"\",  "
                + "\"LE_OWNER\": \"Shruti Pillai\",  \"LEGALENTITY_ID\": \"\",  \"CURRENCY\": \"USD\",  "
                + "\"STATUS_IND\": \"ACTIVE\",  \"INACTIVATED_DT\": \"\" }";
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCustomerEntities() {
        Mockito.when(
                userApi.userGetCall(Mockito.anyString(), Mockito.anyMap(),
                        Mockito.eq(String.class))).thenReturn(
                getAccountJsonString());

        Mockito.when(
                rawFragmentMessageInputChannel.send(Mockito
                        .any(GenericMessage.class))).thenReturn(true);

        userPullServiceWorker
                .processUserData("ABC", "dummy data", "dummy feed");
        Mockito.verify(rawFragmentMessageInputChannel).send(
                Mockito.any(GenericMessage.class));

    }

    @Test
    public void testPull() {

        Mockito.doNothing()
                .when(userPullServiceWorker)
                .processUserData(Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString());

        userPullServiceWorker.pull();

    }

}
