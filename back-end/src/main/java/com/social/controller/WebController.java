package com.social.controller;

import com.social.dao.UserRepository;
import com.social.services.AndroidPushNotificationsService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class WebController {

    private final String TOPIC = "mb";

    @Autowired
    UserRepository userRepository;
    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "JSA Notification");
        notification.put("body", "Happy Message!");

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notification);
        body.put("data", data);
        body.put(" ttl", 3600);

        /**
         {
         "notification": {
         "title": "JSA Notification",
         "body": "Happy Message!"
         },
         "data": {
         "Key-1": "JSA Data 1",
         "Key-2": "JSA Data 2"
         },
         "to": "/topics/JavaSampleApproach",
         "priority": "high"
         }
         */

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);

return null;
    }


    @RequestMapping(value = "/topics", method = RequestMethod.GET, produces = "application/json")
    public String getTopics() throws JSONException {
        List<String> b = userRepository.findAll().stream().map(r->r.getUsername()).collect(Collectors.toList());
        String a="";
        for (String aB : b) {
            a = a + aB +"--";
        }

        a=a.substring(0,a.length()-2);

        return a;
    }

}