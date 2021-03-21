import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Role } from 'src/app/models/role.model';
import { UserModel } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { UserController } from 'src/app/services/controllers/user-controller.service';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'app-update-user-info',
  templateUrl: './update-user-info.component.html',
  styleUrls: ['./update-user-info.component.scss']
})
export class UpdateUserInfoComponent implements OnInit {

  // app user
  user: firebase.default.User;
  userUpdateForm: FormGroup;
  userInfo: UserModel;
  roles: string[];

  constructor(
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private errorInterface: ErrorInterface,
    private userController: UserController
  ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    });
    this.userUpdateForm = this.formBuilder.group({
      id: new FormControl(''),
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      email: new FormControl(''),
      role: new FormControl(''),
    });
    this.userInfo = this.authenticationService.getCurrentUser();

    this.roles = Object.values(Role);
  }

  isAdmin() {
    if(this.userInfo) {
      return this.userInfo.role === Role.Admin;
    }
    return false;
  }

  updateUserInfo(userModel: UserModel) {
    if(this.user.uid === userModel.userId) {
      this.authenticationService.updateCurrentUserInfo(userModel);
    }
  }

  onSubmit() {
    if(this.userUpdateForm.invalid) {
      return;
    }

    if(!this.userUpdateForm.get('id') && this.isAdmin()) {
      this.errorInterface.setErrorMessage("Please fill in the id field");
      return;
    }

    let id;
    if(this.isAdmin()) {
      id = this.userUpdateForm.get('id').value;
    } else {
      id = this.user.uid;
    }

    let firstName = this.userUpdateForm.get('firstName').value;
    let lastName = this.userUpdateForm.get('lastName').value;
    let email = this.userUpdateForm.get('email').value;
    let role = this.userUpdateForm.get('role').value;

    let updatedUser: UserModel = {"userId": id, "firstName": firstName, "lastName": lastName,
      "email": email, "role": role, "photoURL": null};

    this.userController.updateUser(updatedUser).subscribe((newUserInfo: UserModel) => {

      this.errorInterface.setSuccessMessage("Your user information has been updated successfully!");
      this.updateUserInfo(newUserInfo);
      
      // reset form values
      this.userUpdateForm.get('id').setValue("");
      this.userUpdateForm.get('firstName').setValue("");
      this.userUpdateForm.get('lastName').setValue("");
      this.userUpdateForm.get('email').setValue("");
      this.userUpdateForm.get('role').setValue("");
    });
  }


}
