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

import com.sap.tcl.avalon.user.inbound.services.ProductPullServiceWorker;
import com.sap.tcl.avalon.user.inbound.services.UserApi;
import com.sap.tcl.avalon.user.inbound.services.UserProductPullServiceWorker;

public class ProductPullServiceWorkerTest {

    private static final String CONSTANT = "100007";
    private static final String RESULTS = "results";
    @Spy
    @InjectMocks
    private ProductPullServiceWorker productPullServiceWorker;

    @Mock
    private UserApi userApi;
    @Mock
    MessageChannel rawFragmentMessageInputChannel;

    String response = "{\"d\": { \"results\": [    {   \"__metadata\": {   \"type\": \"com.tcl.avalon.customer_inventory.services.ods_cust_master.customer_detailsType\",  "
            + "  },\"cuid\": 100007, \"serviceType\": \"GVPN\",      },    "
            + "      ]       }  }";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        productPullServiceWorker.setCuid(CONSTANT);
        UserProductPullServiceWorker.setSkippingElement("metadata");
        UserProductPullServiceWorker.setSkippingElement("metadata");
        UserProductPullServiceWorker.setRootElement("d");
        UserProductPullServiceWorker.setSubRootElement(RESULTS);
        UserProductPullServiceWorker.setUserApi(userApi);
    }

    @Test
    public void checkDataConversion() {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject customerRootElement = (JSONObject) jsonObject.get("d");
        JSONArray array = (JSONArray) customerRootElement.get(RESULTS);
        List<Map<String, String>> properties = productPullServiceWorker
                .convertToRawdata(array);
        for (Map<String, String> map : properties) {
            Assert.assertEquals(CONSTANT, map.get("cuid"));
            Assert.assertEquals("GVPN", map.get("serviceType"));
            Assert.assertTrue(map.get("cuid").equals(CONSTANT));
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testProductEntities() {
        Mockito.when(
                userApi.userGetCall(Mockito.anyString(), Mockito.anyMap(),
                        Mockito.eq(String.class))).thenReturn(response);

        Mockito.when(
                rawFragmentMessageInputChannel.send(Mockito
                        .any(GenericMessage.class))).thenReturn(true);

        productPullServiceWorker.processProductData("ABC", "dummy data",
                "dummy feed");
        Mockito.verify(rawFragmentMessageInputChannel).send(
                Mockito.any(GenericMessage.class));

    }

    @Test
    public void testPull() {

        Mockito.doNothing()
                .when(productPullServiceWorker)
                .processProductData(Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString());

        productPullServiceWorker.pull();
    }
}
