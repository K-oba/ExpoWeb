(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('DistritoDeleteController',DistritoDeleteController);

    DistritoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Distrito'];

    function DistritoDeleteController($uibModalInstance, entity, Distrito) {
        var vm = this;

        vm.distrito = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Distrito.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
