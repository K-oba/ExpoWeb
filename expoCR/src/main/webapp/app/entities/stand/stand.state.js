(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('stand', {
            parent: 'entity',
            url: '/stand?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Stands'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stand/stands.html',
                    controller: 'StandController',
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
        .state('stand-detail', {
            parent: 'stand',
            url: '/stand/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Stand'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stand/stand-detail.html',
                    controller: 'StandDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Stand', function($stateParams, Stand) {
                    return Stand.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'stand',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('stand-detail.edit', {
            parent: 'stand-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stand/stand-dialog.html',
                    controller: 'StandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Stand', function(Stand) {
                            return Stand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stand.new', {
            parent: 'stand',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stand/stand-dialog.html',
                    controller: 'StandDialogController',
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
                    $state.go('stand', null, { reload: 'stand' });
                }, function() {
                    $state.go('stand');
                });
            }]
        })
        .state('stand.edit', {
            parent: 'stand',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stand/stand-dialog.html',
                    controller: 'StandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Stand', function(Stand) {
                            return Stand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stand', null, { reload: 'stand' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stand.delete', {
            parent: 'stand',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stand/stand-delete-dialog.html',
                    controller: 'StandDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Stand', function(Stand) {
                            return Stand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stand', null, { reload: 'stand' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
