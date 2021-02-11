import { Identifiable } from './identifiable';

export interface UserModel extends Identifiable {
    username : string;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
}