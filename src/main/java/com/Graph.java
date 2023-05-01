package com;

import java.util.List;

public class Graph {
  private String name;
  private List<Node> nodes;
  private List<Edge> edges;

  public Graph copy() {
    return new Graph().withEdges(edges).withNodes(nodes);
  }

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

  public List<Edge> edges() {
    return this.edges;
  }

  public void setEdges(List<Edge> edges) {
    this.edges = edges;
  }

  public Edge findEdgeByNodes(Node sourceNode, Node targetNode) {
    for (Edge edge: edges) {
      if (
        sourceNode.equals(edge.from()) &&
        targetNode.equals(edge.to())
      ) return edge;
    }
    return null;
  }

  public Edge findEdgeByNode(Node sourceNode) {
    for (Edge edge: edges) {
      if (
        sourceNode.equals(edge.from())
      ) return edge;
    }
    return null;
  }

  @Override
  public String toString() {
    return getName() + "\n" + "Nodes size: " + nodes.size() + "\n" + "Edges size: " + edges.size();
  }
}
