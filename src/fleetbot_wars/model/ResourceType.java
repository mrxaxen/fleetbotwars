package fleetbot_wars.model;

enum ResourceType { // TEMPORARY
    WOOD("Wood"),
    STONE("Stone"),
    GOLD("Gold"),
    FOOD("Food");

    final String name;

    ResourceType(String name) {
        this.name = name;
    }

}
