package fleetbot_wars.model.enums;

public enum ResourceType{
    food("Food"), wood("Wood"), gold("Gold"), stone("Stone"), upgrade("Upgrade");
    final String name;

    ResourceType(String name) {
        this.name = name;
    }

    public String getName() {return name;}
}