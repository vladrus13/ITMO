var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
import Swal from "sweetalert2";
var VueSimpleAlert = (function () {
    function VueSimpleAlert() {
    }
    VueSimpleAlert.alert = function (message, title, type, options) {
        return new Promise(function (resolve) {
            var mixedOptions = __assign(__assign({}, VueSimpleAlert.globalOptions), options);
            mixedOptions.title = title || mixedOptions.title;
            mixedOptions.text = message || mixedOptions.text;
            mixedOptions.type = type || mixedOptions.type;
            Swal.fire(mixedOptions)
                .then(function () {
                resolve(true);
            })
                .catch(function () {
                resolve(true);
            });
        });
    };
    VueSimpleAlert.confirm = function (message, title, type, options) {
        return new Promise(function (resolve, reject) {
            var mixedOptions = __assign(__assign({}, VueSimpleAlert.globalOptions), options);
            mixedOptions.title = title || mixedOptions.title;
            mixedOptions.text = message || mixedOptions.text;
            mixedOptions.type = type || mixedOptions.type;
            mixedOptions.showCancelButton = true;
            Swal.fire(mixedOptions)
                .then(function (r) {
                if (r.value === true) {
                    resolve(true);
                }
                else
                    reject();
            })
                .catch(function () { return reject(); });
        });
    };
    VueSimpleAlert.prompt = function (message, defaultText, title, type, options) {
        return new Promise(function (resolve, reject) {
            var mixedOptions = __assign(__assign({}, VueSimpleAlert.globalOptions), options);
            mixedOptions.title = title || mixedOptions.title;
            mixedOptions.inputValue = defaultText;
            mixedOptions.text = message || mixedOptions.text;
            mixedOptions.type = type || mixedOptions.type;
            mixedOptions.showCancelButton = true;
            mixedOptions.input = mixedOptions.input || "text";
            Swal.fire(mixedOptions)
                .then(function (r) {
                if (r.value) {
                    resolve(r.value);
                }
                else
                    reject();
            })
                .catch(function () {
                return reject();
            });
        });
    };
    VueSimpleAlert.fire = function (options) {
        return Swal.fire(options);
    };
    VueSimpleAlert.install = function (Vue, options) {
        VueSimpleAlert.globalOptions = options;
        Vue.alert = VueSimpleAlert.alert;
        Vue.confirm = VueSimpleAlert.confirm;
        Vue.prompt = VueSimpleAlert.prompt;
        Vue.fire = VueSimpleAlert.fire;
        if (!Vue.prototype.hasOwnProperty("$alert")) {
            Vue.prototype.$alert = VueSimpleAlert.alert;
        }
        if (!Vue.prototype.hasOwnProperty("$confirm")) {
            Vue.prototype.$confirm = VueSimpleAlert.confirm;
        }
        if (!Vue.prototype.hasOwnProperty("$prompt")) {
            Vue.prototype.$prompt = VueSimpleAlert.prompt;
        }
        if (!Vue.prototype.hasOwnProperty("$fire")) {
            Vue.prototype.$fire = VueSimpleAlert.fire;
        }
    };
    return VueSimpleAlert;
}());
export { VueSimpleAlert };
export default VueSimpleAlert;
//# sourceMappingURL=index.js.map