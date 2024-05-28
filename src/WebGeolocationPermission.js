import {WebPlugin} from "@capacitor/core";

export class WebGeolocationPermission extends WebPlugin {

    async requestPermission():Promise<GeolocationPosition> {
        return new Promise((resolve, reject) => {
            if (typeof navigator === 'undefined' || !navigator.permissions) {
                reject(this.unavailable('Permissions API not available in this browser.'));
            }
            navigator.permissions.query({name: 'geolocation'}).then((result) => {
                if (result.state === 'granted') {
                    navigator.geolocation.getCurrentPosition((position) => {
                        resolve(position);
                    }, () => {
                        reject(this.unavailable('Permissions API not available in this browser.'));
                    });
                } else if (result.state === 'prompt') {
                    navigator.geolocation.getCurrentPosition((position) => {
                        resolve(position);
                    }, () => {
                        reject(this.unavailable('User denied geolocation permission.'));
                    });
                } else if (result.state === 'denied') {
                    reject(this.unavailable('User denied geolocation permission.'));
                }
            });
        });
    }
}
