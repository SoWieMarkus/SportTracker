function initializeMap() {
    let mapOptions = {
        center: [50.95, 13.45], // Zentrum der Karte
        zoom: 8, //Zoomlevel
        layers: [] // Layer der Map, hier erstmal leer, wird gleich erweitert.
    };

    let divIdOfMap = "mapid";
    map = L.map(divIdOfMap, mapOptions);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        'attribution': 'Kartendaten &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> Mitwirkende',
        'useCache': true
    }).addTo(map);
}

function showTracking(tracking) {

    let path = [];

    for (let position of tracking) {
        path.push([position.latitude,position.longitude]);
    }

    let line = L.polyline(path, {
        color: "#005d79",
        weight: 5,
        smoothFactor: 1
        //dashArray: '10, 10', dashOffset: '0'
    }).addTo(map);

    map.fitBounds(line.getBounds());
}

function addMarker(coordinate) {
    let marker = L.marker(coordinate);
    marker.addTo(map);
    markers.push(marker);
    return marker;
}


let markers = []
function showLiveTracking(tracking, location){

    map.setView([location.latitude, location.longitude], 18);

    for (let marker of markers) {
        map.removeLayer(marker);
    }

    addMarker([location.latitude, location.longitude]);


    let path = [];

    for (let position of tracking) {
        path.push([position.latitude,position.longitude]);
    }

    let line = L.polyline(path, {
        color: "#005d79",
        weight: 5,
        smoothFactor: 1
        //dashArray: '10, 10', dashOffset: '0'
    }).addTo(map);

    markers.push(line);

}