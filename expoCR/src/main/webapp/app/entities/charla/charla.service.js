(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Charla', Charla);

    Charla.$inject = ['$resource'];

    function Charla ($resource) {
        var resourceUrl =  'api/charlas/:id';

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
