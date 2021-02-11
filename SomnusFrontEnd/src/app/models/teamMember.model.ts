import { Identifiable } from './identifiable';

export interface TeamMemberModel extends Identifiable {
    teamMemberName: string;
    photoPath: string;
    dateJoined: Date;
    numContributions: Number;
    githubUsername: string;
}