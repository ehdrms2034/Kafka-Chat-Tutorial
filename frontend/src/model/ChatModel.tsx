export interface ChatMessage {
    messageId: number;
    roomId: number;
    userId: number;
    username: string;
    content: string;
    createdDateTime?: Date;
    updatedDateTime?: Date;
}


export interface ChatRoom {
    roomId : number;
    title : string;
    description : string;
}