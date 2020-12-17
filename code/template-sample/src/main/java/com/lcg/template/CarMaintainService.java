package com.lcg.template;

import java.util.ArrayList;
import java.util.List;

public class CarMaintainService {
    private List<CarMaintain> cars;

    public CarMaintainService(List<CarMaintain> cars) {
        this.cars = cars;
    }

    public void startMaintain(){
        cars.forEach(CarMaintain::maintain);
    }

    public static void main(String[] args) {
        CarMaintain lamborghini = new LamborghiniCar();
        CarMaintain porsche = new PorscheCar();
        CarMaintainService service = new CarMaintainService(new ArrayList<CarMaintain>(){{
            add(lamborghini);
            add(porsche);
        }});
        service.startMaintain();
    }
}
