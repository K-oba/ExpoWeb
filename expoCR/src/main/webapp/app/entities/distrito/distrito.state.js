(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('distrito', {
            parent: 'entity',
            url: '/distrito?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Distritos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/distrito/distritos.html',
                    controller: 'DistritoController',
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
        .state('distrito-detail', {
            parent: 'distrito',
            url: '/distrito/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Distrito'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/distrito/distrito-detail.html',
                    controller: 'DistritoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Distrito', function($stateParams, Distrito) {
                    return Distrito.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'distrito',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('distrito-detail.edit', {
            parent: 'distrito-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/distrito/distrito-dialog.html',
                    controller: 'DistritoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Distrito', function(Distrito) {
                            return Distrito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('distrito.new', {
            parent: 'distrito',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/distrito/distrito-dialog.html',
                    controller: 'DistritoDialogController',
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
                    $state.go('distrito', null, { reload: 'distrito' });
                }, function() {
                    $state.go('distrito');
                });
            }]
        })
        .state('distrito.edit', {
            parent: 'distrito',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/distrito/distrito-dialog.html',
                    controller: 'DistritoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Distrito', function(Distrito) {
                            return Distrito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('distrito', null, { reload: 'distrito' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('distrito.delete', {
            parent: 'distrito',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/distrito/distrito-delete-dialog.html',
                    controller: 'DistritoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Distrito', function(Distrito) {
                            return Distrito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('distrito', null, { reload: 'distrito' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
