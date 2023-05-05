package com;

import java.util.List;

public class Graph {
  private String name;
  private List<Node> nodes;
  private List<Link> edges;

  public Graph copy() {
    return new Graph().withEdges(edges).withNodes(nodes);
  }

  public Graph() {
  }
  public Graph withNodes(List<Node> nodes) {
    setNodes(nodes);
    return this;
  }
  public Graph withEdges(List<Link> edges) {
    setLinks(edges);
    return this;
  }

  // GETTERS AND SETTERS
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Node> nodes() {
    return this.nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

  // TODO: include logic to get node that has no edge TO it
  public Node getFirstNode() {
    return nodes.get(0);
  }

  @Deprecated
  public List<Link> edges() {
    return this.edges;
  }
  public List<Link> links() {
    return this.edges;
  }

  public void setLinks(List<Link> edges) {
    this.edges = edges;
  }

  @Override
  public String toString() {
    return getName() + "\n" + "Nodes size: " + nodes.size() + "\n" + "Edges size: " + edges.size();
  }
}
