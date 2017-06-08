(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ProvinciaDeleteController',ProvinciaDeleteController);

    ProvinciaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Provincia'];

    function ProvinciaDeleteController($uibModalInstance, entity, Provincia) {
        var vm = this;

        vm.provincia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Provincia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
