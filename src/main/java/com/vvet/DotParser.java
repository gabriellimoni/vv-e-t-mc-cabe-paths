package com.vvet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;;

public class DotParser {
  static MutableGraph parse(String path) throws FileNotFoundException, IOException {
    InputStream dot = new FileInputStream(path);
    MutableGraph g = new Parser().read(dot);
    Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex4-1.png"));
    return g;
  }
}
