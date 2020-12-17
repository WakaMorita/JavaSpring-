package com.elpmas.test.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SpringSecurityを利用するための設定クラス
 * ログイン処理でのパラメータ、画面遷移や認証処理でのデータアクセス先を設定する
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 認可設定を無視するリクエストを設定
     * 静的リソース(image,javascript,css)を認可処理の対象から除外する
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        		.antMatchers("/resources/**");
    }

    /**
     * 認証・認可の情報を設定する
     * 画面遷移のURL・パラメータを取得するname属性の値を設定
     * SpringSecurityのconfigureメソッドをオーバーライドしています。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/login").permitAll()
                .anyRequest().authenticated();

        http
        	.formLogin()
                .defaultSuccessUrl("/sample", true);
    }

}
