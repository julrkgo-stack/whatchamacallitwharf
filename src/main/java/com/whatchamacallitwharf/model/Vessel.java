package com.whatchamacallitwharf.model;

public class Vessel {
    private int id;
    private String name;
    private String type;
    private String color;
    private String pattern;
    private String material;
    private int capacity;
    private boolean dishwasherSafe;

    public Vessel(int id, String name, String type, String color, String pattern, String material, int capacity, boolean dishwasherSafe) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.pattern = pattern;
        this.material = material;
        this.capacity = capacity;
        this.dishwasherSafe = dishwasherSafe;
    }
    public Vessel(String name, String type, String color, String pattern, String material, int capacity, boolean dishwasherSafe) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.pattern = pattern;
        this.material = material;
        this.capacity = capacity;
        this.dishwasherSafe = dishwasherSafe;
    }
    public Vessel() {
        this.name = "";
        this.type = "";
        this.color = "";
        this.pattern = "";
        this.material = "";
        this.capacity = 0;
        this.dishwasherSafe = false;        
    }

    // getters

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }    
    public void setType(String type) {
        this.type = type;
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getPattern() {
        return this.pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    public String getMaterial() {
        return this.material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public int getCapacity() {
        return this.capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public boolean getDishwasherSafe() {
        return this.dishwasherSafe;
    }
    public void setDishwasherSafe(boolean dishwasherSafe) {
        this.dishwasherSafe = dishwasherSafe;
    }


    @Override
    public String toString() {
        return "Vessel{name=" + name + ", type=" + type + ", color=" + color + ", id=" + id + "}";
    }

}
// 2. Data Structures (Entities)
// Plain objects representing database tables
