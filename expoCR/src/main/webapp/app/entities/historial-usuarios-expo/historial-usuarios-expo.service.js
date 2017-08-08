(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('HistorialUsuariosExpo', HistorialUsuariosExpo);

    HistorialUsuariosExpo.$inject = ['$resource'];

    function HistorialUsuariosExpo ($resource) {
        var resourceUrl =  'api/historial-usuarios-expos/:id';

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
