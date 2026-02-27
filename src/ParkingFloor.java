
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ParkingFloor {
    private final int floorNumber;
    private final Map<SpotType, Queue<ParkingSpot>> availableSpots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.availableSpots = new ConcurrentHashMap<>();

        for (SpotType type : SpotType.values()) {
            availableSpots.put(type, new ConcurrentLinkedQueue<>());
        }
    }

    public void addSpot(ParkingSpot spot) {
        availableSpots.get(spot.getType()).offer(spot);
    }

    public ParkingSpot getAvailableSpot(SpotType type) {
        return availableSpots.get(type).poll();
    }

    public void releaseSpot(ParkingSpot spot) {
        availableSpots.get(spot.getType()).offer(spot);
    }

}
