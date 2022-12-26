package com.tweetapp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="TWEET_INFO")
public class TweetTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long tweetId;
	
	@NotBlank(message="userId cannot be Blank")
	private String userId;
	
	@NotBlank(message="Tweet cannot be blank")
	@Length(min=1,max=144)
	private String rootTweet;
	
	@Column(name = "creation_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date date;
	
	private Long likeCount;
	
	private String likeUser;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="t_id", referencedColumnName = "tweetId")
	private List<ReplyTweetTable> replytweetTable = new ArrayList<>();

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

	public String getRootTweet() {
		return rootTweet;
	}

	public void setRootTweet(String rootTweet) {
		this.rootTweet = rootTweet;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public String getLikeUser() {
		return likeUser;
	}

	public void setLikeUser(String likeUser) {
		this.likeUser = likeUser;
	}

	public List<ReplyTweetTable> getReplytweetTable() {
		return replytweetTable;
	}

	public void setReplytweetTable(List<ReplyTweetTable> replytweetTable) {
		this.replytweetTable = replytweetTable;
	}

	@Override
	public String toString() {
		return "TweetTable [tweetId=" + tweetId + ", userId=" + userId + ", rootTweet=" + rootTweet + ", date=" + date
				+ ", likeCount=" + likeCount + ", likeUser=" + likeUser + ", replytweetTable=" + replytweetTable + "]";
	}

	public TweetTable(Long tweetId, @NotBlank(message = "userId cannot be Blank") String userId,
			@NotBlank(message = "Tweet cannot be blank") String rootTweet, Date date, Long likeCount, String likeUser,
			List<ReplyTweetTable> replytweetTable) {
		super();
		this.tweetId = tweetId;
		this.userId = userId;
		this.rootTweet = rootTweet;
		this.date = date;
		this.likeCount = likeCount;
		this.likeUser = likeUser;
		this.replytweetTable = replytweetTable;
	}

	public TweetTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
