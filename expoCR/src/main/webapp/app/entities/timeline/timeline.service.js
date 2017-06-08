(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Timeline', Timeline);

    Timeline.$inject = ['$resource'];

    function Timeline ($resource) {
        var resourceUrl =  'api/timelines/:id';

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
