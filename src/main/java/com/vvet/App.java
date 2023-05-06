package com.vvet;

import com.Graph;
import com.parser.CustomDotParser;
import com.vvet.mccabe.McCabeRunner;

public class App 
{
    public static void main( String[] args )
    {
      try {
        Graph g = CustomDotParser.parse("src/assets/example.dot");
        System.out.println(g);
        new McCabeRunner(g, "name").run();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
}
