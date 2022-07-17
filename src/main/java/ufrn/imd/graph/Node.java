package ufrn.imd.graph;

import lombok.Data;

@Data
public class Node {
    private final int id;
    private String name;
    private Coordinates coordinates;


    public Node(int id, Coordinates coordinates) {
        this.id = id;
        this.name = "Client_" + id;
        this.coordinates = coordinates;
    }

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Node(int id, String name, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }

    public double calculeDistance(Node node) {
        // This code is based on geeksforgeeks.com/program-distance-two-points-earth/
        double lat1 = this.coordinates.getLatitude();
        double lat2 = node.getCoordinates().getLatitude();
        double lon1 = this.coordinates.getLongitude();
        double lon2 = node.getCoordinates().getLongitude();

        double difLon = lon2 - lon1;
        double difLat = lat2 - lat1;
        double a = Math.pow(Math.sin(difLat / 2), 2)
                   + Math.cos(lat1) * Math.cos(lat2)
                   * Math.pow(Math.sin(difLon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double radius = 6371;

        return c * radius;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }
}
