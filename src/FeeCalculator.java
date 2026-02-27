
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;


public class FeeCalculator {
    private static final Map<VehicleType, Double> RATES = Map.of(
            VehicleType.MOTORCYCLE, 10.0,
            VehicleType.CAR, 20.0,
            VehicleType.BUS, 50.0
    );

    public static double calculate(
            Vehicle vehicle,
            LocalDateTime entry,
            LocalDateTime exit
    ) {
        long hours = Duration.between(entry, exit).toHours();
        if (hours == 0) hours = 1;

        return hours * RATES.get(vehicle.getType());
    }

}
