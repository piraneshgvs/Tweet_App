package com.tweetapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="REPLY_TWEET_INFO")
public class ReplyTweetTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long subTweetId;
	
	@NotNull(message="Tweet ID cannot be null")
	private Long tweetId;
	
	@NotBlank(message="UserId cannot be null")
	private String userId;
	
	@NotBlank(message="Reply Tweet cannot be Blank")
	@Length(min=1,max=144)
	private String replytweet;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date replyCreated;
	
	
	public Long getSubTweetId() {
		return subTweetId;
	}
	public void setSubTweetId(Long subTweetId) {
		this.subTweetId = subTweetId;
	}
	public Long getTweetId() {
		return tweetId;
	}
	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReplytweet() {
		return replytweet;
	}
	public void setReplytweet(String replytweet) {
		this.replytweet = replytweet;
	}
	public Date getReplyCreated() {
		return replyCreated;
	}
	public void setReplyCreated(Date replyCreated) {
		this.replyCreated = replyCreated;
	}
	@Override
	public String toString() {
		return "ReplyTweetTable [subTweetId=" + subTweetId + ", tweetId=" + tweetId + ", userId=" + userId
				+ ", replytweet=" + replytweet + ", replyCreated=" + replyCreated + "]";
	}
	public ReplyTweetTable(Long subTweetId, @NotNull(message = "Tweet ID cannot be null") Long tweetId,
			@NotBlank(message = "UserId cannot be null") String userId,
			@NotBlank(message = "Reply Tweet cannot be Blank") String replytweet,
			@NotNull(message = "Date cannot be null") Date replyCreated) {
		super();
		this.subTweetId = subTweetId;
		this.tweetId = tweetId;
		this.userId = userId;
		this.replytweet = replytweet;
		this.replyCreated = replyCreated;
	}
	public ReplyTweetTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
