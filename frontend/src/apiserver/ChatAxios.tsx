import axios, {AxiosInstance, AxiosResponse} from 'axios';
import { ChatRoom } from '../model/ChatModel';
import { DefaultResponse } from '../model/DefaultResponse';

class ChatInvoker {
    
    instance : AxiosInstance

    constructor(){
        this.instance = axios.create({
            timeout : 5000,
            headers: {
                "Content-Type" : "application/json"
            },
            baseURL : "http://localhost:8080/"
        });
    }

    async fetchRooms() : Promise<AxiosResponse<DefaultResponse<ChatRoom[]>>>{
        const url = "/api/chat/rooms";
        const result = await this.instance.get(url);
        return result;
    }
}

export const chatInvoker = new ChatInvoker();