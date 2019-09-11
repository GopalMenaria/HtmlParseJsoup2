package com.shubhloans.shubhloans.listener;


/**
 * Listener class for getting back response.
 */

public interface ContentResponseListener {
    void onSuccess(String content);

    void onError();
}
