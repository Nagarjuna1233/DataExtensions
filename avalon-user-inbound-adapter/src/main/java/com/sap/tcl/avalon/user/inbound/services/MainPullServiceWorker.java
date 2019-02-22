package com.sap.tcl.avalon.user.inbound.services;

public class MainPullServiceWorker {

    private UserPullServiceWorker userPullServiceWorker;
    private EventServiceWorker eventServiceWorker;

    public void userPull() {
        getUserPullServiceWorker().pull();
        getEventServiceWorker().pullEvent();
    }

    public UserPullServiceWorker getUserPullServiceWorker() {
        return userPullServiceWorker;
    }

    public void setUserPullServiceWorker(
            UserPullServiceWorker userPullServiceWorker) {
        this.userPullServiceWorker = userPullServiceWorker;
    }

    public EventServiceWorker getEventServiceWorker() {
        return eventServiceWorker;
    }

    public void setEventServiceWorker(EventServiceWorker eventServiceWorker) {
        this.eventServiceWorker = eventServiceWorker;
    }

}
