import { makeAutoObservable } from 'mobx';

class AppStore {

    data = 0;

    constructor(){
        makeAutoObservable(this);
    }

    addData(number : number){
        this.data+=number;
    }

}


export const appStore = new AppStore();