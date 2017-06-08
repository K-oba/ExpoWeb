(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('charla', {
            parent: 'entity',
            url: '/charla?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Charlas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/charla/charlas.html',
                    controller: 'CharlaController',
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
        .state('charla-detail', {
            parent: 'charla',
            url: '/charla/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Charla'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/charla/charla-detail.html',
                    controller: 'CharlaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Charla', function($stateParams, Charla) {
                    return Charla.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'charla',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('charla-detail.edit', {
            parent: 'charla-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/charla/charla-dialog.html',
                    controller: 'CharlaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Charla', function(Charla) {
                            return Charla.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('charla.new', {
            parent: 'charla',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/charla/charla-dialog.html',
                    controller: 'CharlaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                fechaInicio: null,
                                fechaFin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('charla', null, { reload: 'charla' });
                }, function() {
                    $state.go('charla');
                });
            }]
        })
        .state('charla.edit', {
            parent: 'charla',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/charla/charla-dialog.html',
                    controller: 'CharlaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Charla', function(Charla) {
                            return Charla.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('charla', null, { reload: 'charla' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('charla.delete', {
            parent: 'charla',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/charla/charla-delete-dialog.html',
                    controller: 'CharlaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Charla', function(Charla) {
                            return Charla.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('charla', null, { reload: 'charla' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
