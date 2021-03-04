import { Identifiable } from "./identifiable";

export interface UserComment extends Identifiable {
    userId: string;
    username: string;
    publishedAt: Date;
    editedAt: Date;
    articleId: string;
    content: string;
    //see how to display recursive data structures in angular
    responseComments: UserComment[]; 
}