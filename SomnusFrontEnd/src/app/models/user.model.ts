import { Identifiable } from './identifiable';

export interface UserModel extends Identifiable {
    userId: string;
    displayName?: string;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
    pictureUrl: string;
}