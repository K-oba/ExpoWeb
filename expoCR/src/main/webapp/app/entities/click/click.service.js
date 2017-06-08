(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Click', Click);

    Click.$inject = ['$resource'];

    function Click ($resource) {
        var resourceUrl =  'api/clicks/:id';

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
