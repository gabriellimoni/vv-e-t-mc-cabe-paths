package com.vvet;

import com.Graph;
import com.parser.CustomDotParser;
import com.vvet.mccabe.McCabeRunner;
import com.vvet.mccabe.McCabeRunner2;
import com.vvet.mccabe.McCabeRunner3;

import guru.nidi.graphviz.model.MutableGraph;

public class App 
{
    public static void main( String[] args )
    {
      // try {
      //   Example.main(args);
      //   MutableGraph g = DotParser.parse("src/assets/example.dot");
      //   McCabe.run("", g);
      // } catch (Exception e) {
      //   System.out.println(e);
      // }

      // try {
      //   Graph g = CustomDotParser.parse("src/assets/example.dot");
      //   McCabe.run2("cal", g);
      // } catch (Exception e) {
      //   System.out.println(e);
      // }

      try {
        Graph g = CustomDotParser.parse("src/assets/example.dot");
        new McCabeRunner3(g, "name").run();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
}
