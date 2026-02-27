
import java.util.concurrent.locks.ReentrantLock;


public class ParkingSpot {
    private final String id;
    private final SpotType type;
    private boolean available;
    private final ReentrantLock lock = new ReentrantLock();

    private ParkingSpot(String id, SpotType type) {
        this.id = id;
        this.type = type;
        this.available = true;
    }

    public static ParkingSpot createParkingSpot(String id, SpotType type) {
        return new ParkingSpot(id, type);
    }

    public String getId() {
        return id;
    }

    public SpotType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean assign() {
        lock.lock();
        try {
            if (!available) return false;
            available = false;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            available = true;
        } finally {
            lock.unlock();
        }
    }
}
