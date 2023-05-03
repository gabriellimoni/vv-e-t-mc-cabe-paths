package com.vvet.mccabe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.Node;
import com.Path;

public class McCabeRunner {
  private String name;
  private List<Path> paths;
  private Path basePath;
  private Set<Node> activatedNodes;

  public McCabeRunner(String name) {
    this.name = name;
    activatedNodes = new HashSet<>();
    paths = new ArrayList<>();
  }
  public McCabeRunner withPaths(List<Path> paths) {
    this.paths = paths;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public void addPath(Path path) {
    this.paths.add(path);
  }
  public Path getBasePath() {
    return this.basePath;
  }
  public void setBasePath(Path basePath) {
    this.basePath = basePath;
  }

  public void wentThrough(Node node) {
    activatedNodes.add(node);
  }
}
