import { Identifiable } from './identifiable';

export interface TeamMemberModel extends Identifiable {
    memberName: string;
    photoPath: string;
    dateJoined: Date;
    contributions: Number;
}