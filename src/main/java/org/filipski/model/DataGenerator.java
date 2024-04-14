package org.filipski.model;

import org.filipski.schema.*;

import java.time.LocalDateTime;
import java.util.Map;

public class DataGenerator {
    //public Demo(){}
    public static Class<?>[] classes = new Class<?>[]
            {SmartphoneRegistry.class, Smartphone.class, Tester.class, Schedule.class, Review.class};


    public static Map<String, SmartphoneRegistry> registry = Map.ofEntries
        (
            Map.entry ("SG S9",   new SmartphoneRegistry ("Samsung Galaxy S9")),
            Map.entry ("SG S8",   new SmartphoneRegistry ("Samsung Galaxy S8")),
            Map.entry ("MN 6",    new SmartphoneRegistry ("Motorola Nexus 6")),
            Map.entry ("O 9",     new SmartphoneRegistry ("Oneplus 9")),
            Map.entry ("AIP 13",  new SmartphoneRegistry ("Apple iPhone 13")),
            Map.entry ("AIP 12",  new SmartphoneRegistry ("Apple iPhone 12")),
            Map.entry ("AIP 11",  new SmartphoneRegistry ("Apple iPhone 11")),
            Map.entry ("IP X",    new SmartphoneRegistry ("iPhone X")),
            Map.entry ("N 3310",  new SmartphoneRegistry ("Nokia 3310"))
        );

    public static Map<String, Smartphone> smartphones = Map.ofEntries
        (
            Map.entry("SG S9",    new Smartphone(registry.get("SG S9"))),
            Map.entry("SG S8",    new Smartphone(registry.get("SG S8"))),
            Map.entry("SG S8 .1", new Smartphone(registry.get("SG S8"))),
            Map.entry("MN 6",     new Smartphone(registry.get("MN 6"))),
            Map.entry("O 9",      new Smartphone(registry.get("O 9"))),
            Map.entry("AIP 13",   new Smartphone(registry.get("AIP 13"))),
            Map.entry("AIP 12",   new Smartphone(registry.get("AIP 12"))),
            Map.entry("AIP 11",   new Smartphone(registry.get("AIP 11"))),
            Map.entry("IP X",     new Smartphone(registry.get("IP X"))),
            Map.entry("N 3310",   new Smartphone(registry.get("N 3310")))
        );

    @SuppressWarnings("SpellCheckingInspection")
    public static Map<String, Tester> testers = Map.ofEntries
        (
            Map.entry("JE", new Tester ("John", "Eagle")),
            Map.entry("DG", new Tester ("Dorian", "Green")),
            Map.entry("GB", new Tester ("George", "Baumstein")),
            Map.entry("TJ", new Tester ("Trudy", "Jaxen")),
            Map.entry("JC", new Tester ("Jon", "Cottoneye")),
            Map.entry("VJ", new Tester ("Vladimir", "Jaxen")),
            Map.entry("KK", new Tester ("Kimoto", "Kudzumi"))
        );

    static LocalDateTime referenceDate = LocalDateTime.of(2024, 4, 14, 0, 0);

    public static TestPlan testPlan = new TestPlan
        (
            new Schedule(smartphones.get ("SG S9"), testers.get("JE"), referenceDate, 4),
            new Schedule(smartphones.get ("SG S9"), null, referenceDate.plusDays(5), 4),
            new Schedule(smartphones.get ("SG S9"), testers.get("JE"), referenceDate.plusDays(9), 4)
        );





}
