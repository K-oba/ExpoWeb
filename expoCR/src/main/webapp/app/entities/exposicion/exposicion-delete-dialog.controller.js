(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ExposicionDeleteController',ExposicionDeleteController);

    ExposicionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Exposicion'];

    function ExposicionDeleteController($uibModalInstance, entity, Exposicion) {
        var vm = this;

        vm.exposicion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Exposicion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
