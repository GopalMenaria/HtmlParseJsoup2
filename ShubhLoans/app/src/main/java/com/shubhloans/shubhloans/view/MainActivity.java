package com.shubhloans.shubhloans.view;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shubhloans.shubhloans.R;
import com.shubhloans.shubhloans.controller.ContentRequestTask;
import com.shubhloans.shubhloans.listener.ContentResponseListener;
import com.shubhloans.shubhloans.model.Word;
import com.shubhloans.shubhloans.utils.NetworkConnectionHelper;
import com.shubhloans.shubhloans.utils.StringHelper;

import java.util.Arrays;
import java.util.Map;

/**
 * MainActivity class to inflate UI  and handle ui events.
 */
public class MainActivity extends AppCompatActivity implements NetworkConnectionHelper.NetworkConnectionCallback {
    private TextView char10thText;
    private TextView everyChar10thText;
    private TextView wordCountText;
    private boolean isNetAvailable;

    private NetworkConnectionHelper networkConnectionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        handleNetworkConnectionState();
    }

    /**
     * Receive updates on Network Availability and take proper action.
     */
    private void handleNetworkConnectionState() {
        networkConnectionHelper = new NetworkConnectionHelper(this);
        networkConnectionHelper.checkNetworkConnection(this);
    }

    /**
     * Method to initialize Ui components.
     */
    private void initView() {
        char10thText = findViewById(R.id.char_10th);
        everyChar10thText = findViewById(R.id.every_char_10th);
        everyChar10thText.setMovementMethod(new ScrollingMovementMethod());
        wordCountText = findViewById(R.id.word_count);
        wordCountText.setMovementMethod(new ScrollingMovementMethod());
    }


    /**
     * OnClickListener implementation.
     *
     * @param view button view
     */
    public void onQueryFire(View view) {
        /**Clear the data on ui**/
        char10thText.setText("");
        everyChar10thText.setText("");
        wordCountText.setText("");

        /** Async task running simultaneously and updating ui using THREAD_POOL_EXECUTOR**/
        if (isNetAvailable) {
            sendRequestForChar10thQuery();
            sendRequestForWordCountQuery();
            sendRequestForEveryChar10thQuery();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internet_off_text), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Create and send request for 10th character using Async task.
     */
    private void sendRequestForChar10thQuery() {
        final String aboutUrl = getString(R.string.about_url);

        new ContentRequestTask(aboutUrl, new ContentResponseListener() {
            @Override
            public void onSuccess(String content) {
                char10thText.setText(StringHelper.getEveryNthCharacter(content, 10, 10));
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), aboutUrl + " : " + getString(R.string.error_text), Toast.LENGTH_SHORT).show();
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Create and send request for every 10th character using Async task.
     */
    private void sendRequestForEveryChar10thQuery() {
        final String indexUrl = getString(R.string.index_url);
        new ContentRequestTask(indexUrl, new ContentResponseListener() {
            @Override
            public void onSuccess(String content) {
                everyChar10thText.setText(Arrays.toString(StringHelper.getEveryNthCharacter(content, 10, content.length()).toCharArray()));
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), indexUrl + " : " + getString(R.string.error_text), Toast.LENGTH_SHORT).show();
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Create and send request for unique word with occurrences using Async task.
     */
    private void sendRequestForWordCountQuery() {
        final String contactUrl = getString(R.string.contact_url);
        new ContentRequestTask(contactUrl, new ContentResponseListener() {
            @Override
            public void onSuccess(String content) {
                String desc = content.replaceAll("[!|'.“/,‘’”}]", "").replaceAll("com", ".com");
                String words[] = desc.split("\\s+");
                StringBuilder mapData = new StringBuilder();
                for (Map.Entry<Word, Integer> entry : StringHelper.getUniqueWordCountFromStringArray(words).entrySet()) {
                    mapData.append(entry.getKey().getDataKey()).append(" : ").append(entry.getValue()).append("\n");
                }
                wordCountText.setText(mapData);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), contactUrl + " : " + getString(R.string.error_text), Toast.LENGTH_SHORT).show();
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void networkAvailable() {
        isNetAvailable = true;
        Toast.makeText(getApplicationContext(), getString(R.string.internet_on_text), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkLost() {
        isNetAvailable = false;
        Toast.makeText(getApplicationContext(), getString(R.string.internet_off_text), Toast.LENGTH_SHORT).show();
    }
}
