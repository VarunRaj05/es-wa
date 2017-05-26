package ja.temp

import java.util.regex.{Matcher, Pattern}

object TestMe {
  def main(args: Array[String]): Unit = {
/*    val html = "<html><head><title>First parse</title></head>" + "<body><p>Parsed HTML into a doc.</p></body></html>"
    val doc = Jsoup.parse(html) */
val dataLinePattern = Pattern.compile("(\\d{14})")

        val line = "12345678901234 12345678"
        val matcher: Matcher = dataLinePattern.matcher(line)
        println(matcher.toMatchResult)



    import org.jsoup.Jsoup
    val html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>"
    val doc = Jsoup.parse(html)
    val text = doc.body.text


    println("*****wikipedia")
    val doc2 = Jsoup.connect("http://en.wikipedia.org/").get
    //val newsHeadlines = doc.select("#mp-itn b a")
    val newsHeadlines = doc2.select("#mp-itn b a")
    println(newsHeadlines)
    println("*****wikipedia text")
    val html1 = doc2
    val doc1 = doc
    val text1 = doc1.body.text

    println(text)
    println(text1)
  }
}
