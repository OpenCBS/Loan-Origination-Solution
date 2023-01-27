export class User {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    mobilePhone: string;
    // otherLanguages: string;
    // addressProf: string;
    // postalCodeProf: string;
    // city: string;
    // country: string;
    // language: string;
    // origin: string;
    // placeOfBirthCity: string;
    // placeOfBirthCountry: string;
    role: Role;
    enabled: boolean;
    phoneNumber: string;
    address: String;
    street: String;
    postalCode: String;
    currentOccupation: String;
    citizenship: String;
    // birthDate: String;
    spokenLanguages: String;
    preferredWorkingLanguages: String;
    specialization: String;
    availability: String;
    alreadyVolunteered: String;
    support: String;
    supportOther: String;
    supportPromoters: String;
    supportPromotersOther: String;
    photoPath: String;
}

export class Role {
    id: number;
    code: string;
    name: string;
    permissions?: string[];
}
