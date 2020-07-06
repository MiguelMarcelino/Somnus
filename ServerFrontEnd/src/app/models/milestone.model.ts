import { Identifiable } from './identifiable';

export interface MilestoneModel extends Identifiable{
    title: string;
    year: Number;
    description: string;
}