import { Identifiable } from "./identifiable";
import { UserModel } from "./user.model";

export interface UserComment extends Identifiable {
    articleId: string;
    username?: string;
    userDisplayName?: string;
    publishedAt?: Date;
    editedAt?: Date;
    content: string;
    numLikes?: number;
    //see how to display recursive data structures in angular
    responseComments?: UserComment[];
    parentId?: string;
    isUserLikedComment?: boolean;
}