
import java.time.Duration;
import java.time.LocalDateTime;


public class ParkingTicket {
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private TicketStatus status;

    public ParkingTicket(Vehicle vehicle, ParkingSpot spot) {
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    public void closeTicket() {
        this.exitTime = LocalDateTime.now();
        this.amount = FeeCalculator.calculate(vehicle, entryTime, exitTime);
        this.status = TicketStatus.CLOSED;
    }

    public double getAmount() {
        return amount;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public TicketStatus getStatus() {
        return status;
    }

}
