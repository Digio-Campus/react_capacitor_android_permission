import './App.css';
import React from "react";
import {WebGeolocationPermission} from "./WebGeolocationPermission";
import cameraPermission from "./PluginBridge";

function App() {
    const locationReceived = (position) => {
        console.log("position", position)
        document.getElementById("locationReceived").innerText =
            `Altitude: ${position.coords.altitude} Latitude: ${position.coords.latitude}`;
    }
    return (
        <>
            <h1>Permissions</h1>

            <h3>PWA Permission: Geolocation</h3>
            <button onClick={async () => {
                const webGeolocationPermission = new WebGeolocationPermission();
                const position = await webGeolocationPermission.requestPermission();
                locationReceived(position);
            }}>Get Location
            </button>
            <p id="locationReceived"></p>

            <h3>Android Permission: Camera</h3>
            <button onClick={async () => {
                try {
                    await cameraPermission.takePhoto();
                } catch (error) {
                    console.error('Error taking photo:', error);
                }
            }}>Take Picture
            </button>
        </>
    );
}

export default App;
