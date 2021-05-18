package com.learn.springboot.web;
import com.learn.springboot.config.auth.LoginUser;
import com.learn.springboot.config.auth.dto.SessionUser;
import com.learn.springboot.service.posts.PostsService;
import com.learn.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        httpSession.setAttribute("viewCountCookie", new ArrayList<Long>());
        return "index";
    }

    @GetMapping("/register")
    public String register(){

        return "register";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        return "posts-save";
    }

    @GetMapping("/posts/view/{id}")
    public String postsView(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        ArrayList<Long> seenList = (ArrayList<Long>) httpSession.getAttribute("viewCountCookie");
        boolean alreadySeen = seenList.contains(id);

        if(!alreadySeen) {
            seenList.add(id);
            httpSession.setAttribute("viewCountCookie", seenList);
        }

        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        PostsResponseDto dto = postsService.findById(id, alreadySeen);
        model.addAttribute("post", dto);

        return "posts-view";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        PostsResponseDto dto = postsService.findById(id, true);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/notice")
    public String notice(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        model.addAttribute("posts", postsService.findBoardDesc("notice"));
        model.addAttribute("boardName", "notice");

        return "board";
    }

    @GetMapping("/free")
    public String board(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        model.addAttribute("posts", postsService.findBoardDesc("free"));
        model.addAttribute("boardName", "free");

        return "board";
    }

}
