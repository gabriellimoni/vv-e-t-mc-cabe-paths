package com.vvet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.LinkSource;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.model.Factory;

public class McCabe {
  static void run(String name, MutableGraph graph) throws IOException {
    System.out.println(name);

    MutableGraph originGraph = graph.copy();
    WriteGraph.write(originGraph, "source-" + name + ".png");

    Collection<MutableNode> rootNodes = originGraph.rootNodes();
    if (rootNodes.size() < 1) return;

    MutableNode firstNode = rootNodes.iterator().next();
    System.out.println(firstNode);

    List<Link> nodeLinks = firstNode.links();
    if (nodeLinks.size() < 1) return;

    Link link = nodeLinks.get(0);
    MutableGraph nextGraph = (MutableGraph)link.to();

    // System.out.println(nextGraph.nodes());
    
    // for (MutableNode n : graph.nodes()) {
    //   List<Link> nodeLinks = n.links();
    //   if (nodeLinks.size() > 0) {
    //     Link firstLink = nodeLinks.get(0);
    //     firstLink.add(Color.RED);
    //     System.out.println("Aqui");
    //   }
    // }
  }
}
