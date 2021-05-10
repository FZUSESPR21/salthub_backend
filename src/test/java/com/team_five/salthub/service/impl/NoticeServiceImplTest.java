package com.team_five.salthub.service.impl;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.model.Account;
import com.team_five.salthub.model.Notice;
import com.team_five.salthub.service.AccountService;
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
	@Autowired
	AccountService accountService;

	@ParameterizedTest
	@MethodSource("noticeargs")
	void testPublishNotice(Notice notice, String correct){
		try {
			this.service.publishNotice(notice);
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream noticeargs(){
		Date date = new Date();
		return Stream.of(
				Arguments.of(new Notice(null,"221801310","标题",null,date,"221801310"),"内容为空"),
				Arguments.of(new Notice(null,"221801310",null,"内容",date,"221801310"), "标题为空"),
//				Arguments.of(new Notice(null,null,"标题","内容",date,"221801310"), "发布人为空"),
				Arguments.of(new Notice(null,"221801310","标题","内容",date,null), "用户名为空"),
				Arguments.of(new Notice(null,"221801310","标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题过长标题","内容",date,"221801310"), "标题长度过长")
//				Arguments.of(new Notice(null,"221801310","标题","内容",date,"221801310"), "221801310", "标题长度过长")
		);
	}

	@ParameterizedTest
	@MethodSource("args1")
	void testQueryNoticeByName(String accountName, Integer current,String correct){
		try {
			this.service.queryNoticeByName(accountName, current);
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args1(){
		return Stream.of(
				Arguments.of(null, 1,"用户名为空"),
				Arguments.of("123", 1, "该用户名不存在")
		);
	}

	@ParameterizedTest
	@MethodSource("args2")
	void testDeleteNoticeByName(String id, String correct){
		try {
			this.service.deleteNotice(id);
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args2(){
		return Stream.of(
				Arguments.of("123", "该通知id不存在")
		);
	}

	@ParameterizedTest
	@MethodSource("args3")
	void testModifyNoticeByName(String id, String correct){
		try {
			this.service.deleteNotice(id);
		} catch (BaseException exception){
			Assertions.assertEquals(correct,exception.getMessage());
		}
	}

	static Stream args3(){
		return Stream.of(
				Arguments.of("123", "该通知id不存在")
		);
	}


}