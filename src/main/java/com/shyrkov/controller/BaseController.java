package com.shyrkov.controller;

import au.com.bytecode.opencsv.CSVReader;
import com.shyrkov.CsvToPipelineConverter;
import com.shyrkov.DbController;
import com.shyrkov.Main;
import com.shyrkov.RouteAnalyzer;
import com.shyrkov.model.Pipeline;
import com.shyrkov.model.PointSet;
import com.shyrkov.model.ResultModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BaseController {

    @FXML
    public TableView<Pipeline> systemTable;
    @FXML
    public TableColumn<Pipeline, Integer> xId;
    @FXML
    public TableColumn<Pipeline, Integer> yId;
    @FXML
    public TableColumn<Pipeline, Integer> pipeLength;
    @FXML
    public TableView<PointSet> pointsTable;
    @FXML
    public TableColumn<PointSet, Integer> pointA;
    @FXML
    public TableColumn<PointSet, Integer> pointB;
    @FXML
    public TableView<ResultModel> resultTable;
    @FXML
    public TableColumn<ResultModel, Boolean> hasRoute;
    @FXML
    public TableColumn<ResultModel, String> minLength;
    @FXML
    public Button chooseSystemBtn;
    @FXML
    public Button choosePointsBtn;
    @FXML
    public Button calculateBtn;

    private Main main;
    private final FileChooser fileChooser = new FileChooser();
    private final RouteAnalyzer analyzer = new RouteAnalyzer();
    private ObservableList<Pipeline> pipelines = FXCollections.observableArrayList();
    private ObservableList<PointSet> points = FXCollections.observableArrayList();
    private ObservableList<ResultModel> results = FXCollections.observableArrayList();
    private final String resPath = "src/main/resources/results.csv";
    private final DbController dbController = new DbController();

    @FXML
    private void initialize() {
        this.chooseSystemBtn.setOnAction(actionEvent -> {
            try {
                Stage stage = (Stage) chooseSystemBtn.getScene().getWindow();
                File file = fileChooser.showOpenDialog(stage);
                CsvToPipelineConverter converter = new CsvToPipelineConverter(file.getAbsolutePath());
                pipelines.addAll(converter.convert());
                xId.setCellValueFactory(new PropertyValueFactory<Pipeline, Integer>("startPointId"));
                yId.setCellValueFactory(new PropertyValueFactory<Pipeline, Integer>("endPointId"));
                pipeLength.setCellValueFactory(new PropertyValueFactory<Pipeline, Integer>("length"));
                systemTable.setItems(pipelines);
                dbController.loadPipelineSystem(file.getAbsolutePath(), pipelines);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.choosePointsBtn.setOnAction(actionEvent -> {
            try {
                Stage stage = (Stage) choosePointsBtn.getScene().getWindow();
                File file = fileChooser.showOpenDialog(stage);
                CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()), ';');
                String[] strings;
                while ((strings = reader.readNext()) != null) {
                    points.add(new PointSet(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])));
                }

                pointA.setCellValueFactory(new PropertyValueFactory<PointSet, Integer>("pointA"));
                pointB.setCellValueFactory(new PropertyValueFactory<PointSet, Integer>("pointB"));

                pointsTable.setItems(points);
                dbController.loadPointSet(file.getAbsolutePath(), points);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        this.calculateBtn.setOnAction(actionEvent -> {
            try {
                new FileWriter(resPath, false);
                resultTable.getItems().clear();
                for (PointSet point : points) {
                    results.add(analyzer.findRoute(point.getPointA(), point.getPointB(), pipelines, resPath));
                }
                hasRoute.setCellValueFactory(new PropertyValueFactory<ResultModel, Boolean>("hasRoute"));
                minLength.setCellValueFactory(new PropertyValueFactory<ResultModel, String>("length"));
                resultTable.setItems(results);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setAppFX(Main main) {
        this.main = main;
    }
}
