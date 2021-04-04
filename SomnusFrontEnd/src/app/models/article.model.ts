import { Identifiable } from './identifiable';

export interface ArticleModel extends Identifiable {
    articleName: string;
    normalizedName?: string;
    userId: string;
    authorUserName: string;
    description: string;
    datePublished: Date;
    lastUpdate: Date;
    topic: String;
    normalizedTopic?: string;
    content: any;
}

