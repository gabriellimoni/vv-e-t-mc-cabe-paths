package com.vvet.mccabe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.Graph;
import com.Link;
import com.Node;
import com.Path;

public class McCabeRunner {
  private Graph graph;
  private String name;
  private List<Path> paths;
  private Path basePath;
  private Set<Node> activatedNodes;
  private Map<Path, List<Link>> decisionLinksFromPath;
  private List<Path> pathsAlreadyChangedDecision;
  
  public void run() {
    for (Integer i = 0; i < 3; i++) {
      Path path = new Path(graph);
      Node node;
      if (basePath != null) {
        node = getLastNotSelectedDecision(path).from();
        path = basePath.copyUntil(node);
      } else {
        node = graph.getFirstNode();
        path.addNode(node);
      }
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
        path.addNode(node);
      } while (node.links().size() > 0);
      addPath(path);
      System.out.println(path);
    }
  }

  public void run2() {
    for (Integer i = 0; i < 1; i++) {
      Path path = new Path(graph);
      Node node = null;
      if (basePath != null) {
        
      } else {
        node = graph.getFirstNode();
        path.addNode(node);
      }
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
        path.addNode(node);
      } while (node.links().size() > 0);
      addPath(path);
      System.out.println(path);
    }
  }

  public McCabeRunner(Graph graph, String name) {
    this.graph = graph;
    this.name = name;
    decisionLinksFromPath = new HashMap<>();
    activatedNodes = new HashSet<>();
    paths = new ArrayList<>();
    pathsAlreadyChangedDecision = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  private void addPath(Path path) {
    if (this.paths.size() == 0) setBasePath(path);
    this.paths.add(path);
  }
  private Path getBasePath() {
    return this.basePath;
  }
  private void setBasePath(Path basePath) {
    this.basePath = basePath;
  }
  private void wentThrough(Node node) {
    activatedNodes.add(node);
  }

  private Link getNextLink(Path currentPath, List<Link> links) {
    Iterator<Link> it = links.iterator();
    Link nextLink = it.next();
    while(currentPath.edgeAlreadyUsed(nextLink)) {
      nextLink = it.next();
    }
    if (pathsAlreadyChangedDecision.contains(currentPath)) return nextLink;
    if (basePath == null) return nextLink;

    Link lastDecision = getLastNotSelectedDecision(currentPath);
    while (lastDecision == null) {
      setBasePath(paths.get(paths.indexOf(basePath) + 1));
      lastDecision = getLastNotSelectedDecision(currentPath);
    }

    return lastDecision;
  }

  private Link getLastNotSelectedDecision(Path currentPath) {
    List<Node> decisionNodes = new ArrayList<>();
    for (Node n : basePath.nodes()) {
      if (n.links().size() > 1) decisionNodes.add(n);
    }
    while (decisionNodes.size() > 0) {
      Node n = decisionNodes.remove(decisionNodes.size() - 1);
      for (Link l : n.links()) {
        if (!basePath.edgeAlreadyUsed(l)) {
          List<Link> basePathUsedLinks = decisionLinksFromPath.get(basePath);
          if (basePathUsedLinks == null || !basePathUsedLinks.contains(l)) {
            if (basePathUsedLinks == null) {
              basePathUsedLinks = new ArrayList<>();
              decisionLinksFromPath.put(basePath, basePathUsedLinks);
            }
            basePathUsedLinks.add(l);
            pathsAlreadyChangedDecision.add(currentPath);
            return l;
          }
        }
      }
    }
    return null;
  }
}
