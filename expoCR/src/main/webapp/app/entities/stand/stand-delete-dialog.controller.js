(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('StandDeleteController',StandDeleteController);

    StandDeleteController.$inject = ['$uibModalInstance', 'entity', 'Stand'];

    function StandDeleteController($uibModalInstance, entity, Stand) {
        var vm = this;

        vm.stand = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stand.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
