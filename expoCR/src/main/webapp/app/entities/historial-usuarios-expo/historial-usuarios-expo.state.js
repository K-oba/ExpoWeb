(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('historial-usuarios-expo', {
            parent: 'entity',
            url: '/historial-usuarios-expo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HistorialUsuariosExpos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/historial-usuarios-expo/historial-usuarios-expos.html',
                    controller: 'HistorialUsuariosExpoController',
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
        .state('historial-usuarios-expo-detail', {
            parent: 'historial-usuarios-expo',
            url: '/historial-usuarios-expo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HistorialUsuariosExpo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/historial-usuarios-expo/historial-usuarios-expo-detail.html',
                    controller: 'HistorialUsuariosExpoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'HistorialUsuariosExpo', function($stateParams, HistorialUsuariosExpo) {
                    return HistorialUsuariosExpo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'historial-usuarios-expo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('historial-usuarios-expo-detail.edit', {
            parent: 'historial-usuarios-expo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historial-usuarios-expo/historial-usuarios-expo-dialog.html',
                    controller: 'HistorialUsuariosExpoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HistorialUsuariosExpo', function(HistorialUsuariosExpo) {
                            return HistorialUsuariosExpo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('historial-usuarios-expo.new', {
            parent: 'historial-usuarios-expo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historial-usuarios-expo/historial-usuarios-expo-dialog.html',
                    controller: 'HistorialUsuariosExpoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idExpo: null,
                                deviceId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('historial-usuarios-expo', null, { reload: 'historial-usuarios-expo' });
                }, function() {
                    $state.go('historial-usuarios-expo');
                });
            }]
        })
        .state('historial-usuarios-expo.edit', {
            parent: 'historial-usuarios-expo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historial-usuarios-expo/historial-usuarios-expo-dialog.html',
                    controller: 'HistorialUsuariosExpoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HistorialUsuariosExpo', function(HistorialUsuariosExpo) {
                            return HistorialUsuariosExpo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('historial-usuarios-expo', null, { reload: 'historial-usuarios-expo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('historial-usuarios-expo.delete', {
            parent: 'historial-usuarios-expo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historial-usuarios-expo/historial-usuarios-expo-delete-dialog.html',
                    controller: 'HistorialUsuariosExpoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HistorialUsuariosExpo', function(HistorialUsuariosExpo) {
                            return HistorialUsuariosExpo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('historial-usuarios-expo', null, { reload: 'historial-usuarios-expo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
