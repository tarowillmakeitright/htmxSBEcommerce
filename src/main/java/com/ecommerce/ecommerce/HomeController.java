package com.ecommerce.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private boolean isRed = true;

    @GetMapping
    public String index(Model model) {
       String color = isRed ? "red" : "blue";
       isRed = !isRed;
       model.addAttribute("color", color);
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
    // 特定のタグでアニメをフィルタリングするメソッド
    @GetMapping("/animeList/tags/{tag}")
    public String showAnimeByTag(@PathVariable("tag") String tag, Model model) {
        List<Anime> animeListByTag = animeService.getAnimeByTag(tag);
        model.addAttribute("list", animeListByTag);
        model.addAttribute("selectedTag", tag); // 現在選択されているタグを表示するための変数
        return "animeList"; // animeListテンプレートを再利用
    }
//    @PostMapping("/add")
//    public String addComment(@RequestParam("animeId") String animeId, @RequestParam("content") String content) {
//        animeService.addComment(animeId, content); // コメント追加処理をサービスで実行
//        return "redirect:/comments/" + animeId; // コメントページへリダイレクト
//    }

    // アニメIDに基づくコメントページ表示
    @GetMapping("/animeList/comments/{id}")
    public String showCommentsPage(@PathVariable("id") String id, Model model) {
        // AnimeオブジェクトをIDで取得
        Anime anime = animeService.getAnimeById(id);

        // 取得したアニメ情報をモデルに追加
        model.addAttribute("anime", anime);

        // comments.htmlテンプレートに遷移
        return "comments";
    }

}
