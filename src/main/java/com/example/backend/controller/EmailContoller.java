package com.example.backend.controller;

import com.example.backend.model.email.Email;
import com.example.backend.model.request.email.EmailRequest;
import com.example.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@RestController()
@RequestMapping("/email")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmailContoller extends BaseApiController {
    private final EmailService emailService;

    @PostMapping("send")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void sendEmail(@RequestBody final EmailRequest emailRequest) {
        try {
            emailService.sendEmail(emailRequest);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
