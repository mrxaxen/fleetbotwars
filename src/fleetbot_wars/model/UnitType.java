package fleetbot_wars.model;

import java.net.URL;

enum UnitType {
    TREE(UnitType.class.getResource("/resources/unit.png"));

    private URL url;
    URL getUrl() {
        return url;}

    UnitType(URL url) {
        this.url = url;
    }
}
