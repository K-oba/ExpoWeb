(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('SubCategoria', SubCategoria);

    SubCategoria.$inject = ['$resource'];

    function SubCategoria ($resource) {
        var resourceUrl =  'api/sub-categorias/:id';

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
