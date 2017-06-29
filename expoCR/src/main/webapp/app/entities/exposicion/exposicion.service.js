(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Exposicion', Exposicion);

    Exposicion.$inject = ['$resource'];

    function Exposicion ($resource) {

        var resourceUrl =  'api/exposicions/:id';

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
