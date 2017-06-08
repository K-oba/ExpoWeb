(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('beacon', {
            parent: 'entity',
            url: '/beacon?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Beacons'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beacon/beacons.html',
                    controller: 'BeaconController',
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
        .state('beacon-detail', {
            parent: 'beacon',
            url: '/beacon/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Beacon'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beacon/beacon-detail.html',
                    controller: 'BeaconDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Beacon', function($stateParams, Beacon) {
                    return Beacon.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'beacon',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('beacon-detail.edit', {
            parent: 'beacon-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beacon/beacon-dialog.html',
                    controller: 'BeaconDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beacon', function(Beacon) {
                            return Beacon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beacon.new', {
            parent: 'beacon',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beacon/beacon-dialog.html',
                    controller: 'BeaconDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                uuid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('beacon', null, { reload: 'beacon' });
                }, function() {
                    $state.go('beacon');
                });
            }]
        })
        .state('beacon.edit', {
            parent: 'beacon',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beacon/beacon-dialog.html',
                    controller: 'BeaconDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beacon', function(Beacon) {
                            return Beacon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beacon', null, { reload: 'beacon' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beacon.delete', {
            parent: 'beacon',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beacon/beacon-delete-dialog.html',
                    controller: 'BeaconDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Beacon', function(Beacon) {
                            return Beacon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beacon', null, { reload: 'beacon' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
