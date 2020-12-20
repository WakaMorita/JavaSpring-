package com.elpmas.test.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.elpmas.test.domain.entity.BookEntity;

@Repository
public class JDBCtestRepository {

	@Autowired
	JdbcTemplate  jdbctemplate; //jdbcを利用するためのJdbcTemplate(SpringBootでは自動でBean定義されるのでAutowiredのみで使える)

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List findAll(){

		String sql ="SELECT * FROM book_sample ORDER BY id";

		return jdbctemplate.queryForList(sql);
	}

	public Integer findMaxId(){

		String sql ="SELECT Max(id) FROM book_sample";

		return jdbctemplate.queryForObject(sql,Integer.class);//検索結果は第二引数で指定したデータ型で返却される
	}

	public String findTitleById(int id){

		String sql ="SELECT title FROM book_sample where id = ?"; //バインドパラメータは？で指定

		return jdbctemplate.queryForObject(sql,String.class,id);//第三引数でパラメータを指定
	}

	public String findTitleByIdAndDelFlg(Object[] args){

		String sql ="SELECT title FROM book_sample WHERE id = ? AND delflg = ?"; //バインドパラメータは？で指定

		return jdbctemplate.queryForObject(sql,args,String.class);//第三引数でパラメータを指定
	}

	public BookEntity findBookById(int id){

		String sql ="SELECT * FROM book_sample where id = ?"; //バインドパラメータは？で指定
		Map<String,Object> result = jdbctemplate.queryForMap(sql,id);//第三引数でパラメータを指定
		//取得済みデータをエンティティにsetする
		BookEntity book =new BookEntity();
		book.setId(Integer.parseInt(result.get("id").toString()));//なぜかLong型で取得されているのでLong型→String型→Int型でキャストをかける。
		book.setTitle((String)result.get("title"));
		book.setSummary((String)result.get("summary"));
		book.setClassification((String)result.get("classification"));
		book.setDelflg((int)result.get("delflg"));
		return book;
	}

	public List<BookEntity> findBooksByDelflg(int delflg){

		String sql ="SELECT * FROM book_sample where delflg = ?"; //バインドパラメータは？で指定
		List<Map<String,Object>> resultList = jdbctemplate.queryForList(sql,delflg);//第三引数でパラメータを指定
		//取得済みデータをエンティティにsetする
		List<BookEntity> bookList =new ArrayList<BookEntity>();
		for(Map<String,Object> result:resultList)
		{
			BookEntity book =new BookEntity();
			book.setId(Integer.parseInt(result.get("id").toString()));//なぜかLong型で取得されているのでLong型→String型→Int型でキャストをかける。
			book.setTitle((String)result.get("title"));
			book.setSummary((String)result.get("summary"));
			book.setClassification((String)result.get("classification"));
			book.setDelflg((int)result.get("delflg"));
			bookList.add(book);
		}

		return bookList;
	}

	public String findTitleByIdNameParam1(int id){

		String sql ="SELECT title FROM book_sample where id = :id"; //バインドパラメータは:カラム名で指定
		Map<String,Object> params =new HashMap<String,Object>();
		params.put("id", id); //カラム名とパラメータをセット
		return namedParameterJdbcTemplate.queryForObject(sql,params,String.class);
	}

	public String findTitleByIdNameParam2(int id,int delflg){

		String sql ="SELECT title FROM book_sample where id = :id and delflg= :delflg"; //バインドパラメータは:カラム名で指定
		MapSqlParameterSource map = new MapSqlParameterSource()
				.addValue("id",id)
				.addValue("delflg", delflg);
		return namedParameterJdbcTemplate.queryForObject(sql,map,String.class);//第三引数でパラメータを指定
	}

	public String findTitleByIdNameParam3(int id,int delflg){

		String sql ="SELECT title FROM book_sample where id = :id and delflg= :delflg"; //バインドパラメータは:カラム名で指定
		BookEntity bookentity = new BookEntity(id,"title","summary","classification",delflg);
		BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(bookentity);
		return namedParameterJdbcTemplate.queryForObject(sql,map,String.class);//第三引数でパラメータを指定
	}
}
