package com.elpmas.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.elpmas.test.domain.repository.JDBCtestRepository;



@Controller
public class SampleContoroller {

	@Autowired
	JDBCtestRepository jdbcTestRepository;

	@GetMapping("/sample")
    public String getSamplePage(Model model) {

		List bookAll = jdbcTestRepository.findAll();

		Integer id = jdbcTestRepository.findMaxId();

		String titleAll = jdbcTestRepository.findTitleById(1);
		Object[] args = new Object[] {2,0};
		String titleNotDel = jdbcTestRepository.findTitleByIdAndDelFlg(args);

		model.addAttribute("id",id);
		model.addAttribute("bookAll",bookAll);
		model.addAttribute("titleAll",titleAll);
		model.addAttribute("titleNotDel",titleNotDel);
		model.addAttribute("book",jdbcTestRepository.findBookById(1));

		model.addAttribute("bookList",jdbcTestRepository.findBooksByDelflg(0));
		model.addAttribute("title1",jdbcTestRepository.findTitleByIdNameParam1(1));
		model.addAttribute("title2",jdbcTestRepository.findTitleByIdNameParam2(1,0));
		model.addAttribute("title3",jdbcTestRepository.findTitleByIdNameParam3(1,0));

		model.addAttribute("insert",jdbcTestRepository.InsertBook(0));
		model.addAttribute("update",jdbcTestRepository.UpdateBook(1,1));
		model.addAttribute("delete",jdbcTestRepository.DeleteBook(1,1));

        return "sample";
    }
}
