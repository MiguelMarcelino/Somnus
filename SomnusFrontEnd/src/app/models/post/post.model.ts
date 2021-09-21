import { Identifiable } from '../identifiable';

export interface PostModel extends Identifiable {
    postName: string;
    normalizedName?: string;
    userId: string;
    authorUserName: string;
    description: string;
    datePublished: Date;
    lastUpdate: Date;
    content: any;
}
