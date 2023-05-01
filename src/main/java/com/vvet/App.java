package com.vvet;

import com.Graph;
import com.parser.CustomDotParser;

import guru.nidi.graphviz.model.MutableGraph;

public class App 
{
    public static void main( String[] args )
    {
      // try {
      //   Example.main(args);
      //   MutableGraph g = DotParser.parse("src/assets/example.dot");
      //   McCabe.run("cal", g);
      // } catch (Exception e) {
      //   System.out.println(e);
      // }

      try {
        Graph g = CustomDotParser.parse("src/assets/example.dot");
        McCabe.run2("cal", g);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
}
