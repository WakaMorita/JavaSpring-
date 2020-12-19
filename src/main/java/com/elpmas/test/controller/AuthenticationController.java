package com.elpmas.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController {

	@GetMapping("/login")
    public String getLogin(Model model) {
        return "login";
    }

    @GetMapping("/eroor")
    public String getLoginError(Model model) {
    	model.addAttribute("ErrorMessage","*ユーザー名もしくはパスワードが一致しません");
        return "login";
    }

    //FormのSubmitを押すとPostメソッドがリクエストされます。（）に書かれたURLのリクエストを受け取るとこのメソッドが発動します。
    @PostMapping("/login")
    public String postLogin(Model model) {

        return "sample";
    }
}