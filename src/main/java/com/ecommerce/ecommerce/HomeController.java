package com.ecommerce.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String index(Model model) {
        return "index";
    }

  @Autowired
    private AnimeService animeService;

    @GetMapping("/animeList")
    public String showAnimeList(Model model) {
        List<Anime> animeList = animeService.getAllAnime();
        System.out.println(animeList);
        model.addAttribute("list", animeList);
        return "animeList"; // 使用するThymeleafのテンプレート名
    }

}
