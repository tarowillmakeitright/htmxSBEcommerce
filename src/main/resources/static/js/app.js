alert("hello world~");


function shareAnime(title, text, url) {
        // Web Share API が利用可能か確認
        if (navigator.share) {
            navigator.share({
                title: title,   // シェアのタイトル
                text: text,     // シェアするテキスト
                url: url        // シェアするURL
            })
            .then(() => console.log('Anime shared successfully!'))
            .catch((error) => console.error('Error sharing anime:', error));
        } else {
            alert('Web Share API is not supported on this device/browser.');
        }
    }
