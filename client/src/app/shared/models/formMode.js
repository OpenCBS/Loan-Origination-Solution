/**
 * Created by alopatin on 02-May-17.
 */
"use strict";
(function (FormMode) {
    FormMode[FormMode["create"] = 0] = "create";
    FormMode[FormMode["edit"] = 1] = "edit";
    FormMode[FormMode["view"] = 2] = "view";
})(exports.FormMode || (exports.FormMode = {}));
var FormMode = exports.FormMode;
