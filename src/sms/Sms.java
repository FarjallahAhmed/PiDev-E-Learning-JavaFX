/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sms;

import Entities.projet;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author NMEDIA
 */
public class Sms {
    
        public static final String ACCOUNT_SID = System.getenv("ACb8e37bb0bf3cea10e7bdde983d27d7a9");
    public static final String AUTH_TOKEN = System.getenv("b57e36889995d8e63ac432b1faff674b");

    public static void sendSMS(projet p) {
        Twilio.init("ACb8e37bb0bf3cea10e7bdde983d27d7a9", "b57e36889995d8e63ac432b1faff674b");
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21628225420"),
                new com.twilio.type.PhoneNumber("+17028003261"),
                "Nom Projet: "+p.getNom()+" Sujet Projet: "+p.getSujet()+" Description Projet: "+p.getDescription()+" Date : "+p.getDate_creation())
            .create();

        System.out.println(message.getSid());
    }
    
}
