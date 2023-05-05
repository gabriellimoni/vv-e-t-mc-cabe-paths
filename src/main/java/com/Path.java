package com;

import java.util.ArrayList;
import java.util.List;

public class Path {
  private Graph graph;
  private List<Node> nodes = new ArrayList<>();
  private List<Link> links = new ArrayList<>();
  private List<Link> optedLinks = new ArrayList<>();
  private Link nextEdgeChange = null;
  private List<GraphItem> graphItems = new ArrayList();

  public Path(Graph graph) {
    this.graph = graph;
  }

  public void addNode(Node node) {
    this.nodes.add(node);
    this.graphItems.add(node);
  }
  @Deprecated
  public void addEdge(Link edge) {
    this.links.add(edge);
    this.graphItems.add(edge);
  }
  public void addLink(Link link) {
    this.links.add(link);
    this.graphItems.add(link);
  }

  public List<Node> nodes() {
    return nodes;
  }

  public List<Link> links() {
    return links;
  }

  public boolean edgeAlreadyUsed(Link edge) {
    if (links.contains(edge)) {
      return true;
    }
    return false;
  }

  public boolean edgeAlreadyOpted(Link edge) {
    if (optedLinks.contains(edge)) {
      return true;
    }
    return false;
  }

  public Path copyUntil(Node node) {
    Path path = new Path(graph);

    Node prevNode = null;
    for (Node n: nodes) {
      if (prevNode != null) {
        for (Link l: links) {
          if (l.from().equals(prevNode) && l.to().equals(n)) {
            path.addEdge(l);
          }
        }
      }
      path.addNode(n);
      prevNode = n;
      if (n.equals(node)) break;
    }

    return path;
  }

  public Link getNextEdgeChange() {
    if (nextEdgeChange != null) return nextEdgeChange;
    Link nextUnusedEdge = null;

    for (Link e: links) {
      for (Link ge: graph.links()) {
        if (!ge.equals(e)) {
          if (nodes.contains(ge.from())) {
            nextUnusedEdge = ge;
          }
        }
      }
    }

    return nextUnusedEdge;
  }
  public boolean hasNextEdgeChange() {
    return nextEdgeChange != null;
  }

  public void setNextEdgeChange(Link nextEdgeChange) {
    this.nextEdgeChange = nextEdgeChange;
  }

  @Override
  public String toString() {
    String str = "";
    for (Node n: nodes) {
      str += n.toString() + ',';
    }
    str += "\n";
    return str;
  }

  public Integer size() {
    return graphItems.size();
  }

  public void optedLink(Link link) {
    optedLinks.add(link);
  }
}
