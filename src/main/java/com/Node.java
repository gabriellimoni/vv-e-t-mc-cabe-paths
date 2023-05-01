package com;

import java.util.List;

import guru.nidi.graphviz.attribute.Color;

public class Node {
  private String id;
  private String label;
  private List<Edge> links;
  private Color color;

  public Node(String id, String label) {
    this.id = id;
    this.label = label;
  }

  public Node withLinks(List<Edge> links) {
    setLinks(links);
    return this;
  }

  // GETTERS AND SETTERS
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

  public List<Edge> getLinks() {
    return this.links;
  }

  public void setLinks(List<Edge> links) {
    this.links = links;
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
}
