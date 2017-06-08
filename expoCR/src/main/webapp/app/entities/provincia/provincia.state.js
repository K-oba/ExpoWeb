(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provincia', {
            parent: 'entity',
            url: '/provincia?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Provincias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provincia/provincias.html',
                    controller: 'ProvinciaController',
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
        .state('provincia-detail', {
            parent: 'provincia',
            url: '/provincia/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Provincia'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provincia/provincia-detail.html',
                    controller: 'ProvinciaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Provincia', function($stateParams, Provincia) {
                    return Provincia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provincia',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provincia-detail.edit', {
            parent: 'provincia-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provincia/provincia-dialog.html',
                    controller: 'ProvinciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Provincia', function(Provincia) {
                            return Provincia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provincia.new', {
            parent: 'provincia',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provincia/provincia-dialog.html',
                    controller: 'ProvinciaDialogController',
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
                    $state.go('provincia', null, { reload: 'provincia' });
                }, function() {
                    $state.go('provincia');
                });
            }]
        })
        .state('provincia.edit', {
            parent: 'provincia',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provincia/provincia-dialog.html',
                    controller: 'ProvinciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Provincia', function(Provincia) {
                            return Provincia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provincia', null, { reload: 'provincia' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provincia.delete', {
            parent: 'provincia',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provincia/provincia-delete-dialog.html',
                    controller: 'ProvinciaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Provincia', function(Provincia) {
                            return Provincia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provincia', null, { reload: 'provincia' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
