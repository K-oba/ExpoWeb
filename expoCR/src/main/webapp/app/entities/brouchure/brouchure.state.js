(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('brouchure', {
            parent: 'entity',
            url: '/brouchure?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Brouchures'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brouchure/brouchures.html',
                    controller: 'BrouchureController',
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
        .state('brouchure-detail', {
            parent: 'brouchure',
            url: '/brouchure/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Brouchure'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brouchure/brouchure-detail.html',
                    controller: 'BrouchureDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Brouchure', function($stateParams, Brouchure) {
                    return Brouchure.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'brouchure',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('brouchure-detail.edit', {
            parent: 'brouchure-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brouchure/brouchure-dialog.html',
                    controller: 'BrouchureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Brouchure', function(Brouchure) {
                            return Brouchure.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brouchure.new', {
            parent: 'brouchure',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brouchure/brouchure-dialog.html',
                    controller: 'BrouchureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                urlimagen: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('brouchure', null, { reload: 'brouchure' });
                }, function() {
                    $state.go('brouchure');
                });
            }]
        })
        .state('brouchure.edit', {
            parent: 'brouchure',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brouchure/brouchure-dialog.html',
                    controller: 'BrouchureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Brouchure', function(Brouchure) {
                            return Brouchure.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brouchure', null, { reload: 'brouchure' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brouchure.delete', {
            parent: 'brouchure',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brouchure/brouchure-delete-dialog.html',
                    controller: 'BrouchureDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Brouchure', function(Brouchure) {
                            return Brouchure.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brouchure', null, { reload: 'brouchure' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
