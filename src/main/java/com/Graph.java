package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {

  private String name;
  private List<Node> nodes;
  private List<Link> edges;

  public Graph copy() {
    return new Graph().withEdges(edges).withNodes(nodes);
  }

  public Graph() {}

  public Graph withNodes(List<Node> nodes) {
    setNodes(nodes);
    return this;
  }

  public Graph withEdges(List<Link> edges) {
    setLinks(edges);
    return this;
  }

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

  public Node getFirstNode() throws IOException {
    List<Node> nodesWithoutLinkTo = new ArrayList<>(nodes());
    for (Link l : links()) {
      nodesWithoutLinkTo.remove(l.to());
    }
    if (nodesWithoutLinkTo.size() > 1) {
      throw new IOException("Graph has more than one starting nodes");
    }
    return nodesWithoutLinkTo.get(0);
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
    return (
      getName() +
      "\n" +
      "Nodes size: " +
      nodes.size() +
      "\n" +
      "Edges size: " +
      edges.size()
    );
  }

  public Integer complexity() {
    Integer endingNodes = endingNodes();
    return links().size() - nodes().size() + endingNodes + 1;
  }

  public Integer endingNodes() {
    List<Node> endingNodes = new ArrayList<>();
    for (Node n : nodes()) {
      if (n.links().size() == 0) endingNodes.add(n);
    }
    return endingNodes.size();
  }
}
