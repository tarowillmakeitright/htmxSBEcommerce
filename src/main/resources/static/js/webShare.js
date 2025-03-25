function shareAnime() {
     // クリックされたボタン要素を取得
    const button = event.target;
     // 親要素 (card) を取得
    const card = button.closest('.card');
    //         // h5要素のテキストを取得
    const title = card.querySelector('.card-title').innerText;
    // 完全なURLを組み立て
    const url = window.location.href;

    // Web Share API が利用可能か確認
    if (navigator.share) {
        navigator.share({
            title: title,   // シェアのタイトル
            text: `おすすめのアニメ: ${title}`, // 動的にタイトルを挿入
            url: url        // シェアするURL
        })
        .then(() => console.log('Anime shared successfully!'))
        .catch((error) => console.error('Error sharing anime:', error));
    } else {
        alert('Web Share API is not supported on this device/browser.');
    }
}
