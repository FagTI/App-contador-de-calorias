package entities;

import com.google.firebase.firestore.DocumentId;

import java.util.HashMap;
import java.util.Map;

public class User {

    @DocumentId
    private String documentId;
    public String name;
    public int age;
    public int height;
    public double weight;
    public double fat;
    public int activity;
    public int proteinConsumption;
    public int carbConsumption;
    public int fatConsumption;
    public Double basalMetabolicRate;
    public int totalMetabolicRate;
    public String gender;
    public String email;
    public boolean adm;


    public User(String name, int age, int height, double weight, double fat, int activity, String gender, String email,
                double basalMetabolicRate, int totalMetabolicRate, int proteinConsumption, int carbConsumption, int fatConsumption){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.fat = fat;
        this.activity = activity;
        this.proteinConsumption = calculateProteinConsumption((int)weight);
        this.carbConsumption = calculateCarbConsumption(proteinConsumption, fatConsumption, totalMetabolicRate);
        this.fatConsumption = calculateFatConsumption((int)weight);
        this.gender = gender;
        this.basalMetabolicRate = calculateBasalMetabolicRate(weight, height, age, gender);
        this.totalMetabolicRate = calculateTotalMetabolicRate(basalMetabolicRate, activity);
        this.email = email;
        this.adm = false;
    }

    public User(){}

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        result.put("height", height);
        result.put("weight", weight);
        result.put("fat", fat);
        result.put("activity", activity);
        result.put("gender", gender);
        result.put("email", email);
        result.put("adm", adm);
        result.put("basalMetabolicRate",basalMetabolicRate);
        result.put("totalMetabolicRate",totalMetabolicRate);
        result.put("proteinConsumption", proteinConsumption);
        result.put("carbConsumption", carbConsumption);
        result.put("fatConsumption", fatConsumption);
        return result;
    }

    public static Double calculateBasalMetabolicRate(double mass, int height, int age, String gender) {

        double basalMetabolicRate = 0;

        double calculation = (13.75 * mass) + (5 * height) - (6.76 * age);
 
        if (gender.equals("male")) {
            basalMetabolicRate = calculation + 66.5;
        } else if (gender.equals("female")) {
            basalMetabolicRate = calculation + 665;
        }
        return basalMetabolicRate;
    }

    public static int calculateTotalMetabolicRate(Double basalMetabolicRate, int activity) {

        Double totalMetabolicRate = 0.0;

        switch (activity) {
            case 1:
                totalMetabolicRate = basalMetabolicRate * 1.2;
                break;
            case 2:
                totalMetabolicRate = basalMetabolicRate * 1.375;
                break;
            case 3:
                totalMetabolicRate = basalMetabolicRate * 1.55;
                break;
            case 4:
                totalMetabolicRate = basalMetabolicRate * 1.725;
                break;
            case 5:
                totalMetabolicRate = basalMetabolicRate * 1.9;
                break;
        }
        return totalMetabolicRate.intValue();
    }

    public static int calculateProteinConsumption(int weight)
    {
        return weight * 2;
    }

    public static int calculateCarbConsumption(int proteinConsumption, int fatConsumption, int totalMetabolicRate)
    {
        int proteinConsumedInCal = proteinConsumption * 4;
        int fatConsumedInCal = fatConsumption * 9;
        int totalCaloriesToConsume = (totalMetabolicRate - (int)(totalMetabolicRate * 0.2));

        return (totalCaloriesToConsume - proteinConsumedInCal - fatConsumedInCal) / 8;
    }

    public static int calculateFatConsumption(int weight)
    {
        return (int)(weight * 0.8);
    }

}
