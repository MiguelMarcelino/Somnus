import { Identifiable } from './identifiable';

export interface FeedbackModel extends Identifiable{
    title: string;
    content: string;
}