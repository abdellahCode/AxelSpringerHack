package com.abdlh.axelspringerhack.gettyConnectSDK.connectsdk.search;


import com.abdlh.axelspringerhack.gettyConnectSDK.connectsdk.Credentials;

public class Search {
    private String baseUrl;
    private Credentials credentials;

    private Search(Credentials credentials, String baseUrl) {
        this.credentials = credentials;
        this.baseUrl = baseUrl;
    }

    public static Search GetInstance(Credentials credentials, String baseUrl) {
        return new Search(credentials, baseUrl);
    }

    public IBlendedImagesSearch Images() {
        return SearchImages.GetInstance(credentials, baseUrl);
    }
}
