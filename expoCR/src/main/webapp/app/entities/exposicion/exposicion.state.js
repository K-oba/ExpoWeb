(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('exposicion', {
            parent: 'entity',
            url: '/exposicion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Exposicions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exposicion/exposicions.html',
                    controller: 'ExposicionController',
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
        .state('exposicion-detail', {
            parent: 'exposicion',
            url: '/exposicion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Exposicion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exposicion/exposicion-detail.html',
                    controller: 'ExposicionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Exposicion', function($stateParams, Exposicion) {
                    return Exposicion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'exposicion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('exposicion-detail.edit', {
            parent: 'exposicion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exposicion/exposicion-dialog.html',
                    controller: 'ExposicionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Exposicion', function(Exposicion) {
                            return Exposicion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exposicion.new', {
            parent: 'exposicion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exposicion/exposicion-dialog.html',
                    controller: 'ExposicionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                estadoExpo: null,
                                fechaInicio: null,
                                fechaFin: null,
                                coordenadas: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('exposicion', null, { reload: 'exposicion' });
                }, function() {
                    $state.go('exposicion');
                });
            }]
        })
        .state('exposicion.edit', {
            parent: 'exposicion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exposicion/exposicion-dialog.html',
                    controller: 'ExposicionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Exposicion', function(Exposicion) {
                            return Exposicion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exposicion', null, { reload: 'exposicion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exposicion.invite', {
            parent: 'exposicion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exposicion/exposicion-invite-dialog.html',
                    controller: 'ExposicionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Exposicion', function(Exposicion) {
                            return Exposicion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exposicion', null, { reload: 'exposicion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exposicion.delete', {
            parent: 'exposicion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exposicion/exposicion-delete-dialog.html',
                    controller: 'ExposicionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Exposicion', function(Exposicion) {
                            return Exposicion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exposicion', null, { reload: 'exposicion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
