package com.sap.tcl.avalon.rest.services;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.sap.tcl.avalon.exceptions.ResoucePullingFailedException;

/**
 * 
 * @author Techouts-1194 Advance glass fish jersey rest client
 *
 */
public class AvalonJerseyClient {

    private static final Logger LOG = LoggerFactory.getLogger(AvalonJerseyClient.class);

    private String url = null;
    private Builder builder = null;
    private ClientConfig clientConfig;
    private Client client = null;
    private int connectRetry = 0;
    private Class<? extends Exception> retryExceptionType;
    private ExponentialBackOffPolicy retrybackOffPolicy;
    private RetryTemplate retryTemplate;

    public AvalonJerseyClient(AvaloneHttpBuilder builder) {
        this.url = builder.url;
        this.builder = builder.builder;
        this.clientConfig = builder.clientConfig;
        this.client = builder.client;
        this.connectRetry = builder.retryCount;
        this.retryExceptionType = builder.retryExceptionType;
        this.retrybackOffPolicy = builder.retrybackOffPolicy;
        this.retryTemplate = builder.retryTemplate;
    }


    public <T> T get(Class<T> responseType,String interFaceName,String interfaceUrl,String msg) throws ResoucePullingFailedException {
        return this.syncMethod(AvalonClientConstants.GET, responseType ,interFaceName ,interfaceUrl ,msg);
    }

    public void closeConnection() {
        this.client.close();
    }

    public Class<? extends Exception> getRetryExceptionType() {
        return retryExceptionType;
    }

