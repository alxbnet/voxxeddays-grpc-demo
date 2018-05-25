package com.voxxeddays.grpcdemo.providers;

import com.voxxeddays.grpcdemo.Temperature;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Randomly generates {@link Temperature}.
 */
public class RandomTemperatureProvider implements Supplier<Temperature> {

    private final Random random = new Random();

    @Override
    public Temperature get() {
        float degrees = 10 + random.nextInt(20) + random.nextFloat();
        return Temperature.newBuilder().setDegrees(degrees).setUnits(Temperature.Units.CELSUIS).build();
    }
}
