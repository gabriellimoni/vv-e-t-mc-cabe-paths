package com.old;

import java.io.File;
import java.io.IOException;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

public class WriteGraph {
  static void write(MutableGraph g, String path) throws IOException {
    Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File(path));
  }
}
