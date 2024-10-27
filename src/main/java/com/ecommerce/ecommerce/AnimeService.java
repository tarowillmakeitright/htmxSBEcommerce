package com.ecommerce.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

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
}
