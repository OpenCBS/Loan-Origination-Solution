import { map } from 'rxjs/operators';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';
import {
  ProfileService,
  ProfileFormModalComponent,
  ProfileModalService,
  ApplicationFormModalComponent,
  ApplicationService,
  ApplicationModalService
} from '../shared';

import { Profile } from '../shared/models';

import { AppSettings } from '../app.settings';
import { FileUploadComponent } from '../shared/modules/cbs-file-upload/file-upload';

@Component({
  selector: 'cbs-profiles',
  templateUrl: 'profiles.component.html',
  styleUrls: ['profiles.component.scss']
})

export class ProfilesComponent implements OnInit {
  @ViewChild(ProfileFormModalComponent, {static: false}) private profileFormModal: ProfileFormModalComponent;
  @ViewChild(ApplicationFormModalComponent, {static: false}) private createApplicationFormModal: ApplicationFormModalComponent;

  public profilesData: any[];
  public queryPageObject: any;
  public queryObject: any = {};
  public pagerData: any = {};
  public currentPage = 0;
  public isLoading = true;
  public noData = true;
  public sortDirection = 1;
  public propertyName = 'id';
  public profileId: number;
  public searchQuery = '';

  public permissionCreateProfile: string = AppSettings.PERMISSION_NAMES['CREATE_PROFILE'];
  public permissionCreateApplication: string = AppSettings.PERMISSION_NAMES['CREATE_APPLICATION'];
  public permissionEditProfile: string = AppSettings.PERMISSION_NAMES['EDIT_PROFILE'];
  public permissionDeleteProfile: string = AppSettings.PERMISSION_NAMES['DELETE_PROFILE'];

  constructor(private profileService: ProfileService,
              private applicationService: ApplicationService,
              private applicationModalService: ApplicationModalService,
              private modalService: ProfileModalService,
              private route: ActivatedRoute,
              private fileUploadComponent: FileUploadComponent,
              private router: Router) {
  }


  ngOnInit() {
    this.queryPageObject = this.route
      .queryParams.pipe(
        map(params => params || null));
    this.queryPageObject.subscribe((obj: any) => {
      this.queryObject = JSON.parse(JSON.stringify(obj));
      this.populate(this.queryObject);
    });
  }

  clearSearch() {
    this.changeSearchQueryParams();
  }

  search(query) {
    this.changeSearchQueryParams(query);
  }

  changeSearchQueryParams(query?) {
    this.queryObject.query = query || '';
    this.queryObject.page = 1;

    const navigationExtras: NavigationExtras = {
      queryParams: this.queryObject.query ? this.queryObject : {}
    };

    this.router.navigate(['/profiles'], navigationExtras);
  }

  populate(obj) {
    this.profileService.getPeople(obj).subscribe(
      data => {
        if (data.content.length) {
          this.noData = false;
          this.isLoading = false;
          let arr = [];
          data.content.map(profile => {
            if (profile.profileFieldSections['0'].profileFields.length) {
              arr.push({
                id: profile.id,
                firstName: this.getFirstNameValue(profile.profileFieldSections['0'].profileFields),
                lastName: this.getLastNameValue(profile.profileFieldSections['0'].profileFields),
                email: this.getEmailValue(profile.profileFieldSections['0'].profileFields),
              });
            }
          });
          this.profilesData = arr;
          this.pagerData.size = data.size;
          this.pagerData.total = data.totalElements;
          this.currentPage = data.number + 1;
        } else {
          this.isLoading = false;
          this.noData = true;
        }
      },
      err => {
        this.isLoading = false;
        this.noData = true;
      }
    );
  }

  getFirstNameValue(value) {
    for (let val of value) {
      if (val.code === 'first_name' && val.profileFieldValues.length) {
        return val.profileFieldValues['0'].value
      }
    }
  }

  getLastNameValue(value) {
    for (let val of value) {
      if (val.code === 'last_name' && val.profileFieldValues.length) {
        return val.profileFieldValues['0'].value
      }
    }
  }

  getEmailValue(value) {
    for (let val of value) {
      if (val.code === 'email' && val.profileFieldValues.length) {
        return val.profileFieldValues['0'].value
      }
    }
  }

  gotoPage(page: number) {
    this.queryObject.page = page;
    this.isLoading = true;

    const navigationExtras: NavigationExtras = {
      queryParams: this.queryObject
    };
    this.router.navigate(['/profiles'], navigationExtras);
  }

  addNewProfile() {
    this.profileFormModal.openModal(new Profile(), true);
    window.localStorage.removeItem('profileId');
  }

  editProfile(profile: Profile) {
    window.localStorage.removeItem('profileId');
    window.localStorage.setItem('profileId', profile.id.toString())
    this.profileFormModal.openModal(profile, false);
  }

  deleteProfile(profile: Profile) {
    if (confirm('Are you sure you want to delete profile?') === false) {
      return;
    }

    this.profileService.deleteProfile(profile.id)
      .subscribe(
        res => {
          this.refreshCurrentRoute();
        },
        err => {
          alert(err.message);
        });
  }

  createApplication(profile: Profile) {
    this.createApplicationFormModal.openModal(profile);
  }

  refreshCurrentRoute() {
    this.populate(this.queryObject);
  }

  saveProfileData(data) {
    let sendToFields = {
      profileSections: [],
    };
    let attachments = [];
    data.data.profileSections.map(val => {
      sendToFields.profileSections.push({
        sectionCode: val.sectionCode,
        values: val.values.map(v => {
          if (v.code === 'id_photo') {
            attachments.push({
              sectionCode: val.sectionCode,
              fieldCode: v.code,
              files: v.values
            });
          } else {
            return v
          }
        })
      });
    });

    if (data.isNew) {
      sendToFields['isNew'] = true;
      this.profileService.createProfile(sendToFields).subscribe(
        resp => {
          this.profileId = resp.id;
          if (attachments.length) {
            this.uploadAttachment({id: this.profileId, attachments: attachments});
          }
          this.refresh(true);
        },
        err => {
          this.modalService.announceDataChange({status: 'error', isNew: true});
        });
    } else {
      this.profileService.updateProfile(sendToFields, data.id).subscribe(
        resp => {
          this.profileId = resp.id
          if (attachments.length && attachments[0].files[0].files) {
            this.uploadAttachment({id: this.profileId, attachments: attachments});
          }
          this.refresh(false);
        },
        err => {
          this.modalService.announceDataChange({status: 'error', isNew: false});
        });
    }
  }

  uploadAttachment(value) {
    this.fileUploadComponent.upload({
      id: value.id,
      fieldValue: value.attachments
    });
  }

  refresh(isNew) {
    this.modalService.announceDataChange({status: 'success', isNew: isNew});
    this.refreshCurrentRoute();
  }

  createPostApplication(data) {
    this.applicationService.createApplication(data).subscribe(
      resp => {
        this.applicationModalService.announceDataChange({status: 'success'});
      },
      err => {
        this.applicationModalService.announceDataChange({status: 'error', err});
      }
    );
  }
}
