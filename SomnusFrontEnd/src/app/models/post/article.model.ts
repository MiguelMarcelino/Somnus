import { Identifiable } from '../identifiable';
import { PostModel } from './post.model';

export interface ArticleModel extends Identifiable, PostModel {
    topic: String;
    normalizedTopic?: string;
}

