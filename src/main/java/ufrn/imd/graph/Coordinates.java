package ufrn.imd.graph;

import lombok.Data;

@Data
public class Coordinates {
    public final double latitude;
    public final double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
