package com.vvet.mccabe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.Graph;
import com.Link;
import com.Node;
import com.Path;

public class McCabeRunner {
  private Graph graph;
  private String name;
  private Path basePath;
  private List<Path> paths;
  private List<Link> missingLinks = new ArrayList<>();

  public McCabeRunner(Graph graph, String name) {
    this.graph = graph;
    this.name = name;
    paths = new ArrayList<>();
  }

  public List<Path> run() throws IOException {
    Integer complexity = graph.complexity();
    System.out.println("Total paths: " + complexity);
    
    Path path = getFirstPath();
    setBasePath(path);
    addPath(path);
    
    while (paths.size() < complexity) {
      Link nextLink = getNextDecisionLink();
      Path secondaryPath = basePath.copyUntil(nextLink.from());
      secondaryPath.addLink(nextLink);
      secondaryPath.addNode(nextLink.to());
      followBasicPath(nextLink.to(), secondaryPath);
      addPath(secondaryPath);
    }

    for (Integer i = 1; i<=paths.size(); i++) {
      System.out.println(i + ") " + paths.get(i-1));
    }
    return paths;
  }

  private Path getFirstPath() throws IOException {
    Path path = new Path(graph);
    Node node = graph.getFirstNode();
    path.addNode(node);
    followBasicPath(node, path);
    return path;
  }
  private void followBasicPath(Node node, Path path) {
    do {
      List<Link> links = node.links();
      Link nextLink;
      if (links.size() == 0) {
        continue;
      }
      else if (links.size() == 1) {
        nextLink = links.get(0);
      } else {
        nextLink = getNextBasicLink(path, links);
      }
      node = nextLink.to();

      path.addLink(nextLink);
      path.addNode(node);
    } while (node.links().size() > 0);
  }
  private Link getNextBasicLink(Path currentPath, List<Link> links) {
    Iterator<Link> it = links.iterator();
    Link nextLink = it.next();
    missingLinks.remove(nextLink);
    while(currentPath.linkAlreadyUsed(nextLink)) {
      nextLink = it.next();
    }
    return nextLink;
  }

  private Link getNextDecisionLink() {
    Link nextLink = missingLinks.remove(0);
    while (!basePath.nodes().contains(nextLink.from())) {
      Integer nextIndex = paths.indexOf(basePath) + 1;
      if (nextIndex > paths.size() - 1 && paths.size() > 1) nextIndex = 0;
      setBasePath(paths.get(nextIndex));
    }
    return nextLink;
  }

  private void addPath(Path path) {
    this.paths.add(path);
  }

  private void setBasePath(Path path) {
    if (basePath == null) {
      for (Node n : graph.nodes()) {
        if (n.isDecisionPoint()) {
          if (path.nodes().contains(n)){
            missingLinks.addAll(n.links().subList(1, n.links().size()));
          } else {
            missingLinks.addAll(n.links().subList(0, n.links().size()));
          }
        }
      }
    }
    this.basePath = path;
  }

  public String getName() {
    return name;
  }
}
