package com.jayas;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class MinReFuel 
{
    public static void main( String[] args )
    {
        Map<Float,Float> availableFuellingStations = new HashMap<Float,Float>(5);
        availableFuellingStations.put(60f,100.6f);
        availableFuellingStations.put(70f,90.6f);
        availableFuellingStations.put(90f,116.3f);
        availableFuellingStations.put(40f,103.8f);
        availableFuellingStations.put(150f,101.4f);
        availableFuellingStations.put(180f,103.4f);
        availableFuellingStations.put(200f,116.4f);
        availableFuellingStations.put(320f,99.4f);
        availableFuellingStations.put(400f,111.4f);
        availableFuellingStations.put(470f,98.4f);

        MinReFuel minReFuel = new MinReFuel();

        Map<Float,Float> answer = minReFuel.fuelingPlan(40f,10f,500,availableFuellingStations);
        answer.entrySet().stream().forEach(e -> System.out.println(" Fill "+e.getValue()+"  at "+e.getKey()));

    }

    public static Map<Float,Float> fuelingPlan(float capacityInLitres, float fuelEfficiency, float distance, Map<Float,Float> fuellingStations ) {

        //For free sort keys asc order and create a sorted Map
        TreeMap<Float,Float> sortedFuellingStations = new TreeMap<Float,Float>(fuellingStations);
        TreeMap<Float,Float> finalFuellingStations = new TreeMap<Float,Float>();

        float fuelAdded = 0;
        while(!sortedFuellingStations.isEmpty()){

            Map.Entry<Float,Float> entry = sortedFuellingStations.pollFirstEntry();
            float dist = entry.getKey();
            float distanceCovered = dist;
            float totalDistanceToCover = distance - distanceCovered;
            float fuelConsumed =  distanceCovered / fuelEfficiency;
            float distanceThatCanBeCovered = (capacityInLitres - fuelConsumed + fuelAdded) * fuelEfficiency;
            boolean isFuelAvailableForFullJourney =  distanceThatCanBeCovered >= totalDistanceToCover;
            if(isFuelAvailableForFullJourney) {
                break;
            }
            // Get subList sorted in asc order by the cost, that are with in distanceThatCanBeCovered
            List<Entry<Float,Float>> entriesToProcess = sortedFuellingStations.entrySet().stream().collect(Collectors.toList());
            entriesToProcess.add(entry);
            Optional<Entry<Float,Float>> nextTarget = entriesToProcess.stream()
            .filter(e -> e.getKey() <= distanceCovered +distanceThatCanBeCovered )
            .sorted(new Comparator<>() {

                @Override
                public int compare(Entry<Float, Float> o1, Entry<Float, Float> o2) {
                    float diff = o1.getValue() - o2.getValue();
                    return (int)diff;
                }
                
            }).findFirst();
            boolean refuelHere = false;
            if(nextTarget.isPresent()) {
                List<Float> keysToRemove = sortedFuellingStations.keySet().stream().filter(k -> k < nextTarget.get().getKey()).collect(Collectors.toList());
                keysToRemove.forEach(sortedFuellingStations::remove);
                if(nextTarget.get().getKey() == entry.getKey()) {
                    refuelHere = true;
                }

                
            } else  {
                refuelHere = true;
            }
            if(refuelHere) {
                //Refuel here.
                float fuelNeeded = totalDistanceToCover/fuelEfficiency;
                System.out.println("totalDistanceToCover : "+totalDistanceToCover);
                System.out.println("FuelNeeded : "+fuelNeeded);
                System.out.println("fuelConsumed : "+fuelConsumed);
                float numOfLitersOfFuel = Math.min(fuelNeeded,fuelConsumed);
                fuelAdded = fuelAdded + numOfLitersOfFuel;
                finalFuellingStations.put(entry.getKey(),numOfLitersOfFuel);

            };
            
            
            // Continue
           
            


        }
        return finalFuellingStations;

        
        
    }
}
