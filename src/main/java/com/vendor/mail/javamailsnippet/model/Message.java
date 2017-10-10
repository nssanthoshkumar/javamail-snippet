package com.vendor.mail.javamailsnippet.model;

/*
 * @author Santhosh
 */
public class Message {

	private String sender;
	private String subject;
	private String body;
	private String attachmentContent;
	private String attachmentName;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttachmentContent() {
		return attachmentContent;
	}

	public void setAttachmentContent(String attachmentContent) {
		this.attachmentContent = attachmentContent;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

}
