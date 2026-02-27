import java.util.*;


    public static void main(String[] args) throws InterruptedException {

        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSpot(ParkingSpot.createParkingSpot("1A", SpotType.CAR));
        floor1.addSpot(ParkingSpot.createParkingSpot("1B", SpotType.MOTORCYCLE));
        floor1.addSpot(ParkingSpot.createParkingSpot("1C", SpotType.BUS));

        List<ParkingFloor> floors = List.of(floor1);
        ParkingLot parkingLot = new ParkingLot(floors);

        Vehicle car = new Vehicle("KA01AB1234", VehicleType.CAR);

        // Park vehicle
        ParkingTicket ticket = parkingLot.parkVehicle(car);
        System.out.println("Vehicle parked.");

        Thread.sleep(3000); // simulate time

        // Unpark vehicle
        double amount = parkingLot.unparkVehicle(car.getLicensePlate());
        System.out.println("Vehicle unparked. Fee: " + amount);
    }