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

	public Boolean InsertBook(int delflg){

		Boolean successFlg = false;

		String sql ="INSERT \r\n"
				+ "INTO book_sample \r\n"
				+ "VALUES ( \r\n"
				+ "    5\r\n"
				+ "    , 'わかばちゃんと学ぶ Git使い方入門'\r\n"
				+ "    , '本書は、マンガと実践で学ぶGitの入門書です。Gitの概念はもちろん、GitHubやBitbucketについても丁寧に解説しています。これからGitを使い始める人にオススメの1冊です。'\r\n"
				+ "    ,'技術書'\r\n"
				+ "    ,:delflg\r\n"//バインドパラメータは:カラム名で指定
				+ ");";
		Map<String,Object> params =new HashMap<String,Object>();
		params.put("delflg", delflg); //カラム名とパラメータをセット
		int updateCount = namedParameterJdbcTemplate.update(sql,params); //戻り値は更新対象レコード数
		if(updateCount>0)//0の時は更新が上手くいかなかったと判断できる
		{
			successFlg=true;
		}
		return successFlg;
	}

	public Boolean UpdateBook(int id,int delflg){

		Boolean successFlg = false;

		String sql ="UPDATE book_sample\r\n"
				+ "SET delflg=:delflg\r\n"
				+ "WHERE id=:id;";
		MapSqlParameterSource map = new MapSqlParameterSource()
				.addValue("id",id)
				.addValue("delflg", delflg);

		int updateCount = namedParameterJdbcTemplate.update(sql,map); //戻り値は更新対象レコード数
		if(updateCount>0)//0の時は更新が上手くいかなかったと判断できる
		{
			successFlg=true;
		}
		return successFlg;
	}

	public Boolean DeleteBook(int id,int delflg){

		Boolean successFlg = false;

		String sql ="DELETE FROM book_sample \r\n"
				+ "WHERE\r\n"
				+ "    id = :id \r\n"
				+ "    AND delflg = :delflg;"; //バインドパラメータは:カラム名で指定
		MapSqlParameterSource map = new MapSqlParameterSource()
				.addValue("id",id)
				.addValue("delflg", delflg);

		int updateCount = namedParameterJdbcTemplate.update(sql,map); //戻り値は更新対象レコード数
		if(updateCount>0)//0の時は更新が上手くいかなかったと判断できる
		{
			successFlg=true;
		}
		return successFlg;
	}

	//////////////////RowMapperを使用した実装//////////////////
	public BookEntity GetBookById(int id) {
		String sql ="SELECT * FROM book_sample where id =?"; //バインドパラメータは:カラム名で指定
		BookRowMapper rowMapper = new BookRowMapper();
		return jdbctemplate.queryForObject(sql, rowMapper,id); //指定のEntityへの変換をrowMapperが自動で行う。
	}

	public List<BookEntity> GetAllBook()
	{
		String sql ="SELECT * FROM book_sample";
		BookRowMapper rowMapper = new BookRowMapper();
		return jdbctemplate.query(sql, rowMapper); //指定のEntityへの変換をrowMapperが自動で行う。
	}
///////////////////////////////////////////////////////////////
}
