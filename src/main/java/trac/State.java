///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package trac;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author nguyentrac
// */
//public class State extends Government{
//    private OrderedAddOnce<City> cityList;
//
//    // Constructor that initializes the state with a name and an empty city list
//    public State(String name) {
//        super(name);
//        this.cityList = new OrderedAddOnce<>();
//    }
//
//    // Getter for cityList, returns the ordered list of cities
//    public OrderedAddOnce<City> getCityList() {
//        return cityList;
//    }
//
//    // Method to add a city to the cityList if not already present
//    public void addCity(City city) {
//        cityList.addOnce(city);
//    }
//    
//    
//    // Method to retrieve the top 10 cities by zip code count
//    public List<City> getTop10Cities() {
//        List<City> sortedCities = new ArrayList<>();
//        for (City city : cityList) {
//            sortedCities.add(city);
//        }
//        // Sort cities by zip code count in descending order
//        sortedCities.sort((c1, c2) -> Integer.compare(c2.getZipCodeCount(), c1.getZipCodeCount()));
//        return sortedCities.subList(0, Math.min(10, sortedCities.size()));
//    }
//
//}