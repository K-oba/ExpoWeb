(function() {
    'use strict';
    angular
        .module('expoCrApp')
        .factory('HistorialUsuariosExpo', HistorialUsuariosExpo);

    HistorialUsuariosExpo.$inject = ['$resource', 'DateUtils'];

    function HistorialUsuariosExpo ($resource, DateUtils) {
        var resourceUrl =  'api/historial-usuarios-expos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
