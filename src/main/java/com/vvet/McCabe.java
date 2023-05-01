package com.vvet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.*;

public class McCabe {
  static void run(String name, Graph graph) throws IOException {
    Integer totalPaths = graph.edges().size() - graph.nodes().size() + 2;
    System.out.println(totalPaths);

    List<Path> paths = new ArrayList<>();
    Integer decisionIndex = 0;
    
    List<Node> selectedNodes = new ArrayList<>();
    while (paths.size() < totalPaths) {
      Path path = new Path(graph);
      Node node = graph.getFirstNode();
      path.addNode(node);
      selectedNodes.add(node);
      List<Edge> links = node.links();
      if (paths.size() == 0) {
        boolean decisionMarked = false;
        Integer idx = 0;
        while(links.size() > 0) {
          if (links.size() > 1 && !decisionMarked) {
            decisionMarked = true;
            decisionIndex = idx;
          }
          Iterator<Edge> it = links.iterator();
          Edge link = it.next();
          while(path.edgeAlreadyUsed(link)) {
            link = it.next();
          }
          node = link.to();
          path.addNode(node);
          path.addEdge(link);
          selectedNodes.add(node);
          links = node.links();
          idx++;
        }
      } else {
        boolean decisionMarked = false;
        Integer idx = 0;
        while(links.size() > 0) {
          Iterator<Edge> it = links.iterator();
          Edge link = it.next();
          if (links.size() > 1 && !decisionMarked && idx != decisionIndex) {
            decisionMarked = true;
            decisionIndex = idx;
          }
          if (decisionIndex == idx) {
            link = it.next();
          }
          while(path.edgeAlreadyUsed(link) && links.size() > 1) {
            link = it.next();
          }
          node = link.to();
          path.addNode(node);
          path.addEdge(link);
          selectedNodes.add(node);
          links = node.links();
          idx++;
        }
      }
      paths.add(path);
    }
    for (Path path: paths) {
      System.out.println(path);
    }
  }

  static void run2(String name, Graph graph) throws IOException {
    Integer totalPaths = graph.edges().size() - graph.nodes().size() + 2;
    System.out.println(totalPaths);

    List<Path> paths = new ArrayList<>();
    Path previousPath = null;

    while (paths.size() < totalPaths) {
      Path path = new Path(graph);
      Node node = graph.getFirstNode();
      path.addNode(node);
      List<Edge> links = node.links();

      if (previousPath == null) {
        while(links.size() > 0) {
          Iterator<Edge> it = links.iterator();
          Edge link = it.next();
          while(path.edgeAlreadyUsed(link)) {
            link = it.next();
          }
          if (links.size() > 1 && !path.hasNextEdgeChange()) {
            path.setNextEdgeChange(it.next());
          }
          node = link.to();
          path.addNode(node);
          path.addEdge(link);
          links = node.links();
        }
      } else {
        path = previousPath.copyUntil(previousPath.getNextEdgeChange().from());
        Node firstPathNode = path.nodes().get(path.nodes().size() - 1);
        links = firstPathNode.links();
        while(links.size() > 0) {
          Iterator<Edge> it = links.iterator();
          Edge link = it.next();
          if (links.contains(previousPath.getNextEdgeChange())) {
            while (!link.equals(previousPath.getNextEdgeChange())) {
              link = it.next();
            }
          }
          while(path.edgeAlreadyUsed(link)) {
            link = it.next();
          }
          if (it.hasNext() && !path.hasNextEdgeChange()) {
            path.setNextEdgeChange(it.next());
          }
          node = link.to();
          path.addNode(node);
          path.addEdge(link);
          links = node.links();
        }
      }
      
      System.out.println(path);
      paths.add(path);
      previousPath = path;
    }
  }


}