    public ExponentialBackOffPolicy getRetrybackOffPolicy() {
        return retrybackOffPolicy;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public <T> T syncMethod(String name, final Class<T> responseType,String interFaceName,String interfaceUrl,String msg) throws ResoucePullingFailedException {

        T response = null;
        try {
            response = getBuilder().method(name, responseType);
        } catch (Exception exception) {
            RetryTemplate temp = this.retryTemplate;
            if (temp != null) {
                if (getRetryExceptionType().isInstance(getRootCause(exception))) {
                    LOG.debug("Retry enabled for url {},count {}", this.url, this.connectRetry);
                    response = temp.execute(new RetryCallback<T, ResoucePullingFailedException>() {
                        @Override
                        public T doWithRetry(RetryContext context) throws ResoucePullingFailedException {
                          
                            try {
                                return getBuilder().method(name, responseType);

                            } catch (Exception exception) {
                                Throwable rootCause = getRootCause(exception);
                                if (getRetryExceptionType().isInstance(rootCause)) {
                                    throw new ResoucePullingFailedException(interFaceName, interfaceUrl,msg,rootCause);
                                } else {
                                    throw new ResoucePullingFailedException(interFaceName, interfaceUrl,msg,rootCause);
                                }
                            }
                        }
                    });
                } else {
                    throw new ResoucePullingFailedException(interFaceName, interfaceUrl,msg);
                }
            } else {
                throw new ResoucePullingFailedException(interFaceName, interfaceUrl,msg);
            }
        }
        return response;
    
        
    }

    protected Throwable getRootCause(final Throwable throwable) {
        if (throwable.getCause() != null) {
            return getRootCause(throwable.getCause());
        }
        return throwable;
    }

    public Builder getBuilder() {
        return builder;
    }

    public static class AvaloneHttpBuilder {

        private ClientConfig clientConfig = null;
        private Client client = null;
        private String acceptType = MediaType.APPLICATION_JSON;
        private Map<String, String> headers = new LinkedHashMap<String, String>();
        private String url = null;
        private Builder builder;
        private int retryCount = 0;
        private Class<? extends Exception> retryExceptionType;
        private ExponentialBackOffPolicy retrybackOffPolicy;
        private RetryTemplate retryTemplate;

        public AvaloneHttpBuilder(String url) {
            this.client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
            this.url = url;
        }

        public AvaloneHttpBuilder(String url, ClientConfig clientConfig) {
            this.url = url;
            this.clientConfig = clientConfig;
            this.client = ClientBuilder.newBuilder().register(JacksonFeature.class).register(clientConfig).build();
        }

        public AvaloneHttpBuilder setRetry(int count, Class<? extends Exception> retryExceptionType,
                ExponentialBackOffPolicy retrybackOffPolicy) {
            this.retryCount = count;
            this.retryExceptionType = retryExceptionType;
            this.retrybackOffPolicy = retrybackOffPolicy;
            return this;
        }

        public AvaloneHttpBuilder setRetry(int count, Class<? extends Exception> retryExceptionType) {
            this.retryCount = count;
            this.retryExceptionType = retryExceptionType;
            this.retrybackOffPolicy = new ExponentialBackOffPolicy();
            return this;
        }

        public AvaloneHttpBuilder setAccept(String acceptType) {
            this.acceptType = acceptType;
            return this;
        }

        public AvaloneHttpBuilder setBasicAuthentication(String userName, String password) {
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, password);
            this.client.register(feature);
            return this;
        }

        public AvaloneHttpBuilder setHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public void setPoolingConnectionManager(PoolingHttpClientConnectionManager poolingClientConnectionManager) {
            if (this.clientConfig == null) {
                this.clientConfig = new ClientConfig();
                this.clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, poolingClientConnectionManager);
                this.client.register(this.clientConfig);
            } else {
                this.clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, poolingClientConnectionManager);
                this.client.register(this.clientConfig);
            }
        }

        public <T> AvaloneHttpBuilder setLogger(Class<T> responseType) {
            this.client.register(responseType);
            return this;
        }

        /**
         * Create and configure a retry template if the consumer 'maxAttempts'
         * property is set.
         * 
         * @param properties
         *            The properties.
         * @return The retry template, or null if retry is not enabled.
         */
        protected RetryTemplate getRetryTemplateIfRetryEnabled(int retryCount,
                final Class<? extends Throwable> retryException, ExponentialBackOffPolicy backOffPolicy) {
            if (retryCount >= 1 && retryException != null) {
                RetryTemplate template = new RetryTemplate();
                SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(retryCount,
                        Collections.<Class<? extends Throwable>, Boolean> singletonMap(retryException, true));
                template.setRetryPolicy(retryPolicy);
                template.setBackOffPolicy(backOffPolicy);
                return template;
            }
            return null;
        }

        public AvalonJerseyClient build() {
            this.builder = client.target(this.url).request().accept(this.acceptType);
            for (Entry<String, String> headerSet : this.headers.entrySet()) {
                this.builder = builder.header(headerSet.getKey(), headerSet.getValue());
            }
            this.retryTemplate = getRetryTemplateIfRetryEnabled(this.retryCount, this.retryExceptionType,
                    this.retrybackOffPolicy);
            return new AvalonJerseyClient(this);
        }
    }

    public static class AvaloneHttpsBuilder extends AvaloneHttpBuilder {

        private String sslProtocalType = "TLSv1.1";

        public AvaloneHttpsBuilder(String url) throws KeyManagementException, NoSuchAlgorithmException {
            super(url);
            super.client = getHttpsClient().build();
            super.url = url;
        }

        private ClientBuilder getHttpsClient() throws KeyManagementException, NoSuchAlgorithmException {
            SSLContext sc = SSLContext.getInstance(this.sslProtocalType);// Java8
            System.setProperty("https.protocols", this.sslProtocalType);// Java8
            TrustManager[] trustAllCerts = { new AvalonDummyInsecureTrustManager() };
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HostnameVerifier allHostsValid = new AvalonDummyInsecureHostnameVerifier();
            return ClientBuilder.newBuilder().sslContext(sc).hostnameVerifier(allHostsValid);
        }

        public void setSslProtocalType(String sslProtocalType) {
            this.sslProtocalType = sslProtocalType;
        }

    }
}
