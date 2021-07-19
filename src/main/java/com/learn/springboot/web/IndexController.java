package com.learn.springboot.web;
import com.learn.springboot.config.auth.LoginUser;
import com.learn.springboot.config.auth.dto.SessionUser;
import com.learn.springboot.service.member.MemberService;
import com.learn.springboot.service.posts.PostsService;
import com.learn.springboot.service.trading.TradingService;
import com.learn.springboot.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final TradingService tradingService;
    private final HttpSession httpSession;
    private final MemberService memberService;

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

    @GetMapping("/trading")
    public String trading(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }
        model.addAttribute("stocks", tradingService.findAllStockPrice());

        return "trading";
    }

    @GetMapping("/member")
    public String member(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
            if(memberService.isNotRegisteredUser(user.getId())){
                model.addAttribute("member", memberService.findMemberByUserId(user.getId()));
                model.addAttribute("companyCodeAndName", tradingService.findAllCompanyCodeAndName());
            }
        }


        return "member";
    }

    @GetMapping("/member/create")
    public String setting(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginUserName", user.getName());
        }


        return "member-create";
    }

}
