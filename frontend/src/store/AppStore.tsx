import { makeAutoObservable } from 'mobx';
import { chatInvoker } from '../apiserver/ChatAxios';
import { ChatMessage, ChatRoom } from '../model/ChatModel';
import { CompatClient, Stomp, StompSubscription } from "@stomp/stompjs";
import SockJS from 'sockjs-client';

class AppStore {

    isChatModalOpened = false;

    stompClient: CompatClient;

    currentRoom?: ChatRoom;
    currentSubscription?: StompSubscription;

    username: string = "defaultUser";
    rooms: ChatRoom[] = [];
    messages: ChatMessage[] = [];


    constructor() {
        makeAutoObservable(this);
        const sockJS = new SockJS("http://localhost:8080/socket-chat");
        this.stompClient = Stomp.over(sockJS);

        this.stompClient.onConnect = () => {
            console.log("소켓 연결됨");
        }
        this.stompClient.onDisconnect = () => {
            console.log("소켓 연결끊김");
        }
        this.stompClient.onStompError = () => {
            console.error("dsfad");
        }

        this.stompClient.activate();

    }

    setCurrentRoom(chatRoom?: ChatRoom) {
        this.currentRoom = chatRoom;
    }

    setChatModalOpened(state: boolean) {
        if (state === false) {

        }
        this.isChatModalOpened = state;
    }

    setUsername(username: string) {
        this.username = username;
    }

    async updateRooms() {
        const response = await chatInvoker.fetchRooms();
        this.rooms = response.data.result;

    }

    initChatMessages() {
        this.messages = [];
    }

    subscribeRoom(roomId: number, callback?: Function) {
        this.currentSubscription = this.stompClient.subscribe(`/topic/chat/${roomId}`, (data) => {
            const message: ChatMessage = JSON.parse(data.body.toString())
            console.log("메시지", message);
            if (callback) {
                callback(message);
            }
        });
    }

    describeRoom() {
        if (this.currentSubscription) {
            this.currentSubscription.unsubscribe();
        }
    }

    sendMessageToRoom(roomId: number, content: string) {

        const newMessage: ChatMessage = {
            messageId: 0,
            roomId: roomId,
            userId: 0,
            username: this.username ,
            content: content
        }

        this.stompClient.publish({
            destination: `/app/socket/chat/send`,
            body: JSON.stringify(newMessage)
        });
    }

    receiveMessage(roomId: number, chatMessage: ChatMessage) {
        this.messages.push(chatMessage);
    }

}


export const appStore = new AppStore();