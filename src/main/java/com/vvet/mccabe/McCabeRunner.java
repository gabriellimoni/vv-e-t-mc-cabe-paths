package com.vvet.mccabe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.Graph;
import com.Link;
import com.Node;
import com.Path;

public class McCabeRunner {
  private Graph graph;
  private String name;
  private Path basePath;
  private List<Path> paths;
  private Set<Link> activatedLinks = new HashSet<>();
  private List<Link> linksToIterate = new ArrayList<>();

  public McCabeRunner(Graph graph, String name) {
    this.graph = graph;
    this.name = name;
    paths = new ArrayList<>();
  }

  public void run() throws IOException {
    Integer complexity = graph.complexity();
    System.out.println("Total paths: " + complexity);
    
    Path path = getFirstPath();
    setBasePath(path);
    addPath(path);
    
    while (paths.size() < complexity) {
      if (linksToIterate.size() == 0) {
        List<Link> notUsedLinks = new ArrayList<>();
        for (Link l : graph.links()) {
          if (!activatedLinks.contains(l)) {
            notUsedLinks.add(l);
          }
        }
        Link nextLink = notUsedLinks.get(0);
        while (!basePath.nodes().contains(nextLink.from())) {
          basePath = paths.get(paths.indexOf(basePath) + 1);
        }
        Path secondaryPath = basePath.copyUntil(nextLink.from());
        secondaryPath.addLink(nextLink);
        wentThrough(nextLink);
        secondaryPath.addNode(nextLink.to());
        followBasicPath(nextLink.to(), secondaryPath);
        addPath(secondaryPath);
      } else {
        Link nextLink = linksToIterate.remove(0);
        Path secondaryPath = basePath.copyUntil(nextLink.from());
        secondaryPath.addLink(nextLink);
        wentThrough(nextLink);
        secondaryPath.addNode(nextLink.to());
        followBasicPath(nextLink.to(), secondaryPath);
        addPath(secondaryPath);
      }
    }

    for (Integer i = 1; i<=paths.size(); i++) {
      System.out.println(i + ") " + paths.get(i-1));
    }
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
        nextLink = getNextLink(path, links);
      }
      node = nextLink.to();

      path.addLink(nextLink);
      wentThrough(nextLink);
      path.addNode(node);
    } while (node.links().size() > 0);
  }
  private Link getNextLink(Path currentPath, List<Link> links) {
    Iterator<Link> it = links.iterator();
    Link nextLink = it.next();
    while(currentPath.linkAlreadyUsed(nextLink)) {
      nextLink = it.next();
    }
    List<Link> remainingLinks = new ArrayList<>(links);
    remainingLinks.remove(nextLink);
    for (Link l : remainingLinks) {
      if (!currentPath.linkAlreadyUsed(l)) {
        if (basePath == null) {
          linksToIterate.add(l);
        }
      }
    }
    return nextLink;
  }

  private void addPath(Path path) {
    this.paths.add(path);
  }

  private void setBasePath(Path path) {
    this.basePath = path;
  }

  private void wentThrough(Link link) {
    activatedLinks.add(link);
  }
}
