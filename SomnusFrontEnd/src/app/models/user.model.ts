import { UserController } from '../services/controllers/user-controller.service';
import { Identifiable } from './identifiable';
import { UserComment } from './user-comment.model';

export interface UserModel extends Identifiable {
    userId: string;
    displayName?: string;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
    photoURL: string;
}