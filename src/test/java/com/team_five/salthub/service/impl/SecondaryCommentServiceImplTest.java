package com.team_five.salthub.service.impl;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.FirstComment;
import com.team_five.salthub.model.SecondaryComment;
import com.team_five.salthub.service.SecondaryCommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecondaryCommentServiceImplTest {


	@Autowired
	SecondaryCommentService secondaryCommentService;

	@ParameterizedTest
	@MethodSource("args1")
	void testPublish(SecondaryComment secondaryComment, String correct){
		try {
			secondaryCommentService.publishSecondaryComment(secondaryComment);
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args1(){
		Date date = new Date();
		return Stream.of(
				Arguments.of(new SecondaryComment(7L, 1L, "zhuangweilong", "221801310", null, date), "内容为空"),
				Arguments.of(new SecondaryComment(7L, null, "zhuangweilong", "221801310", "123123", date), "被评论commentId为空"),
//				Arguments.of(new SecondaryComment(7L, 1L, "zhuangweilong", "221801310", "123123", date), "被评论commentId为空"),
				Arguments.of(new SecondaryComment(7L, 999L, "zhuangweilong", "221801310", "123123", date), "该一级评论id不存在")

		);
	}

}