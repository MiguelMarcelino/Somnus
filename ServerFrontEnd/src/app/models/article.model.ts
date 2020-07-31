import { Identifiable } from './identifiable';

export interface ArticleModel extends Identifiable {
    articleName: string;
    authorUserName: string;
    description: string;
    content: any;
}