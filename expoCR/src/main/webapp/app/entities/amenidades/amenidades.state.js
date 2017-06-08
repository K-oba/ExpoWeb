(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('amenidades', {
            parent: 'entity',
            url: '/amenidades?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Amenidades'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/amenidades/amenidades.html',
                    controller: 'AmenidadesController',
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
        .state('amenidades-detail', {
            parent: 'amenidades',
            url: '/amenidades/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Amenidades'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/amenidades/amenidades-detail.html',
                    controller: 'AmenidadesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Amenidades', function($stateParams, Amenidades) {
                    return Amenidades.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'amenidades',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('amenidades-detail.edit', {
            parent: 'amenidades-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/amenidades/amenidades-dialog.html',
                    controller: 'AmenidadesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Amenidades', function(Amenidades) {
                            return Amenidades.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('amenidades.new', {
            parent: 'amenidades',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/amenidades/amenidades-dialog.html',
                    controller: 'AmenidadesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                tipo: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('amenidades', null, { reload: 'amenidades' });
                }, function() {
                    $state.go('amenidades');
                });
            }]
        })
        .state('amenidades.edit', {
            parent: 'amenidades',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/amenidades/amenidades-dialog.html',
                    controller: 'AmenidadesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Amenidades', function(Amenidades) {
                            return Amenidades.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('amenidades', null, { reload: 'amenidades' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('amenidades.delete', {
            parent: 'amenidades',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/amenidades/amenidades-delete-dialog.html',
                    controller: 'AmenidadesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Amenidades', function(Amenidades) {
                            return Amenidades.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('amenidades', null, { reload: 'amenidades' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
