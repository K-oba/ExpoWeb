(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rol', {
            parent: 'entity',
            url: '/rol?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rols'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rol/rols.html',
                    controller: 'RolController',
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
        .state('rol-detail', {
            parent: 'rol',
            url: '/rol/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rol'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rol/rol-detail.html',
                    controller: 'RolDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Rol', function($stateParams, Rol) {
                    return Rol.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rol',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rol-detail.edit', {
            parent: 'rol-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rol/rol-dialog.html',
                    controller: 'RolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rol', function(Rol) {
                            return Rol.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rol.new', {
            parent: 'rol',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rol/rol-dialog.html',
                    controller: 'RolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rol', null, { reload: 'rol' });
                }, function() {
                    $state.go('rol');
                });
            }]
        })
        .state('rol.edit', {
            parent: 'rol',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rol/rol-dialog.html',
                    controller: 'RolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rol', function(Rol) {
                            return Rol.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rol', null, { reload: 'rol' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rol.delete', {
            parent: 'rol',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rol/rol-delete-dialog.html',
                    controller: 'RolDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rol', function(Rol) {
                            return Rol.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rol', null, { reload: 'rol' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
