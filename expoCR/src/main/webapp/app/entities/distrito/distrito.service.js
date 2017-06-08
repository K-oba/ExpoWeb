(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Distrito', Distrito);

    Distrito.$inject = ['$resource'];

    function Distrito ($resource) {
        var resourceUrl =  'api/distritos/:id';

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
