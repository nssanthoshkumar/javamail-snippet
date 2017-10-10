package com.vendor.mail.javamailsnippet.web;

import java.net.URI;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vendor.mail.javamailsnippet.model.Message;

/*
 * @author Santhosh
 */
@Controller
public class EmailController {
    
	private static final Logger log = LoggerFactory.getLogger(EmailController.class);
	
    @Autowired
    private JavaMailSender sender;

    @RequestMapping(value="/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<?> sendEmail(@RequestBody Message message) {
        try {
        	sendMailWithAttachment(message);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri();
            log.trace("Successfully sent the email.");
            return ResponseEntity.created(location).body("{\"status\":\"Email Sent!\"}");
        }catch(Exception ex) {
        	log.error("Exception occurred while sending the email.", ex);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void sendMailWithAttachment(Message messageToBeSent) throws Exception{
        if(messageToBeSent.getAttachmentContent()==null){
        	throw new Exception("Empty attachment content!!!");
        }
        
        MimeMessage message = sender.createMimeMessage();
        
        // Enable the multipart flag!
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        
        helper.setTo(messageToBeSent.getSender());
        helper.setText(messageToBeSent.getBody());
        helper.setSubject(messageToBeSent.getSubject());
        
        final InputStreamSource attachmentSource = new ByteArrayResource(messageToBeSent.getAttachmentContent().getBytes());
        helper.addAttachment(messageToBeSent.getAttachmentName(), attachmentSource);
        
        sender.send(message);
    }
}