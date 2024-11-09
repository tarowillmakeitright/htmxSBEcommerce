package com.ecommerce.ecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    private static final Logger logger = LoggerFactory.getLogger(AnimeService.class);
    // MongoDBからすべてのアニメを取得
    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }

    // 追加: IDで特定のアニメを取得（オプション）
    /*public Anime getAnimeById(String id) {
        return animeRepository.findById(id).orElse(null);
    }

    // 追加: 新しいアニメを保存
    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    // 追加: アニメを削除
    public void deleteAnime(String id) {
        animeRepository.deleteById(id);
    }

     */
    // 特定のタグでフィルタリングされたアニメを取得
    public List<Anime> getAnimeByTag(String tag) {
        return animeRepository.findAll().stream()
                .filter(anime -> anime.getTags() != null && anime.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public Anime getAnimeById(String id) {
        Optional<Anime> anime = animeRepository.findById(id);
        return anime.orElse(null);  // IDに対応するアニメが見つからなければnullを返す
    }





    //タグを全表示させる
    public List<String> getAllUniqueTags() {
       return animeRepository.findAll().stream()
               .flatMap(anime -> anime.getTags().stream())
               .distinct()
               .collect(Collectors.toList());
    }


//    public void addComment(String animeId, String content) {
//        Anime anime = animeRepository.findById(animeId).orElse(null);
//        if (anime != null) {
//            Comment newComment = new Comment(content);
//            anime.getComments().add(newComment);
//            animeRepository.save(anime);
//        }
//    }
}
