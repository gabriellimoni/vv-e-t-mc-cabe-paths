package com;

import guru.nidi.graphviz.attribute.Color;

public class Edge {
  private String id;
  private Node from;
  private Node to;
  private Color color;

  public Edge(String id) {
    this.id = id;
  }

  public Node from() {
    return this.from;
  }
  public Edge from(Node from) {
    this.from = from;
    return this;
  }
  public Node to() {
    return this.to;
  }
  public Edge to(Node to) {
    this.to = to;
    return this;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
