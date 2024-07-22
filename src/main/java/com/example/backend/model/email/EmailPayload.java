package com.example.backend.model.email;

import com.example.backend.model.events.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailPayload {

    public static String generateEmailBody(Event event ) {
        StringBuilder body = new StringBuilder();
        body.append("Dragă colegule,\n\n");
        body.append("Ai fost invitat de către"+" "+ event.getUser().getUsername()+ " "+ "să participi la această întâlnire \n\n");
        body.append("Data de începere a evenimentului: ").append(event.getStartDate()).append("\n");
        body.append("Data de finalizare a evenimentului: ").append(event.getEndDate()).append("\n");
        body.append("Cu locația: ").append(event.getLocation()).append("\n\n");
        body.append("Cu motivul: ").append(event.getDescription()).append("\n\n");
        body.append("Te rog să mă anunți, \n\n");
        body.append("Numai bine,\n");
        body.append( event.getUser().getUsername());

        return body.toString();
    }

}