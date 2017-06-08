(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('click', {
            parent: 'entity',
            url: '/click?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Clicks'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/click/clicks.html',
                    controller: 'ClickController',
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
        .state('click-detail', {
            parent: 'click',
            url: '/click/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Click'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/click/click-detail.html',
                    controller: 'ClickDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Click', function($stateParams, Click) {
                    return Click.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'click',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('click-detail.edit', {
            parent: 'click-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/click/click-dialog.html',
                    controller: 'ClickDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Click', function(Click) {
                            return Click.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('click.new', {
            parent: 'click',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/click/click-dialog.html',
                    controller: 'ClickDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                beaconId: null,
                                clientId: null,
                                coordenadas: null,
                                fechaHora: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('click', null, { reload: 'click' });
                }, function() {
                    $state.go('click');
                });
            }]
        })
        .state('click.edit', {
            parent: 'click',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/click/click-dialog.html',
                    controller: 'ClickDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Click', function(Click) {
                            return Click.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('click', null, { reload: 'click' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('click.delete', {
            parent: 'click',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/click/click-delete-dialog.html',
                    controller: 'ClickDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Click', function(Click) {
                            return Click.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('click', null, { reload: 'click' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
