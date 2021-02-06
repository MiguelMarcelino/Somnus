import { Identifiable } from './identifiable';

export interface FeedbackModel extends Identifiable{
    feedbackTitle: string;
    feedback: string;
}