package com.vvet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;;

public class DotParser {
  static MutableGraph parse(String path) throws FileNotFoundException, IOException {
    InputStream dot = new FileInputStream(path);
    MutableGraph g = new Parser().read(dot);
    return g;
  }
}
