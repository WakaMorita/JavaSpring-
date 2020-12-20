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
		model.addAttribute("bookAll",bookAll);

        return "sample";
    }
}
