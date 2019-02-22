package com.sap.tcl.avalon.user.inbound.services;

public class MainProductPullServiceWorker {

    private ProductPullServiceWorker productPullServiceWorker;
    private EventServiceWorker eventServiceWorker;

    public void productPull() {

       getProductPullServiceWorker().pull();
       getEventServiceWorker().pullEvent();
    }

    public ProductPullServiceWorker getProductPullServiceWorker() {
        return productPullServiceWorker;
    }

    public void setProductPullServiceWorker(
            ProductPullServiceWorker productPullServiceWorker) {
        this.productPullServiceWorker = productPullServiceWorker;
    }

    public EventServiceWorker getEventServiceWorker() {
        return eventServiceWorker;
    }

    public void setEventServiceWorker(EventServiceWorker eventServiceWorker) {
        this.eventServiceWorker = eventServiceWorker;
    }

}
