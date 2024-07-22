package com.example.backend.model.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Builder()
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String sender;
    private String recipient;
    private String subject;
    private String content;


}
