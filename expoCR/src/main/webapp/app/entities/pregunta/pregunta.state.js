(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pregunta', {
            parent: 'entity',
            url: '/pregunta?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Preguntas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pregunta/preguntas.html',
                    controller: 'PreguntaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('pregunta-detail', {
            parent: 'pregunta',
            url: '/pregunta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pregunta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pregunta/pregunta-detail.html',
                    controller: 'PreguntaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pregunta', function($stateParams, Pregunta) {
                    return Pregunta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pregunta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pregunta-detail.edit', {
            parent: 'pregunta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta/pregunta-dialog.html',
                    controller: 'PreguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pregunta', function(Pregunta) {
                            return Pregunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pregunta.new', {
            parent: 'pregunta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta/pregunta-dialog.html',
                    controller: 'PreguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pregunta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pregunta', null, { reload: 'pregunta' });
                }, function() {
                    $state.go('pregunta');
                });
            }]
        })
        .state('pregunta.edit', {
            parent: 'pregunta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta/pregunta-dialog.html',
                    controller: 'PreguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pregunta', function(Pregunta) {
                            return Pregunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pregunta', null, { reload: 'pregunta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pregunta.delete', {
            parent: 'pregunta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta/pregunta-delete-dialog.html',
                    controller: 'PreguntaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pregunta', function(Pregunta) {
                            return Pregunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pregunta', null, { reload: 'pregunta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
