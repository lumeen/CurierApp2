package com.social.services;

import com.social.security.HeaderRequestInterceptor;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsService {

    private static final String FIREBASE_SERVER_KEY = "AAAAEmZHNFI:APA91bF1_ctBMg2eLQI61qSsJjZR5504jr8czMyuXAsKN91GycLubKWt0odpK6QhJKaYZ0nnl1Unz3jyslbGo7wp8fDmfxxdupvb7C71h4LzjFbCkuAjR16RMtVryNyJfJx9bAFNKUBeFdAqmsnPF9xhSB9rcQLmCA";


    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";


    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        /**
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY*/

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}

