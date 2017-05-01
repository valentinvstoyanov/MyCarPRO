package elsys.mycar.mycarpro.data;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import elsys.mycar.mycarpro.model.Insurance;
import elsys.mycar.mycarpro.model.Refueling;
import elsys.mycar.mycarpro.model.Service;
import elsys.mycar.mycarpro.model.Vehicle;
import elsys.mycar.mycarpro.util.DateUtils;
import elsys.mycar.mycarpro.util.StringUtils;

public class VehicleRepositoryImpl implements VehicleRepository {

    private List<Vehicle> mVehicles;
    private static final VehicleRepositoryImpl INSTANCE = new VehicleRepositoryImpl();

    private VehicleRepositoryImpl() {
        this.mVehicles = new ArrayList<>();
        String date = DateUtils.textDateFromInts(2015, 3, 2);
        for (int i = 0; i < 5 ; i++) {
            Vehicle v = new Vehicle(UUID.randomUUID().toString(), "Some name" + i, "saasd", "asd", date, 12, 12, "KURVIII");
            v.setInsurances(new ArrayList<Insurance>());
            List<Service> s = new ArrayList<>();
            s.add(new Service("12edsxa", "IDK", "2 May 2020", 2020, 200, "note"));
            s.add(new Service("12edsxaasd", "Sad story", "2 May 2012", 2020, 200, "note"));
            s.add(new Service("12edsxah", "IDK - asd", "20 Apr 2020", 2020, 200, "note"));
            s.add(new Service("12edsxawer3", "adal;d.asd;", "01 Jan 2012", 2020, 200, "note"));
            v.setServices(s);
            v.setRefuelings(new ArrayList<Refueling>());
            mVehicles.add(v);
        }
    }

    public static VehicleRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Vehicle vehicle) {
        if (!StringUtils.checkNotNullOrEmpty(vehicle.getId())) {
            vehicle.setId(UUID.randomUUID().toString());
        }
        vehicle.setInsurances(new ArrayList<Insurance>());
        vehicle.setServices(new ArrayList<Service>());
        vehicle.setRefuelings(new ArrayList<Refueling>());
        mVehicles.add(vehicle);
    }

    @Override
    public Vehicle getById(String id) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        for (Iterator<Vehicle> iterator = mVehicles.iterator(); iterator.hasNext();) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Vehicle> getAll() {
        return mVehicles;
    }

    @Override
    public HashMap<String, String> getVehicleIdToNameHash() {
        HashMap<String, String> hash = new HashMap<>(mVehicles.size());

        for (Vehicle vehicle : mVehicles) {
            hash.put(vehicle.getId(), vehicle.getName());
        }

        return hash;
    }

/*    @Override
    public List<String> getAllVehicleNames() {
        List<String> names = new ArrayList<>();

        for (Vehicle vehicle : mVehicles) {
            names.add(vehicle.getName());
        }

        Collections.sort(names);
        return names;
    }*/

    @Override
    public String getVehicleIdByName(String name) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getName().equals(name)) {
                Log.d("getVehicleIdByName: ", "true");
                Log.d("id", "id = " + vehicle.getId());
                return vehicle.getId();
                //Log.d("getVehicleIdByName: ", "true after return");
            }else {
                Log.d("getVehicleIdByName: ", "false");
            }
        }

        Log.d("getVehicleIdByName: ", "before null");
        return null;
    }

    @Override
    public String getVehicleNamesById(String id) {
        Vehicle vehicle = getById(id);
        if (vehicle != null) {
            return vehicle.getId();
        }
        return null;
    }

    @Override
    public void addInsurance(String vehicleId, Insurance insurance) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                vehicle.addInsurance(insurance);
            }
        }
    }

    @Override
    public void addService(String vehicleId, Service service) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                vehicle.addService(service);
            }
        }
    }

    @Override
    public void addRefueling(String vehicleId, Refueling refueling) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                vehicle.addRefueling(refueling);
            }
        }
    }

    @Override
    public List<Service> getServicesByVehicleId(String vehicleId) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                return vehicle.getServices();
            }
        }
        return null;
    }

    @Override
    public List<Insurance> getInsurancesByVehicleId(String vehicleId) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                return vehicle.getInsurances();
            }
        }
        return null;
    }

    @Override
    public List<Refueling> getRefuelingsByVehicleId(String vehicleId) {
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                return vehicle.getRefuelings();
            }
        }
        return null;
    }

    @Override
    public List<String> getMakes() {
        return Data.getMakes();
    }

    @Override
    public List<String> getCompanyNames() {
        return Data.getInsuranceCompanies();
    }

    @Override
    public List<String> getServiceTypes() {
        return Data.getServiceTypes();
    }

    @Override
    public List<String> getGasStations() {
        return Data.getGasStationCompanies();
    }
}