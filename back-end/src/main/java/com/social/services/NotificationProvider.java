package com.social.services;


import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public class NotificationProvider {

    public HttpEntity<String> createNotification(String ... strings){

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + strings[0]);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", strings[1]);
        notification.put("body", strings[2]);

        JSONObject data = new JSONObject();
        for(int i=3 ; i<strings.length; i++) {
            data.put("Key" + i, strings[i]);
        }

        body.put("notification", notification);
        body.put("data", data);
        body.put(" ttl", 3600);

     return new HttpEntity<String>(body.toString());
    }

}
