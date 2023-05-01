package com;

import java.util.ArrayList;
import java.util.List;

public class Path {
  private Graph graph;
  private List<Node> nodes = new ArrayList<>();
  private List<Edge> edges = new ArrayList<>();
  private Edge nextEdgeChange = null;


  public Path(Graph graph) {
    this.graph = graph;
  }

  public void addNode(Node node) {
    this.nodes.add(node);
  }
  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  public List<Node> nodes() {
    return nodes;
  }

  public boolean edgeAlreadyUsed(Edge edge) {
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
        for (Edge e: edges) {
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

  public Edge getNextEdgeChange() {
    if (nextEdgeChange != null) return nextEdgeChange;
    Edge nextUnusedEdge = null;

    for (Edge e: edges) {
      for (Edge ge: graph.edges()) {
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

  public void setNextEdgeChange(Edge nextEdgeChange) {
    this.nextEdgeChange = nextEdgeChange;
  }

  @Override
  public String toString() {
    String str = "";
    for (Node n: nodes) {
      str += n.toString() + ',';
    }
    str += '\n';
    for (Edge e: edges) {
      str += e.toString() + ',';
    }
    str += "\n";
    return str;
  }
}
