package fleetbot_wars.model.enums;

public enum ResourceType{
    food("Food", "/resources/resources/food.png"),
    wood("Wood", "/resources/resources/wood.png"),
    gold("Gold", "/resources/resources/gold.png"),
    stone("Stone", "/resources/resources/stone.png"),
    upgrade("Upgrade", "/resources/resources/upgrade.png");
    final String URL ;
    final String name;

    ResourceType(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public String getName() {return name;}
    public String getURL() {return URL;}
}