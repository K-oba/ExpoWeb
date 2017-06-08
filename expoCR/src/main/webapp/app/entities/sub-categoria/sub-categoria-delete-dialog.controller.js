(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('SubCategoriaDeleteController',SubCategoriaDeleteController);

    SubCategoriaDeleteController.$inject = ['$uibModalInstance', 'entity', 'SubCategoria'];

    function SubCategoriaDeleteController($uibModalInstance, entity, SubCategoria) {
        var vm = this;

        vm.subCategoria = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SubCategoria.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
