package com.elpmas.test.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="book_sample")
public class BookEntity {

	public BookEntity(int id, String title, String summary, String classification, int delflg) {
		this.id=id;
		this.title=title;
		this.summary=summary;
		this.classification=classification;
		this.delflg=delflg;
	}

	public BookEntity() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="title")
	private String title;

	@Column(name="summary")
	private String summary;

	@Column(name="classification")
	private String classification;

	@Column(name="delflg")
	private int delflg;

}
