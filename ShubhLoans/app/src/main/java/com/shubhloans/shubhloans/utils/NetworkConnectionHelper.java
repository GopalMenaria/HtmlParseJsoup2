package com.shubhloans.shubhloans.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * This class monitors the network Connection of the device.
 */
public class NetworkConnectionHelper {

    private ConnectivityManager mConnectivityManager = null;
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private NetworkConnectionCallback networkConnectionCallback;
    private static Context context;

    public interface NetworkConnectionCallback {
        void networkAvailable();

        void networkLost();
    }

    /**
     * Constructor for the Class NetworkConnectionHelper
     *
     * @param context Application Context
     */
    public NetworkConnectionHelper(final Context context) {
        NetworkConnectionHelper.context = context;
    }

    /**
     * Checks if the Internet Connection is Available
     *
     * @param callback NetworkConnectionCallback to update Network Connection States
     */
    public void checkNetworkConnection(NetworkConnectionCallback callback) {
        registerNetworkReceiver(callback);
        if (networkConnectionCallback != null) {
            final NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                networkConnectionCallback.networkAvailable();
            } else {
                networkConnectionCallback.networkLost();
            }
        }
    }

    /**
     * Allows user to register receiver for network changes and to get callback on particular registered actions.
     *
     * @param callback NetworkConnectionCallback instance to send callback to user on receiving registered actions.
     */
    private void registerNetworkReceiver(NetworkConnectionCallback callback) {
        networkConnectionCallback = callback;
        networkBroadcastReceiver = new NetworkBroadcastReceiver();
        IntentFilter networkEventFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkEventFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        networkEventFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mConnectivityManager = (ConnectivityManager) NetworkConnectionHelper.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        context.registerReceiver(networkBroadcastReceiver, networkEventFilter);
    }

    /**
     * Inner Class that will receive intents sent by sendBroadcast().
     */
    public class NetworkBroadcastReceiver extends BroadcastReceiver {
        /**
         * This method is called when the BroadcastReceiver is receiving an
         * Intent broadcast.
         *
         * @param context The Context in which the receiver is running.
         * @param intent  The Intent being received
         */
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            assert action != null;
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION) && networkConnectionCallback != null) {
                final NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    networkConnectionCallback.networkAvailable();
                } else {
                    networkConnectionCallback.networkLost();
                }
            }
        }
    }

    /*
    Unregisters the Broadcast Receiver
     */
    public void unRegisterReceiver() {
        if (NetworkConnectionHelper.context != null && networkBroadcastReceiver != null) {
            NetworkConnectionHelper.context.unregisterReceiver(networkBroadcastReceiver);
            NetworkConnectionHelper.context = null;
        }
    }

}
