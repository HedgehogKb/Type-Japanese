package com.hedgehogkb.typejapanese

class RomanjiChecker {
    private val allHiraganaMap =  HashMap<String, List<String>>()
    private val allKatakanaMap =  HashMap<String, List<String>>()

    init{
        println("hiragana:")
        fillHiraganaMap()
        println("katakana:")
        fillKatakanaMap()
    }

    private fun fillHiraganaMap() {
        allHiraganaMap.clear()
        allHiraganaMap.putAll(simpleHiraganaMap)

        // Add all regular yoon and dakoun yoon to the list
        for (kana in hiraganaYoonList) {
            val curRomanji = simpleHiraganaMap.get(kana)?.get(0)
                ?: throw Exception("problem with hiragana: $kana")
            for ((yKana, yRomanji) in smallYHiraganaMap) {
                val curYRomanji = yRomanji[0]
                val yoonKana = "$kana$yKana"
                val yoonRomanji = "${curRomanji.substring(0,1)}$curYRomanji"
                println("kana: $yoonKana, romanji: $yoonRomanji")
                allHiraganaMap[yoonKana] = listOf(yoonRomanji)
            }
        }

        // Add all exception yoon and dakoun yoon to the list
        for (kana in hiraganaYoonExceptions) {
            val curRomanji = simpleHiraganaMap[kana]?.get(0)?.dropLast(1)
                ?: throw Exception("problem with hiragana: $kana")
            for ((yKana, yRomanji) in smallYHiraganaMap) {
                val curYRomanji = yRomanji[0].substring(1)
                val yoonKana = "$kana$yKana"
                val yoonRomanji = "$curRomanji$curYRomanji"
                println("kana: $yoonKana, romanji: $yoonRomanji")
                allHiraganaMap[yoonKana] = listOf(yoonRomanji)
            }
        }
    }

    private fun fillKatakanaMap() {
        allKatakanaMap.clear()
        allKatakanaMap.putAll(simpleKatakanaMap)

        // Add all regular yoon and dakoun yoon to the list
        for (kana in katakanaYoonList) {
            val curRomanji = simpleKatakanaMap.get(kana)?.get(0)
                ?: throw Exception("problem with katakana: $kana")
            for ((yKana, yRomanji) in smallYKatakanaMap) {
                val curYRomanji = yRomanji[0]
                val yoonKana = "$kana$yKana"
                val yoonRomanji = "${curRomanji.substring(0,1)}$curYRomanji"
                println("kana: $yoonKana, romanji: $yoonRomanji")
                allKatakanaMap[yoonKana] = listOf(yoonRomanji)
            }
        }

        // Add all exception yoon and dakoun yoon to the list
        for (kana in katakanaYoonExceptions) {
            val curRomanji = simpleKatakanaMap.get(kana)?.get(0)?.dropLast(1)
                ?: throw Exception("problem with katakana: $kana")
            for ((yKana, yRomanji) in smallYKatakanaMap) {
                val curYRomanji = yRomanji[0].substring(1)
                val yoonKana = "$kana$yKana"
                val yoonRomanji = "$curRomanji$curYRomanji"
                println("kana: $yoonKana, romanji: $yoonRomanji")
                allKatakanaMap[yoonKana] = listOf(yoonRomanji)
            }
        }
    }

    fun getHiraganaMap(): Map<String, List<String>> {
        return allHiraganaMap
    }

    fun getKatakanaMap(): Map<String, List<String>> {
        return allKatakanaMap
    }

    /**
     * Determines if a character is hiragana.
     * Checks the character's code against the range of Unicode hiragana characters
     * @param c the character checked for being hiragana
     * @return Boolean if c is hiragana
     */
    private fun isHiragana(c: Char): Boolean {
        return c.code in 0x3040..0x309F
    }

    /**
     * Determines if a character is katakana.
     * Checks the character's code against the range of Unicode katakana characters
     * @param c the character checked for being katakana
     * @return Boolean if c is katakana
     */
    private fun isKatakana(c: Char): Boolean {
        return c.code in 0x30A0..0x30FF
    }

