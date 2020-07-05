import { Identifiable } from './identifiable';

export interface GameAchievementModel extends Identifiable{
    title: string;
    author: string;
    description: string;
    photoPath: string;
    dateAdded: Date;
}