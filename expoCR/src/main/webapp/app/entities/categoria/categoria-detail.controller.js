(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('CategoriaDetailController', CategoriaDetailController);

    CategoriaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Categoria', 'Exposicion', 'SubCategoria'];

    function CategoriaDetailController($scope, $rootScope, $stateParams, previousState, entity, Categoria, Exposicion, SubCategoria) {
        var vm = this;

        vm.categoria = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:categoriaUpdate', function(event, result) {
            vm.categoria = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
