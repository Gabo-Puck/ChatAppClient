package com.azureproject.chatclient.Models;

import java.util.concurrent.CountDownLatch;

import com.azureproject.SharedModels.AppMessage;

public class Response {
    CountDownLatch latch;
    AppMessage responseData;

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public AppMessage getResponseData() {
        return responseData;
    }

    public void setResponseData(AppMessage responseData) {
        this.responseData = responseData;
    }

    public Response(CountDownLatch latch, AppMessage responseData) {
        this.latch = latch;
        this.responseData = responseData;
    }

}
