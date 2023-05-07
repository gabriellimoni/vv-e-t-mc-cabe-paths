package com;

import guru.nidi.graphviz.attribute.Color;

public class Link implements GraphItem {
  private String id;
  private Node from;
  private Node to;
  private Color color;

  public Link(String id) {
    this.id = id;
  }

  public Node from() {
    return this.from;
  }
  public Link from(Node from) {
    this.from = from;
    from.addLink(this);
    return this;
  }
  public Node to() {
    return this.to;
  }
  public Link to(Node to) {
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

  @Override
  public boolean equals(Object obj) {
    String givenId = ((Link)obj).getId();
    return id.equals(givenId);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return from().toString() + "->" + to().toString();
  }
}
