import { CustomFieldSectionValue } from '../core/models';
import { distinctUntilChanged, map } from 'rxjs/operators';
import { AppSettings } from '../app.settings';

export const getProfileStatus = () => {
  return state => state
    .pipe(map(s => s['status']), distinctUntilChanged());
};

export const getCurrentProfileFields = () => {
  return state => state
    .pipe(map(s => s['profileFieldSections']),
      map((sections: CustomFieldSectionValue[]) => {
        const newSections = sections.map((section: CustomFieldSectionValue) => {
          const fieldsArray = [];
          section.profileFields.map(item => {
            const tempObj = {
              value: []
            };
            if ( item['profileFieldValues'].length ) {
              item['profileFieldValues'].forEach(value => {
                if (value.value && item['fieldType'] === 'MULTIPLE_SELECT') {
                  tempObj['value'].push(value.value);
                } else {
                  tempObj['value'] = value.value;
                }
              });
            } else {
               let value: any;
               value = '';
              tempObj['value'] = value
            }

            let fieldMeta;
            if ( item ) {
              fieldMeta = item;
            }

            for (const key in fieldMeta) {
              if ( fieldMeta.hasOwnProperty(key) ) {
                tempObj[key] = fieldMeta[key];
              }
            }

            if ( tempObj['fieldType'] === 'LOOKUP' && tempObj['extra'] && tempObj['extra']['key'] && tempObj['profileFieldValues'].length) {
              let defaultValue = tempObj['profileFieldValues'] && tempObj['value']['name'] ? tempObj['value']['name'] : '';
              tempObj['extra'] = Object.assign({}, tempObj['extra'], {
                url: `${AppSettings.API_ENDPOINT}${tempObj['extra']['key']}/lookup`,
                defaultQuery: defaultValue
              });
            }

            fieldsArray.push(tempObj);
          });

          fieldsArray.sort((a, b) => {
            return a.order - b.order;
          });
          section = Object.assign({}, section, {
            values: fieldsArray
          });
          return section;
        });

        return newSections.sort((a, b) => {
          return a.order - b.order;
        });
      }), distinctUntilChanged());
};

export const getProfileCustomFields = () => {
  return state => state
    .pipe(map((s: any) => {
      const profileFields = [];
      s.map(section => {
        const newArray = [];
        section.profileFields.map(field => {
          if ( field.fieldType === 'LOOKUP' && field.extra && field.extra.key ) {
            field = Object.assign({}, field, {
              extra: {
                key: field.extra.key,
                url: `${AppSettings.API_ENDPOINT}${field.extra.key}/lookup`
              }
            });
          }
          newArray.push(field)
        });
        section = Object.assign({}, section, {
          customFields: newArray
        });
        profileFields.push(section);
      });
      const res = Object.assign({}, s, {
        profileFields: profileFields
      });
      return res.profileFields.sort((a, b) => {
        return a.order - b.order;
      });
    }), distinctUntilChanged());
};

export const getProfilesCurrentPage = () => {
  return state => state
    .pipe(map(s => s['currentPage']));
};
