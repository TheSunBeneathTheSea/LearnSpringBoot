package com.learn.springboot.web;
import com.learn.springboot.config.auth.LoginUser;
import com.learn.springboot.config.auth.dto.SessionUser;
import com.learn.springboot.service.posts.PostsService;
import com.learn.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/view/{id}")
    public String postsView(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-view";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/notice")
    public String notice(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findBoardDesc("notice"));
        model.addAttribute("boardName", "notice");

        return "board";
    }

    @GetMapping("/free")
    public String board(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findBoardDesc("free"));
        model.addAttribute("boardName", "free");

        return "board";
    }

}
