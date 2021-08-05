import { makeAutoObservable } from 'mobx';

class AppStore {

    isChatModalOpened = false;

    constructor() {
        makeAutoObservable(this);
    }

    setChatModalOpened(state: boolean) {
        this.isChatModalOpened = state;
    }

}


export const appStore = new AppStore();