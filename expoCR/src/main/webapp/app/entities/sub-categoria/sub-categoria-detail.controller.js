(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('SubCategoriaDetailController', SubCategoriaDetailController);

    SubCategoriaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SubCategoria', 'Categoria', 'Brouchure', 'Click', 'Beacon'];

    function SubCategoriaDetailController($scope, $rootScope, $stateParams, previousState, entity, SubCategoria, Categoria, Brouchure, Click, Beacon) {
        var vm = this;

        vm.subCategoria = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('expoCrApp:subCategoriaUpdate', function(event, result) {
            vm.subCategoria = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
