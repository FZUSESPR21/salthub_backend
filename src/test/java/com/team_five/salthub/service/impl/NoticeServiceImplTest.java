package com.team_five.salthub.service.impl;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.service.NoticeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.stream.Stream;


@SpringBootTest
class NoticeServiceImplTest {
	@Autowired
	NoticeService service;

	@ParameterizedTest
	@MethodSource("args")
	void testPublishNotice(Notice notice, String correct){
		try {
			this.service.publishNotice(notice);
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args(){
		Date date = new Date();
		return Stream.of(
				Arguments.of(new Notice(null,null,"标题",null,date,"221801310"), "221801310","内容为空"),	//正确数据测试
				Arguments.of(new Notice(null,null,null,"内容",date,"221801310"), "221801310", "标题为空"),
				Arguments.of(new Notice(null,null,"标题","内容",date,"221801310"), null, "发布人为空"),
				Arguments.of(new Notice(null,null,"标题","内容",date,null), "221801310", "未指定接收人")
//				Arguments.of(new Notice(null,"221801310","标题","内容",date,"221801310"), "221801310", "内容长度过长"),
//				Arguments.of(new Notice(null,"221801310","标题","内容",date,"221801310"), "221801310", "标题长度过长")
		);
	}

	@ParameterizedTest
	@MethodSource("args1")
	void testQueryNoticeByName(String accountName, Long current,String correct){
		try {
			this.service.queryNoticeByName(accountName, current);
//			Assertions.assertEquals();
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args1(){
		return Stream.of(
				Arguments.of(null, 1L,"用户名为空")

		);
	}


}