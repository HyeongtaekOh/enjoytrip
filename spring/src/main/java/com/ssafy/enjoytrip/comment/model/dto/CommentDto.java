package com.ssafy.enjoytrip.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	private int commentId;
	private String type;
	private int contentId;
	private String content;
	private int userId;
	private String userName;
	private String createdTime;
	private String modifiedTime;
}
