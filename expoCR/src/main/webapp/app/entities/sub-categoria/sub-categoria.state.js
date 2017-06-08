(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sub-categoria', {
            parent: 'entity',
            url: '/sub-categoria?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SubCategorias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sub-categoria/sub-categorias.html',
                    controller: 'SubCategoriaController',
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
        .state('sub-categoria-detail', {
            parent: 'sub-categoria',
            url: '/sub-categoria/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SubCategoria'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sub-categoria/sub-categoria-detail.html',
                    controller: 'SubCategoriaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SubCategoria', function($stateParams, SubCategoria) {
                    return SubCategoria.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sub-categoria',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sub-categoria-detail.edit', {
            parent: 'sub-categoria-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sub-categoria/sub-categoria-dialog.html',
                    controller: 'SubCategoriaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubCategoria', function(SubCategoria) {
                            return SubCategoria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sub-categoria.new', {
            parent: 'sub-categoria',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sub-categoria/sub-categoria-dialog.html',
                    controller: 'SubCategoriaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                tipo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sub-categoria', null, { reload: 'sub-categoria' });
                }, function() {
                    $state.go('sub-categoria');
                });
            }]
        })
        .state('sub-categoria.edit', {
            parent: 'sub-categoria',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sub-categoria/sub-categoria-dialog.html',
                    controller: 'SubCategoriaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubCategoria', function(SubCategoria) {
                            return SubCategoria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sub-categoria', null, { reload: 'sub-categoria' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sub-categoria.delete', {
            parent: 'sub-categoria',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sub-categoria/sub-categoria-delete-dialog.html',
                    controller: 'SubCategoriaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SubCategoria', function(SubCategoria) {
                            return SubCategoria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sub-categoria', null, { reload: 'sub-categoria' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
