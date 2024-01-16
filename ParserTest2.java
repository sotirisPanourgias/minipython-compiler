import java.io.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import minipython.lexer.Lexer;
import minipython.parser.Parser;
import minipython.node.Start;


public class ParserTest2
{
  public static void main(String[] args)
  {
    try
    {
      Parser parser = 
        new Parser(
        new Lexer(
        new PushbackReader(
        new FileReader(args[0].toString()), 1024)));

      Start ast = parser.parse();

	    visitor visitor = new visitor(new Hashtable(),new Hashtable());
      visitor1 visitor1 = new visitor1(new Hashtable<>(), new Hashtable<>());

      ast.apply(visitor);
      ast.apply(visitor1);

//      System.out.println(ast);
    }
    catch (Exception e)
    {
      System.err.println(e);
    }
  }
}

