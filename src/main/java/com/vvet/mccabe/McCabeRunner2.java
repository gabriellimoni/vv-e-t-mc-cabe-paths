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

public class McCabeRunner2 {
  private Graph graph;
  private String name;
  private List<Path> paths;
  private Path basePath;
  private Set<Node> activatedNodes;
  private Set<Link> activatedLinks;
  private Map<Path, List<Link>> decisionLinksFromPath;
  
  public void run() {
    while (graph.links().size() != activatedLinks.size()) {
      Path path = new Path(graph);
      Node node = null;

      if (basePath != null) {
        // copy graph, get next branch, set node and get the node from it
        Link link = getLastNotSelectedDecision();
        while (link == null) {
          setBasePath(paths.get(paths.indexOf(basePath) + 1));
          link = getLastNotSelectedDecision();
        }
        path = basePath.copyUntil(link.from());
        path.addLink(link);
        wentThrough(link);
        path.addNode(link.to());
        wentThrough(link.to());
        node = link.to();
      } else {
        node = graph.getFirstNode();
        path.addNode(node);
        wentThrough(node);
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
        wentThrough(nextLink);
        path.addNode(node);
        wentThrough(node);
      } while (node.links().size() > 0);

      addPath(path);
      System.out.println(path);
    }
  }

  public McCabeRunner2(Graph graph, String name) {
    this.graph = graph;
    this.name = name;
    activatedNodes = new HashSet<>();
    activatedLinks = new HashSet<>();
    decisionLinksFromPath = new HashMap<>();
    paths = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  private void addPath(Path path) {
    if (this.paths.size() == 0) setBasePath(path);
    this.paths.add(path);
  }
  private void setBasePath(Path basePath) {
    this.basePath = basePath;
    List<Link> electedLinks = new ArrayList<>();
    for (Link l : graph.links()) {
      for (Link pl : basePath.links()) {
        if (l.from() == pl.from()) {
          if (l.from().links().size() > 1) {
            if (!basePath.edgeAlreadyUsed(l))
              electedLinks.add(l);
          }
        }
      }
    }
    System.out.println("");
  }
  private void wentThrough(Node node) {
    activatedNodes.add(node);
  }
  private void wentThrough(Link link) {
    activatedLinks.add(link);
  }

  private Link getNextLink(Path currentPath, List<Link> links) {
    Iterator<Link> it = links.iterator();
    Link nextLink = it.next();
    while(currentPath.edgeAlreadyUsed(nextLink)) {
      nextLink = it.next();
    }
    return nextLink;
  }

  private Link getLastNotSelectedDecision() {
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
            return l;
          }
        }
      }
    }
    return null;
  }
}
