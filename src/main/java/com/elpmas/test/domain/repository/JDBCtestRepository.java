package com.elpmas.test.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCtestRepository {

	@Autowired
	JdbcTemplate  jdbctemplate; //jdbcを利用するためのJdbcTemplate(SpringBootでは自動でBean定義されるのでAutowiredのみで使える)

	public List findAll(){

		String sql ="SELECT * FROM book_sample ORDER BY id";

		return jdbctemplate.queryForList(sql);
	}
}
