export interface ChatMessage {
    messageId: number;
    roomId: number;
    userId: number;
    username: string;
    content: string;
    createdDateTime?: Date;
    updatedDateTime?: Date;
}