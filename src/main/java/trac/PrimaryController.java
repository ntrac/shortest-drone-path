package trac;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class PrimaryController implements Initializable {

    @FXML
    private ComboBox<City> startCityComboBox;

    @FXML
    private ComboBox<City> destinationCityComboBox;

    @FXML
    private TextField fileName;

    @FXML
    private Button readButton;

    @FXML
    private TextField maxRangeField;

    @FXML
    private Label statusLabel;

    @FXML
    private Label directDistance;

    @FXML
    private Label totalPathDistance;

    @FXML
    private ScrollPane resultPane;

    @FXML
    private VBox pathVBox;

    @FXML
    private Label totalCitiesRead;

    private Graph graph; // Graph to represent the cities and distances
    private List<City> cities = new ArrayList<>(); // Static list of cities

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graph = new Graph(); // Initialize the graph
        initializeComboBoxListeners(); // Attach listeners to ComboBoxes
    }

    private void initializeComboBoxListeners() {
        // Add listeners to ComboBoxes to detect changes in selection
        ChangeListener<City> selectionListener = (ObservableValue<? extends City> observable, City oldValue, City newValue) -> {
            if (startCityComboBox.getValue() != null && destinationCityComboBox.getValue() != null) {
                calculateAndDisplayPath();
            }
        };

        startCityComboBox.valueProperty().addListener(selectionListener);
        destinationCityComboBox.valueProperty().addListener(selectionListener);
    }
    
    @FXML
    private void handleReadButton() {
        String filePath = fileName.getText();

        if (filePath.isEmpty()) {
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            statusLabel.setText("Error: File name cannot be blank.");
            return;
        }

        try {
            loadCityData(filePath);
            populateComboBoxes();
            statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            statusLabel.setText("The cities have been read!");
            if (totalCitiesRead != null) {
                totalCitiesRead.setTextFill(javafx.scene.paint.Color.GREEN);
                totalCitiesRead.setText(cities.size() + " Cities Read");
            }
        } catch (Exception e) {
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            statusLabel.setText("Error reading file: " + e.getMessage());
        }
    }


    private void loadCityData(String filePath) throws Exception {
        cities.clear();
        graph.clear();

        Scanner inputFile = new Scanner(new File(filePath));
        if (inputFile.hasNextLine()) {
            inputFile.nextLine(); // Skip header line
        }

        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] data = line.split(",");
            if (data.length < 7) continue;

            int zipCode = Integer.parseInt(data[0].trim());
            String cityName = data[1].trim();
            String stateName = data[2].trim();
            double latitude = Double.parseDouble(data[3].trim());
            double longitude = Double.parseDouble(data[4].trim());
            int timezone = Integer.parseInt(data[5].trim());
            boolean daylight = data[6].trim().equals("1");

            City newCity = new City(zipCode, cityName, stateName, latitude, longitude, timezone, daylight);
            cities.add(newCity);
            graph.addCity(newCity);
        }

        inputFile.close();
    }

     private void populateComboBoxes() {
        cities.sort(Comparator.comparing(City::getCityName).thenComparing(City::stateName));
        ObservableList<City> sortedCities = FXCollections.observableArrayList(cities);
        startCityComboBox.setItems(sortedCities);
        destinationCityComboBox.setItems(sortedCities);
    }
    
    @FXML
private void handleStartCitySelection(ActionEvent event) {
    City selectedCity = startCityComboBox.getValue();
    System.out.println("Start city selected: " + selectedCity);
}

@FXML
private void handleDestinationCitySelection(ActionEvent event) {
    City selectedCity = destinationCityComboBox.getValue();
    System.out.println("Destination city selected: " + selectedCity);
}

     private void calculateAndDisplayPath() {
        City startCity = startCityComboBox.getValue();
        City destinationCity = destinationCityComboBox.getValue();

        if (startCity == null || destinationCity == null) return;

        // Re-read max range from the TextField each time (Requirement 4)
        double maxRange = 235.0;
        try {
            maxRange = Double.parseDouble(maxRangeField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            statusLabel.setText("Invalid Max Range value.");
            return;
        }

        updateGraphEdges(maxRange);

        List<City> path = graph.shortestPath(startCity, destinationCity);
        if (path == null || path.isEmpty()) {
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            statusLabel.setText("No path exists");
            directDistance.setText("");
            totalPathDistance.setText("");
            displayPath(Collections.emptyList());
            return;
        }

        double directDist = calculateDistance(startCity, destinationCity);
        double totalDist = calculatePathDistance(path);
        directDistance.setTextFill(javafx.scene.paint.Color.BLUE);
        directDistance.setText(String.format("Distance: %.1f miles", directDist));
        totalPathDistance.setTextFill(javafx.scene.paint.Color.BLUE);
        totalPathDistance.setText(String.format("Path: %.1f", totalDist));
        displayPath(path);
        statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        statusLabel.setText("Path found successfully.");
    }

    private void updateGraphEdges(double maxRange) {
    graph.clearEdges();

    for (int i = 0; i < cities.size(); i++) {
        for (int j = i + 1; j < cities.size(); j++) {
            City city1 = cities.get(i);
            City city2 = cities.get(j);
            double distance = calculateDistance(city1, city2);
            if (distance <= maxRange) {
                graph.addEdge(city1, city2, distance);
            }
        }
    }
}

    private void displayPath(List<City> path) {
        pathVBox.getChildren().clear();
        if (path.isEmpty()) {
            pathVBox.getChildren().add(new Label("No path exists"));
            return;
        }

        pathVBox.getChildren().add(new Label("PATH:"));
        for (City city : path) {
            pathVBox.getChildren().add(new Label("  " + city.getCityName() + ",  " + city.stateName()));
        }
    }

    private double calculatePathDistance(List<City> path) {
        double totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += calculateDistance(path.get(i), path.get(i + 1));
        }
        return totalDistance;
    }

    private double calculateDistance(City city1, City city2) {
        double lat1 = city1.getLatitude();
        double lon1 = city1.getLongitude();
        double lat2 = city2.getLatitude();
        double lon2 = city2.getLongitude();

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}