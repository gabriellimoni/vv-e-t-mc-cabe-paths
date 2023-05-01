package com.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Edge;
import com.Graph;
import com.Node;

// TODO: test
public class CustomDotParser {
  public static Graph parse(String path) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line = reader.readLine();

    Graph graph = new Graph();
    List<Node> nodes = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();

    while (line != null) {
      if (line.contains("digraph")) {
        graph.setName(line.replace("digraph ", ""));
      } else if (line.contains("->")) {
        String[] split = line.split("->");
        String nodeSourceId = split[0].trim();
        System.out.println("Source node ID " + nodeSourceId);
        Node sourceNode = new Node(nodeSourceId, nodeSourceId);
        if (!nodes.contains(sourceNode)) {
          nodes.add(sourceNode);
        }

        String[] targetNodeIds = split[1].trim().split(";");
        List<Node> targetNodes = new ArrayList<>();
        for (String targetNodeId : targetNodeIds) {
          targetNodeId = targetNodeId.replace("{", "").trim();
          targetNodeId = targetNodeId.replace("}", "").trim();
          targetNodeId = targetNodeId.replaceAll("[^0-9]", "").trim();
          if (targetNodeId.isBlank()) continue;
          System.out.println("Target node ID " + targetNodeId);

          Node targetNode = new Node(targetNodeId, targetNodeId);
          targetNodes.add(targetNode);
          
          Edge edge = new Edge(nodeSourceId + "->" + targetNodeId)
            .from(sourceNode)
            .to(targetNode);
          edges.add(edge);
        }
        for (Node targetNode: targetNodes) {
          if (!nodes.contains(targetNode)) {
            nodes.add(targetNode);
          }
        }
      }
      line = reader.readLine();
    }
    reader.close();

    graph.setNodes(nodes);
    graph.setEdges(edges);
    return graph;
  }
}
