package com;

import java.util.ArrayList;
import java.util.List;

import guru.nidi.graphviz.attribute.Color;

public class Node implements GraphItem {
  private String id;
  private String label;
  private List<Link> links;
  private Color color;

  public Node(String id, String label) {
    this.id = id;
    this.label = label;
    this.links = new ArrayList<>();
  }

  public Node withLinks(List<Link> links) {
    setLinks(links);
    return this;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<Link> links() {
    return this.links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public void addLink(Link link) {
    this.links.add(link);
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public boolean equals(Object obj) {
    String givenId = ((Node)obj).getId();
    return id.equals(givenId);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return getLabel();
  }
}
