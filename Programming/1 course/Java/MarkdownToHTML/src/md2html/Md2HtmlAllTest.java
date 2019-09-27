package md2html;

import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Md2HtmlAllTest extends Md2HtmlTest {
    static {
        TAGS.put("~", "mark");
        TAGS.put("++", "u");
    }

    protected void test() {
        test("[ссылок с _выделением_](https://kgeorgiy.info)", "<p><a href='https://kgeorgiy.info'>ссылок с <em>выделением</em></a></p>");
        test("[ссылка с __выделением__](https://kgeorgiy.info)", "<p><a href='https://kgeorgiy.info'>ссылка с <strong>выделением</strong></a></p>");
        test("[ссылка без выделения](https://kgeorgiy.info)", "<p><a href='https://kgeorgiy.info'>ссылка без выделения</a></p>");
        test("[ссылка без выделения](https://hello__kgeorgiy.info)", "<p><a href='https://hello__kgeorgiy.info'>ссылка без выделения</a></p>");

        test("![картинок](http://www.ifmo.ru/images/menu/small/p10.jpg)", "<p><img alt='картинок' src='http://www.ifmo.ru/images/menu/small/p10.jpg'></p>");
        test("![картинка](https://kgeorgiy.info)", "<p><img alt='картинка' src='https://kgeorgiy.info'></p>");
        test("![картинка с __псевдо-выделением__](https://kgeorgiy.info)", "<p><img alt='картинка с __псевдо-выделением__' src='https://kgeorgiy.info'></p>");

        test("_выделение [ссылка с __выделением__](https://kgeorgiy.info)_", "<p><em>выделение <a href='https://kgeorgiy.info'>ссылка с <strong>выделением</strong></a></em></p>");
        test("~выделение~", "<p><mark>выделение</mark></p>");
        test("++подчеркивание++", "<p><u>подчеркивание</u></p>");

        super.test();

        final String[] markup = {"_", "**", "`", "--", "[", "![", "~", "++"};
        randomTest(100, 100, markup);
        randomTest(100, 1000, markup);
        randomTest(100, 100000, markup);
    }

    @Override
    protected void special(final List<String> markup, final StringBuilder input, final StringBuilder output, final String type) {
        if ("[".equals(type)) {
            final StringBuilder in = new StringBuilder();
            final StringBuilder out = new StringBuilder();

            final boolean removed = markup.remove("![");
            generate(markup, in, out);

            final StringBuilder href = new StringBuilder();
            generate(markup, href, new StringBuilder());
            if (removed) {
                markup.add("![");
            }

            input.append("[").append(in).append("](").append(href).append(')');
            output.append("<a href='").append(href).append("'>").append(out).append("</a>");
        } else {
            final StringBuilder alt = new StringBuilder();
            final StringBuilder src = new StringBuilder();

            final boolean removed = markup.remove("[");
            generate(markup, alt, new StringBuilder());
            generate(markup, src, new StringBuilder());
            if (removed) {
                markup.add("[");
            }

            input.append("![").append(alt).append("](").append(src).append(')');
            output.append("<img alt='").append(alt).append("' src='").append(src).append("'>");
        }
    }

    public static void main(final String... args) {
        new Md2HtmlAllTest().run();
    }
}
