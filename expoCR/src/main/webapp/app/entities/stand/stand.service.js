(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Stand', Stand);

    Stand.$inject = ['$resource'];

    function Stand ($resource) {
        var resourceUrl =  'api/stands/:id';

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
