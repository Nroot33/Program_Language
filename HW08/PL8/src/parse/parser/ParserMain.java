package parse.parser;

import java.io.File;

public class ParserMain {
    public static final void main(String... args) throws Exception {
        ClassLoader cloader = ParserMain.class.getClassLoader();
        File file = new File(cloader.getResource("parse/parser/as07.txt").getFile());

        CuteParser cuteParser = new CuteParser(file);
        NodePrinter nodePrinter = new NodePrinter(cuteParser.parseExpr());
        nodePrinter.prettyPrint();
    }
}
