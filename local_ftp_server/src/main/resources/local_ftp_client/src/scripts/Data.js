export class Data {
    constructor() {
        this.settings = new Settings();
    }

    saveToLocalStorage() {
        localStorage.setItem('local_ftp-data', JSON.stringify(this));
    }

    static loadFromLocalStorage() {
        const data = localStorage.getItem('local_ftp-data');
        if (data) {
            const parsedData = JSON.parse(data);
            const dataObj = new Data();
            Object.assign(dataObj, parsedData);
            return dataObj;
        }
        return new Data();
    }

}

class Settings {
    constructor() {
        this.appLaunches = 0;
        this.nickname = "guest";
        this.darkMode = "system";
        this.hapticFeedback = false;
        this.language = "English";
        this.version = null;
    }
}

export default Data;