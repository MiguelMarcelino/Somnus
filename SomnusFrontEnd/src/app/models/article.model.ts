import { Identifiable } from './identifiable';

export interface ArticleModel extends Identifiable {
    articleName: string;
    userId: string;
    authorUserName: string;
    description: string;
    datePublished: Date;
    lastUpdate: Date;
    topic: String;
    content: any;
}