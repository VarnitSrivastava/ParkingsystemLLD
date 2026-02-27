
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {
    private final List<ParkingFloor> floors;
    private final Map<String, ParkingTicket> activeTickets;
    private final Map<SpotType, AtomicInteger> availability;

    public ParkingLot(List<ParkingFloor> floors) {
        this.floors = floors;
        this.activeTickets = new ConcurrentHashMap<>();
        this.availability = new ConcurrentHashMap<>();

        for (SpotType type : SpotType.values()) {
            availability.put(type, new AtomicInteger(0));
        }

        initializeAvailability();
    }

    private void initializeAvailability() {
        for (ParkingFloor floor : floors) {
            for (SpotType type : SpotType.values()) {
                availability.get(type).addAndGet(10); // assume 10 per type for demo
            }
        }
    }

    public synchronized ParkingTicket parkVehicle(Vehicle vehicle) {

        if (activeTickets.containsKey(vehicle.getLicensePlate())) {
            throw new RuntimeException("Vehicle already parked.");
        }

        SpotType requiredType = SpotType.valueOf(vehicle.getType().name());

        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.getAvailableSpot(requiredType);

            if (spot != null && spot.assign()) {
                ParkingTicket ticket = new ParkingTicket(vehicle, spot);
                activeTickets.put(vehicle.getLicensePlate(), ticket);
                availability.get(requiredType).decrementAndGet();
                return ticket;
            }
        }

        throw new RuntimeException("No spot available.");
    }

    public synchronized double unparkVehicle(String licensePlate) {

        ParkingTicket ticket = activeTickets.get(licensePlate);
        if (ticket == null) {
            throw new RuntimeException("Vehicle not found.");
        }

        ticket.closeTicket();
        ticket.getSpot().release();

        SpotType type = ticket.getSpot().getType();
        availability.get(type).incrementAndGet();

        activeTickets.remove(licensePlate);

        return ticket.getAmount();
    }

    public int getAvailableSpots(SpotType type) {
        return availability.get(type).get();
    }

}
