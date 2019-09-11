package com.shubhloans.shubhloans.controller;

import android.os.AsyncTask;

import com.shubhloans.shubhloans.listener.ContentResponseListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * ContentRequestTask class for sending request for content to endpoint.
 */

public class ContentRequestTask extends AsyncTask<Void, Void, String> {
    private ContentResponseListener contentResponseListener;
    private String contentUrl;
    private String filterText = "\\{\\{displayFeature.title\\}\\}|\\{\\{displayFeature.details\\}";

    public ContentRequestTask(String url, ContentResponseListener contentResponseListener) {
        this.contentUrl = url;
        this.contentResponseListener = contentResponseListener;
    }

    @Override
    protected String doInBackground(Void... params) {
        Document document;
        try {
            /** Jsoup library for parsing html data.**/
            document = Jsoup.connect(contentUrl).get();
        } catch (IOException e) {
            return null;
        }
        Elements docContent = document.select("div");
        return docContent.text().replaceAll(filterText, "");
    }

    @Override
    protected void onPostExecute(String content) {
        if (content != null) {
            contentResponseListener.onSuccess(content);
        } else {
            contentResponseListener.onError();
        }
    }
}
