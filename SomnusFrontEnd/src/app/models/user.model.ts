import { Identifiable } from './identifiable';

export interface UserModel extends Identifiable {
    username : string;
    displayName: string;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
}