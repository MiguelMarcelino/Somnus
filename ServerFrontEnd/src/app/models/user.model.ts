import { Identifiable } from './identifiable';

export interface UserModel extends Identifiable {
    firstName : string;
    lastName: string;
    email: string;
    role: string;
}