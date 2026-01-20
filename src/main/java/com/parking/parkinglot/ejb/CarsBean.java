package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.entities.Car;
import org.example.parkinglot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> copyCarsToDto(List<Car> cars){
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars){

            Long carId = car.getId();
            String carLicensePlate=car.getLicensePlate();
            String carParkingSpot=car.getParkingSpot();
            String carOwnerName=car.getOwner().getUsername();

            CarDto carDto=new CarDto(carId,carLicensePlate,carParkingSpot,carOwnerName);
            carDtos.add(carDto);
        }
        return carDtos;
    }

    public List<CarDto> findAllCars() {
        LOG.info("FindAllCars");
        try{
            TypedQuery<Car>typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        }catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public void createCar(String licensePlate, String parkingSpot, Long userid) {
        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userid);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }
    public CarDto findAllCars(Long id) {
        LOG.info("findAllCars");
        CarDto carDto = entityManager.find(CarDto.class, id);
        return carDto;
    }
    public CarDto findByIdCar(Long id) {
        LOG.info("findByIdCar");
        CarDto carDto = entityManager.find(CarDto.class, id);
        return carDto;
    }
    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userid) {
        LOG.info("updateCar");

        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User oldUser =car.getOwner();
        oldUser.getCars().remove(car);

        User user = entityManager.find(User.class, userid);
        user.getCars().add(car);
        car.setOwner(user);
    }

}
