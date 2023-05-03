package com;

import java.util.ArrayList;
import java.util.List;

public class Path {
  private Graph graph;
  private List<Node> nodes = new ArrayList<>();
  private List<Link> edges = new ArrayList<>();
  private Link nextEdgeChange = null;

  public Path(Graph graph) {
    this.graph = graph;
  }

  public void addNode(Node node) {
    this.nodes.add(node);
  }
  public void addEdge(Link edge) {
    this.edges.add(edge);
  }

  public List<Node> nodes() {
    return nodes;
  }

  public boolean edgeAlreadyUsed(Link edge) {
    if (edges.contains(edge)) {
      return true;
    }
    return false;
  }

  public Path copyUntil(Node node) {
    Path path = new Path(graph);

    Node prevNode = null;
    for (Node n: nodes) {
      if (prevNode != null) {
        for (Link e: edges) {
          if (e.from().equals(prevNode) && e.to().equals(n)) {
            path.addEdge(e);
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

    for (Link e: edges) {
      for (Link ge: graph.edges()) {
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
    str += '\n';
    for (Link e: edges) {
      str += e.toString() + ',';
    }
    str += "\n";
    return str;
  }
}
