package net.jxpress.kuromoji4s.ipadic

object Sample {

  val stopWords : Token => Boolean = {
    token =>
      (token.pos1, token.pos2) match {
        case (Some("名詞"), Some("引用文字列" | "動詞非自律的" | "数" | "接尾"| "代名詞" | "非自立" | "特殊" ))   => false
        case (Some("名詞"), _)   => true

        case _ => false
      }
  }

  val text = """ビジョン
    |テクノロジーで「ビジネスとジャーナリズムの両立」を実現する
    |ニューステクノロジーのリーディングカンパニー
    |JX通信社は、テクノロジーでニュースメディアの未来を創る、ニューステクノロジー（NewsTech）のリーディングカンパニーです。
    |国内で唯一、共同通信グループをはじめとしたマスコミと連携し、報道現場や消費者への接点となるメディア作りにテクノロジーサイドからコミットしているベンチャー企業でもあります。
    |そんな当社が実現する未来は「ビジネスとジャーナリズムの両立」。
    |インターネット普及以来、儲からなくなったと言われるメディアの新しい産業構造を作る― その先に、私たちの民主主義・自由社会の存続があります。
    |テクノロジーを通じて、労働集約的なメディアビジネスを自動化・機械化することは、かつて繊維産業から始まった産業革命と同じかそれ以上のインパクトをメディア分野にもたらすでしょう。
    |オンラインでも引き続き発生してきたあらゆる労働・金銭・時間コストを下げ、収益を最大化すること。そして、これをテクノロジーで実現することが、良質なジャーナリズムを存続させるための必要条件です。
    |学術分野で研究され続けながら十分活用されずに来た様々な要素技術を、ニュースメディア運営に携わる全ての方にとって使いやすいソリューションに進化させる。そして、最新テクノロジーとそのメディアビジネスへの活用の間にある「ミッシングリンク」を埋めていく。それが、NewsTechベンチャーである私たちJX通信社の仕事です。""".stripMargin.split("\n")


  def vectorizerSample() : Unit = {
    import net.jxpress.kuromoji4s.utils.vectorizer.ipadic.Implicits._
    import net.jxpress.kuromoji4s.utils.vectorizer.ipadic.Implicits.default._

    println("明日は明日の風邪がふく" cos "明日は明日の風がふく")

  }

  def main(args: Array[String]) : Unit = {

    val tokenizer = Tokenizer()

    println("-- A sample for print all results --")
    text.foreach {
      sentence =>
        tokenizer(sentence).foreach {
          token =>
          println (token.surface + "\t" + token.pos1.getOrElse("???"))
        }
    }

    println("-- A sample of stop words filtering --")

    text.foreach {
      sentence =>
        tokenizer(sentence).filter { stopWords }
          .map {
            token =>
              token.baseForm.getOrElse(token.surface) }
          .foreach { println }

    }

    println("-- A sample of the vectorizer")

    vectorizerSample()
  }
}
