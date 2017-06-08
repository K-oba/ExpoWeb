(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('canton', {
            parent: 'entity',
            url: '/canton?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cantons'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/canton/cantons.html',
                    controller: 'CantonController',
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
        .state('canton-detail', {
            parent: 'canton',
            url: '/canton/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Canton'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/canton/canton-detail.html',
                    controller: 'CantonDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Canton', function($stateParams, Canton) {
                    return Canton.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'canton',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('canton-detail.edit', {
            parent: 'canton-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canton/canton-dialog.html',
                    controller: 'CantonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Canton', function(Canton) {
                            return Canton.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('canton.new', {
            parent: 'canton',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canton/canton-dialog.html',
                    controller: 'CantonDialogController',
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
                    $state.go('canton', null, { reload: 'canton' });
                }, function() {
                    $state.go('canton');
                });
            }]
        })
        .state('canton.edit', {
            parent: 'canton',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canton/canton-dialog.html',
                    controller: 'CantonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Canton', function(Canton) {
                            return Canton.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('canton', null, { reload: 'canton' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('canton.delete', {
            parent: 'canton',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canton/canton-delete-dialog.html',
                    controller: 'CantonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Canton', function(Canton) {
                            return Canton.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('canton', null, { reload: 'canton' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