    /**
     * Determines if a passed in romanji string matches a known kana string.
     * @throws IllegalArgumentException if the kana string is empty
     * @throws IllegalArgumentException if the passed kana isn't in the known list
     */
    fun doesRomanjiMatchKana(kana: String, romanji: String): Boolean {
        if (kana.isEmpty()) {
            throw IllegalArgumentException("Passed kana is empty")
        }
        if (isHiragana(kana[0])) {
            return (romanji.lowercase() in allHiraganaMap.getValue(kana))
        }
        if (isKatakana(kana[0])) {
            return (romanji.lowercase() in allKatakanaMap.getValue(kana))
        }
        throw IllegalArgumentException("Kana doesn't exist in list!")
    }
    companion object {
        private val simpleHiraganaMap = mapOf(
            "あ" to listOf("a"), "い" to listOf("i"), "う" to listOf("u"), "え" to listOf("e"), "お" to listOf("o"),
            "か" to listOf("ka"), "き" to listOf("ki"), "く" to listOf("ku"), "け" to listOf("ke"), "こ" to listOf("ko"),
            "さ" to listOf("sa"), "し" to listOf("shi", "si"), "す" to listOf("su"), "せ" to listOf("se"), "そ" to listOf("so"),
            "た" to listOf("ta"), "ち" to listOf("chi", "ti"), "つ" to listOf("tsu", "tu"), "て" to listOf("te"), "と" to listOf("to"),
            "な" to listOf("na"), "に" to listOf("ni"), "ぬ" to listOf("nu"), "ね" to listOf("ne"), "の" to listOf("no"),
            "は" to listOf("ha"), "ひ" to listOf("hi"), "ふ" to listOf("fu", "hu"), "へ" to listOf("he"), "ほ" to listOf("ho"),
            "ま" to listOf("ma"), "み" to listOf("mi"), "む" to listOf("mu"), "め" to listOf("me"), "も" to listOf("mo"),
            "や" to listOf("ya"), "ゆ" to listOf("yu"), "よ" to listOf("yo"),
            "ら" to listOf("ra"), "り" to listOf("ri"), "る" to listOf("ru"), "れ" to listOf("re"), "ろ" to listOf("ro"),
            "わ" to listOf("wa"), "を" to listOf("wo", "o"), "ん" to listOf("n"),
            "が" to listOf("ga"), "ぎ" to listOf("gi"), "ぐ" to listOf("gu"), "げ" to listOf("ge"), "ご" to listOf("go"),
            "ざ" to listOf("za"), "じ" to listOf("ji", "zi"), "ず" to listOf("zu"), "ぜ" to listOf("ze"), "ぞ" to listOf("zo"),
            "だ" to listOf("da"), "ぢ" to listOf("ji", "di"), "づ" to listOf("zu", "du"), "で" to listOf("de"), "ど" to listOf("do"),
            "ば" to listOf("ba"), "び" to listOf("bi"), "ぶ" to listOf("bu"), "べ" to listOf("be"), "ぼ" to listOf("bo"),
            "ぱ" to listOf("pa"), "ぴ" to listOf("pi"), "ぷ" to listOf("pu"), "ぺ" to listOf("pe"), "ぽ" to listOf("po")
        )

        private val simpleKatakanaMap = mapOf(
            "ア" to listOf("a"), "イ" to listOf("i"), "ウ" to listOf("u"), "エ" to listOf("e"), "オ" to listOf("o"),
            "カ" to listOf("ka"), "キ" to listOf("ki"), "ク" to listOf("ku"), "ケ" to listOf("ke"), "コ" to listOf("ko"),
            "サ" to listOf("sa"), "シ" to listOf("shi", "si"), "ス" to listOf("su"), "セ" to listOf("se"), "ソ" to listOf("so"),
            "タ" to listOf("ta"), "チ" to listOf("chi", "ti"), "ツ" to listOf("tsu", "tu"), "テ" to listOf("te"), "ト" to listOf("to"),
            "ナ" to listOf("na"), "ニ" to listOf("ni"), "ヌ" to listOf("nu"), "ネ" to listOf("ne"), "ノ" to listOf("no"),
            "ハ" to listOf("ha"), "ヒ" to listOf("hi"), "フ" to listOf("fu", "hu"), "ヘ" to listOf("he"), "ホ" to listOf("ho"),
            "マ" to listOf("ma"), "ミ" to listOf("mi"), "ム" to listOf("mu"), "メ" to listOf("me"), "モ" to listOf("mo"),
            "ヤ" to listOf("ya"), "ユ" to listOf("yu"), "ヨ" to listOf("yo"),
            "ラ" to listOf("ra"), "リ" to listOf("ri"), "ル" to listOf("ru"), "レ" to listOf("re"), "ロ" to listOf("ro"),
            "ワ" to listOf("wa"), "ヲ" to listOf("wo", "o"), "ン" to listOf("n", "nn"),
            "ガ" to listOf("ga"), "ギ" to listOf("gi"), "グ" to listOf("gu"), "ゲ" to listOf("ge"), "ゴ" to listOf("go"),
            "ザ" to listOf("za"), "ジ" to listOf("ji", "zi"), "ズ" to listOf("zu"), "ゼ" to listOf("ze"), "ゾ" to listOf("zo"),
            "ダ" to listOf("da"), "ヂ" to listOf("ji", "di"), "ヅ" to listOf("zu", "du"), "デ" to listOf("de"), "ド" to listOf("do"),
            "バ" to listOf("ba"), "ビ" to listOf("bi"), "ブ" to listOf("bu"), "ベ" to listOf("be"), "ボ" to listOf("bo"),
            "パ" to listOf("pa"), "ピ" to listOf("pi"), "プ" to listOf("pu"), "ペ" to listOf("pe"), "ポ" to listOf("po")
        )

        private val smallYHiraganaMap = mapOf(
            "ゃ" to listOf("ya"),"ゅ" to listOf("yu"), "ょ" to listOf("yo"),
        )
        private val smallYKatakanaMap = mapOf(
            "ャ" to listOf("ya"), "ュ" to listOf("yu"), "ョ" to listOf("yo"),
        )

        private val hiraganaYoonList: List<String> = listOf(
            "き","ぎ","に","ひ","び","ぴ","み","り",
        )
        private val hiraganaYoonExceptions = listOf(
            "し","じ","ち","ぢ",
        )
        private val katakanaYoonList: List<String> = listOf(
            "キ","ギ","ニ","ヒ","ビ","ピ","ミ","リ",
        )
        private val katakanaYoonExceptions = listOf(
            "シ","ジ","チ","ヂ",
        )
    }
}
