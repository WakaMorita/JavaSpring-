package com.elpmas.test.domain.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.elpmas.test.domain.entity.BookEntity;

public class BookRowMapper implements RowMapper<BookEntity> {

	@Override
	public BookEntity mapRow(ResultSet rs,int rowNum) throws SQLException
	{
		BookEntity book = new BookEntity();
		book.setId(rs.getInt("id"));
		book.setTitle(rs.getString("title"));
		book.setSummary(rs.getString("summary"));
		book.setClassification(rs.getString("classification"));
		book.setDelflg(rs.getInt("delflg"));

		return book;
	}
}
