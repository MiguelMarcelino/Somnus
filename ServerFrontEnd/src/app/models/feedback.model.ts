import { Identifiable } from './identifiable';

export interface FeedbackModel extends Identifiable{
    first_name: string;
    email: string;
    feedback: string;
}