package fleetbot_wars.model;

import java.net.URL;

enum GroundType {
    BASE(GroundType.class.getResource("/resources/ground.png"));

    private URL url;
    URL getUrl() {return url;}

    GroundType(URL url) {this.url = url;}
}
