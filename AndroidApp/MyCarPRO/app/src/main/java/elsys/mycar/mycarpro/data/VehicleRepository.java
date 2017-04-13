package elsys.mycar.mycarpro.data;

import java.util.List;

import elsys.mycar.mycarpro.model.Vehicle;

public interface VehicleRepository {

    void save(Vehicle vehicle);

    void delete(String id);

    List<Vehicle> getAll();
}
