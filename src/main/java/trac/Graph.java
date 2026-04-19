/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trac;

/**
 *
 * @author nguyentrac
 */
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;


public class Graph {
    private Map<City, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addCity(City city) {
        adjacencyList.putIfAbsent(city, new ArrayList<>());
    }

    public void addEdge(City fromCity, City toCity, double distance) {
        adjacencyList.get(fromCity).add(new Edge(toCity, distance));
        adjacencyList.get(toCity).add(new Edge(fromCity, distance)); // Since it's undirected
    }
    
    // Clear all edges in the graph
    public void clearEdges() {
        for (List<Edge> edges : adjacencyList.values()) {
            edges.clear();
        }
    }
    
     public void clear() {
        adjacencyList.clear(); // Clear all cities and edges
    }
    
    public List<City> shortestPath(City start, City destination) {
    System.out.println("Finding path from " + start.getCityName() + " to " + destination.getCityName());

    MinHeapPriorityQueue<CityDistance> minHeap = new MinHeapPriorityQueue<>();
    Map<City, Double> distances = new HashMap<>();
    Map<City, City> previous = new HashMap<>();

    for (City city : adjacencyList.keySet()) {
        distances.put(city, Double.MAX_VALUE);
        previous.put(city, null);
    }

    distances.put(start, 0.0);
    minHeap.insert(new CityDistance(start, 0.0));

    while (!minHeap.isEmpty()) {
        CityDistance current = minHeap.extractMin();
        City currentCity = current.city;

        System.out.println("Visiting city: " + currentCity.getCityName());

        if (currentCity.equals(destination)) {
            return reconstructPath(previous, destination);
        }

        for (Edge edge : adjacencyList.get(currentCity)) {
            City neighbor = edge.toCity;
            double newDist = distances.get(currentCity) + edge.distance;

            if (newDist < distances.get(neighbor)) {
                distances.put(neighbor, newDist);
                previous.put(neighbor, currentCity);
                minHeap.insertOrUpdate(new CityDistance(neighbor, newDist));
            }
        }
    }

    return new ArrayList<>(); // No path found
}
    
    

    public List<City> getCities() {
        return new ArrayList<>(adjacencyList.keySet());
    }
    
    private List<City> reconstructPath(Map<City, City> previous, City destination) {
        List<City> path = new ArrayList<>();
        for (City at = destination; at != null; at = previous.get(at)) {
            path.add(0, at);
        }
        return path;
    }

    private class Edge {
        City toCity;
        double distance;

        Edge(City toCity, double distance) {
            this.toCity = toCity;
            this.distance = distance;
        }
    }

    private class CityDistance implements Comparable<CityDistance> {
        City city;
        double distance;

        CityDistance(City city, double distance) {
            this.city = city;
            this.distance = distance;
        }

        @Override
        public int compareTo(CityDistance other) {
            return Double.compare(this.distance, other.distance);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            CityDistance that = (CityDistance) obj;
            return city.equals(that.city);
        }

        @Override
        public int hashCode() {
            return city.hashCode();
        }
    }
}
