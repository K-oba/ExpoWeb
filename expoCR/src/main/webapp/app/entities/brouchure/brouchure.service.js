(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('Brouchure', Brouchure);

    Brouchure.$inject = ['$resource'];

    function Brouchure ($resource) {
        var resourceUrl =  'api/brouchures/:id';

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
