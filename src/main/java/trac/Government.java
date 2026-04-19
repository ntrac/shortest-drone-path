/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trac;

/**
 *
 * @author nguyentrac
 */
public class Government implements Comparable<Government> {
    protected String govName;

    public Government(String name) {
        govName = name;
    }

    public String getName() {
        return govName;
    }

    @Override
    public int compareTo(Government other) {
        return this.govName.compareTo(other.govName); // Compare by name
    }

    @Override
    public String toString() {
        return govName;
    }
}
