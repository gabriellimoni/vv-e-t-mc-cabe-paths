package com;

import java.util.List;

public class Graph {
  private String name;
  private List<Node> nodes;
  private List<Edge> edges;
  private Node firstNode;

  public Graph() {
  }
  public Graph withNodes(List<Node> nodes) {
    setNodes(nodes);
    return this;
  }
  public Graph withEdges(List<Edge> edges) {
    setEdges(edges);
    return this;
  }
  public Graph withFirstNode(Node node) {
    setFirstNode(node);
    return this;
  }

  // GETTERS AND SETTERS
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Node> getNodes() {
    return this.nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

  public Node getFirstNode() {
    return this.firstNode;
  }

  public void setFirstNode(Node firstNode) {
    this.firstNode = firstNode;
  }

  public List<Edge> getEdges() {
    return this.edges;
  }

  public void setEdges(List<Edge> edges) {
    this.edges = edges;
  }

  @Override
  public String toString() {
    return getName() + "\n" + "Nodes size: " + nodes.size() + "\n" + "Edges size: " + edges.size();
  }
}
