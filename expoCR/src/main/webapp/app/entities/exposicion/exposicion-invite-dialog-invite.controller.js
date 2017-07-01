(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ExposicionInviteController',ExposicionDeleteController);

    ExposicionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Exposicion'];

    function ExposicionDeleteController($uibModalInstance, entity, Exposicion) {
        var vm = this;

        vm.exposicion = entity;
        vm.clear = clear;
        vm.confirmInvite = confirmInvite;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmInvite (id) {
            Exposicion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
