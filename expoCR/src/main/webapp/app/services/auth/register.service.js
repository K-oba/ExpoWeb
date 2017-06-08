(function () {
    'use strict';

    angular
        .module('expoCrApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
