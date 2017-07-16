(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('invitacion', {
            parent: 'app',
            url: '/invitacion',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/invitacion.html',
                    controller: 'InvitacionController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
