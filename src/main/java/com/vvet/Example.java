package com.vvet;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;

import java.io.File;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

public class Example 
{
    public static void main( String[] args )
    {
      try {
        Graph g = graph("example1").directed()
          .graphAttr().with(Rank.dir(RankDir.LEFT_TO_RIGHT))
          .linkAttr().with("class", "link-class")
          .with(
                  node("a").with(Color.RED).link(node("b")),
                  node("b").link(
                          to(node("c"))
                  )
          );
        Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(new File("example/ex1.png"));
      } catch (Exception e) {}
    }
}
