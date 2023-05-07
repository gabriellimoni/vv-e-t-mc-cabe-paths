/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vvet.view.controller;

import static guru.nidi.graphviz.model.Factory.*;

import com.Graph;
import com.Path;
import com.parser.CustomDotParser;
import com.vvet.mccabe.McCabeRunner;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController {

  @FXML
  private MenuBar menuBar;

  @FXML
  private ImageView originalGraph;

  @FXML
  protected void openFileChooser(ActionEvent event) throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open File");

    fileChooser.setTitle("Open Resource File");
    Stage stage = (Stage) menuBar.getScene().getWindow();
    File selectedFile = fileChooser.showOpenDialog(stage);
    MutableGraph g = new Parser().read(selectedFile).setDirected(true);
    Graphviz
      .fromGraph(g)
      .render(Format.PNG)
      .toFile(new File(selectedFile.getAbsolutePath().replace(".dot", ".png")));

    Image outImage = new Image(
      "file://" + selectedFile.getAbsolutePath().replace(".dot", ".png")
    );
    originalGraph.setImage(outImage);

    stage.setWidth(outImage.getWidth() + 30);
    stage.setHeight(outImage.getHeight() + 100);

    try {
      Graph mccabe = CustomDotParser.parse(selectedFile.getAbsolutePath());
      List<Path> paths = new McCabeRunner(mccabe, g.name().toString()).run();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @FXML
  protected void processExit(ActionEvent event) {
    System.exit(0);
  }
}
