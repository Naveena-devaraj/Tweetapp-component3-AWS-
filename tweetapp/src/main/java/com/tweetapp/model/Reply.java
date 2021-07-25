package com.tweetapp.model;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Reply {
	@DynamoDBAttribute
	private String loginID;
	@DynamoDBAttribute
	private String replyMsg;
	@DynamoDBAttribute
	private Date replyCreatedDate;

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(String replyMsg) {
		this.replyMsg = replyMsg;
	}

	public Date getReplyCreatedDate() {
		return replyCreatedDate;
	}

	public void setReplyCreatedDate(Date replyCreatedDate) {
		this.replyCreatedDate = replyCreatedDate;
	}

}
