(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Rol', Rol);

    Rol.$inject = ['$resource'];

    function Rol ($resource) {
        var resourceUrl =  'api/rols/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
