package com.team_five.salthub.service.impl;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.FirstComment;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.service.FirstCommentService;
import org.apache.ibatis.annotations.Arg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirstCommentServiceImplTest {


	@Autowired
	FirstCommentService firstCommentService;

	@ParameterizedTest
	@MethodSource("args1")
	void testPublish(FirstComment firstComment, String correct){
		try {
			firstCommentService.publishFirstComment(firstComment);
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args1(){
		Date date = new Date();
		return Stream.of(
				Arguments.of(new FirstComment(7L, 1L, "zhuangweilong", null, date), "内容为空"),
				Arguments.of(new FirstComment(7L, null, "zhuangweilong", "abc", date), "blog_id为空")

		);
	}


	@ParameterizedTest
	@MethodSource("args2")
	void testQuery(Long id, String correct){
		try {
			firstCommentService.queryFirstComment(id);

		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args2(){
		return Stream.of(
				Arguments.of(1L,"该博客id不存在")
		);
	}


}